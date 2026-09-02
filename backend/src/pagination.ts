import { createHash, createHmac, timingSafeEqual } from "node:crypto";

import { z } from "zod";

import type { EntityIds } from "./readonly-graphql.js";

const pageSize = 8;
const entityKeys = ["ability", "item", "move", "pokemon"] as const;

const idsSchema = z.object({
  ability: z.array(z.number().int().positive()).max(1_000),
  item: z.array(z.number().int().positive()).max(1_000),
  move: z.array(z.number().int().positive()).max(1_000),
  pokemon: z.array(z.number().int().positive()).max(1_000),
});
const keysetSchema = z.object({
  ability: z.number().int().positive().nullable(),
  item: z.number().int().positive().nullable(),
  move: z.number().int().positive().nullable(),
  pokemon: z.number().int().positive().nullable(),
});

const cursorSchema = z.object({
  after: keysetSchema,
  expires_at: z.number().int().positive(),
  query_sha256: z.string().regex(/^[a-f0-9]{64}$/),
  remaining: idsSchema,
  version: z.literal(1),
});

interface PaginationOptions {
  now?: () => number;
  secret: string;
  ttlMs?: number;
}

export interface EntityIdPage {
  ability_ids: number[];
  item_ids: number[];
  move_ids: number[];
  pagination: {
    continuation_cursor: string | null;
    has_more: boolean;
    page_size: 8;
    scope: "verified_entity_ids";
  };
  pokemon_ids: number[];
}

export interface PaginationService {
  firstPage(question: string, candidates: EntityIds): EntityIdPage;
  nextPage(question: string, cursor: string): EntityIdPage;
}

export class ContinuationCursorError extends Error {}

function normalizedIds(ids: number[]): number[] {
  return [...new Set(ids)].sort((left, right) => left - right);
}

function normalizeEntityIds(ids: EntityIds): EntityIds {
  return Object.fromEntries(entityKeys.map((key) => [key, normalizedIds(ids[key])])) as unknown as EntityIds;
}

function questionHash(question: string): string {
  return createHash("sha256").update(question.trim()).digest("hex");
}

function invalidCursor(): Error {
  return new ContinuationCursorError("Invalid continuation cursor");
}

type Keyset = z.infer<typeof keysetSchema>;

function advancedKeyset(previous: Keyset, current: EntityIds): Keyset {
  return Object.fromEntries(
    entityKeys.map((key) => [key, current[key].at(-1) ?? previous[key]]),
  ) as Keyset;
}

export function createPaginationService(options: PaginationOptions): PaginationService {
  if (Buffer.byteLength(options.secret) < 32) {
    throw new Error("Pagination signing secret must contain at least 32 bytes");
  }
  const now = options.now ?? Date.now;
  const ttlMs = options.ttlMs ?? 15 * 60 * 1_000;
  if (!Number.isInteger(ttlMs) || ttlMs < 1 || ttlMs > 24 * 60 * 60 * 1_000) {
    throw new Error("Pagination cursor TTL must be between 1 millisecond and 24 hours");
  }

  function sign(encodedPayload: string): string {
    return createHmac("sha256", options.secret).update(encodedPayload).digest("base64url");
  }

  function encode(payload: z.infer<typeof cursorSchema>): string {
    const encoded = Buffer.from(JSON.stringify(payload)).toString("base64url");
    return `${encoded}.${sign(encoded)}`;
  }

  function decode(cursor: string): z.infer<typeof cursorSchema> {
    if (cursor.length > 100_000) throw invalidCursor();
    const parts = cursor.split(".");
    if (parts.length !== 2 || !parts[0] || !parts[1]) throw invalidCursor();
    const expected = Buffer.from(sign(parts[0]), "base64url");
    let actual: Buffer;
    try {
      actual = Buffer.from(parts[1], "base64url");
    } catch {
      throw invalidCursor();
    }
    if (actual.length !== expected.length || !timingSafeEqual(actual, expected)) throw invalidCursor();
    try {
      return cursorSchema.parse(JSON.parse(Buffer.from(parts[0], "base64url").toString("utf8")));
    } catch {
      throw invalidCursor();
    }
  }

  function page(
    question: string,
    current: EntityIds,
    remaining: EntityIds,
    expiresAt: number,
    previousKeyset: Keyset,
  ): EntityIdPage {
    const hasMore = entityKeys.some((key) => remaining[key].length > 0);
    const after = advancedKeyset(previousKeyset, current);
    return {
      ability_ids: current.ability,
      item_ids: current.item,
      move_ids: current.move,
      pagination: {
        continuation_cursor: hasMore
          ? encode({
              after,
              expires_at: expiresAt,
              query_sha256: questionHash(question),
              remaining,
              version: 1,
            })
          : null,
        has_more: hasMore,
        page_size: pageSize,
        scope: "verified_entity_ids",
      },
      pokemon_ids: current.pokemon,
    };
  }

  return {
    firstPage(question, candidates) {
      const verified = normalizeEntityIds(candidates);
      const first = Object.fromEntries(
        entityKeys.map((key) => [key, verified[key].slice(0, pageSize)]),
      ) as unknown as EntityIds;
      const remaining = Object.fromEntries(
        entityKeys.map((key) => [key, verified[key].slice(pageSize)]),
      ) as unknown as EntityIds;
      return page(
        question,
        first,
        remaining,
        now() + ttlMs,
        { ability: null, item: null, move: null, pokemon: null },
      );
    },
    nextPage(question, cursor) {
      const payload = decode(cursor);
      if (payload.expires_at <= now()) {
        throw new ContinuationCursorError("Continuation cursor has expired");
      }
      if (payload.query_sha256 !== questionHash(question)) {
        throw new ContinuationCursorError("Continuation cursor does not match the query");
      }
      const current = Object.fromEntries(
        entityKeys.map((key) => [key, payload.remaining[key].slice(0, pageSize)]),
      ) as unknown as EntityIds;
      const remaining = Object.fromEntries(
        entityKeys.map((key) => [key, payload.remaining[key].slice(pageSize)]),
      ) as unknown as EntityIds;
      return page(question, current, remaining, payload.expires_at, payload.after);
    },
  };
}

export function resolvePaginationConfig(environment: {
  CURSOR_SIGNING_SECRET?: string;
  CURSOR_TTL_SECONDS?: string;
}) {
  const secret = environment.CURSOR_SIGNING_SECRET;
  if (!secret) throw new Error("CURSOR_SIGNING_SECRET is required");
  const ttlSeconds = Number(environment.CURSOR_TTL_SECONDS ?? "900");
  if (!Number.isInteger(ttlSeconds) || ttlSeconds < 1 || ttlSeconds > 86_400) {
    throw new Error("CURSOR_TTL_SECONDS must be an integer between 1 and 86400");
  }
  return { secret, ttlMs: ttlSeconds * 1_000 };
}

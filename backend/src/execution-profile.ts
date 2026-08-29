export const EXECUTION_PROFILE = {
  graphql: {
    max_complexity: 10_000,
    max_depth: 8,
    max_response_bytes: 1_000_000,
    max_rows: 100,
    timeout_ms: 5_000,
  },
  max_graphql_attempts: 6,
  max_tool_rounds: 6,
  model: {
    max_retries: 1,
    planning_output_tokens: 2_500,
    synthesis_output_tokens: 1_500,
    timeout_ms: 45_000,
  },
  version: "ask-pokedexer-eval-v3",
} as const;

export interface EntityResolution {
  alias: string;
  canonicalName: string;
  disclosure: string;
}

const reviewedAliases: EntityResolution[] = [
  {
    alias: "Pikablu",
    canonicalName: "marill",
    disclosure: "an unofficial historical fan name associated with Marill",
  },
];

function normalizedWords(value: string): string {
  return value
    .normalize("NFKD")
    .replaceAll(/[\u0300-\u036f]/g, "")
    .toLocaleLowerCase()
    .replaceAll(/[^a-z0-9]+/g, " ")
    .trim()
    .replaceAll(/\s+/g, " ");
}

export function resolveEntityAliases(question: string): EntityResolution[] {
  const normalizedQuestion = ` ${normalizedWords(question)} `;
  return reviewedAliases.filter(({ alias }) =>
    normalizedQuestion.includes(` ${normalizedWords(alias)} `),
  );
}

export function discloseEntityResolutions(
  answer: string,
  resolutions: EntityResolution[],
): string {
  if (resolutions.length === 0) return answer;
  const disclosures = resolutions
    .filter(({ alias, canonicalName }) => {
      const normalizedAnswer = normalizedWords(answer);
      return !(
        normalizedAnswer.includes(normalizedWords(alias)) &&
        normalizedAnswer.includes(normalizedWords(canonicalName)) &&
        /\b(?:fan|historical|unofficial)\b/i.test(answer)
      );
    })
    .map(({ alias, disclosure }) => `“${alias}” is ${disclosure}.`);
  return disclosures.length === 0 ? answer : `${disclosures.join(" ")} ${answer}`;
}

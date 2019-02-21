public final class BoyerMoore {
    private final String pattern;
    private final int patternLength;
    private final BadCharacterRule badCharacterRule;
    private final GoodSuffixRule goodSuffixRule;

    public BoyerMoore(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.pattern = pattern;
        this.patternLength = pattern.length();
        this.badCharacterRule = new BadCharacterRule(pattern);
        this.goodSuffixRule = new GoodSuffixRule(pattern);
    }

    // Returns the number of times this pattern matches against the text
    public int run(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        final int textLength = text.length();
        if (textLength == 0 || text.length() < patternLength) {
            return 0;
        }

        if (textLength == patternLength) {
            return pattern.equals(text) ? 1 : 0;
        }

        int matches = 0;
        int index = patternLength - 1;
        while (index < textLength) {
            int offset = 0;
            while (offset < patternLength) {
                if (pattern.charAt(patternLength - offset - 1) != text.charAt(index - offset)) {
                    // Mismatch, try to skip as much as we can
                    int badCharacterRuleSkips = badCharacterRule.getNumSkips(text.charAt(index - offset), offset);
                    int goodSuffixRuleSkips = goodSuffixRule.getNumSkips(offset);
                    index += Math.max(badCharacterRuleSkips, goodSuffixRuleSkips);
                    break;
                } else {
                    offset++;
                }
            }

            if (offset == patternLength) {
                matches++;
                // The Good Suffix Rule still applies and can result in skips
                // The Bad Character Rule doesn't apply as all characters matched (i.e. no bad characters)
                index += goodSuffixRule.getNumSkips(offset);
            }
            // we will always make 1 more shift as we want number of shifts, and rules give us number of skips
            // if there were no skips at all, we would still want to shift by 1
            index++;
        }

        return matches;
    }

    public String getPattern() {
        return pattern;
    }
}

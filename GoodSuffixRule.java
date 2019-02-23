public final class GoodSuffixRule {
    private final String pattern;
    private final int patternLength;
    private final int[] table;

    public GoodSuffixRule(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.pattern = pattern;
        this.patternLength = pattern.length();
        this.table = new int[patternLength+1];
        buildTable();
    }

    // Returns the number of skips by the Good Suffix Rule
    // offset is the number of characters to the left of the last index of the pattern
    // e.g. ABCD -> D's offset = 0, C's offset = 1, etc.
    // offset should be on the mismatched character
    public int getNumSkips(int offset) {
        if (offset < 0 || offset > patternLength) {
            throw new IllegalArgumentException();
        }

        // Good Suffix Rule doesn't apply (mismatched on first character)
        if (offset == 0) {
            return 0;
        }

        // This index can be -1 if pattern matched
        int index = patternLength - offset - 1;
        return table[index+1];
    }


    // Pre-processing for the Good Suffix Rule
    // Reference: http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/bmen.htm
    private void buildTable() {
        int[] tmp = new int[patternLength+1];

        int i = patternLength;
        int j = patternLength + 1;

        tmp[i] = j;

        while (i > 0) {
            while (j <= patternLength && pattern.charAt(i-1) != pattern.charAt(j-1)) {
                if (table[j] == 0) {
                    table[j] = j - i;
                }
                j = tmp[j];
            }
            i--;
            j--;
            tmp[i] = j;
        }

        j = tmp[0];
        for (i=0; i<=patternLength; i++) {
            if (table[i] == 0) {
                table[i] = j;
            }
            if (i == j) {
                j = tmp[j];
            }
        }

        for (i=0; i<table.length; i++) {
            table[i]--;
        }
    }
}

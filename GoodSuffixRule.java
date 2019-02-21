public final class GoodSuffixRule {
    private final String pattern;
    private final int[] table;

    public GoodSuffixRule(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.pattern = pattern;
        this.table = new int[this.pattern.length()+1];
        buildTable();
    }

    // Returns the number of skips by the Good Suffix Rule
    // offset should be on the mismatched character
    public int getNumSkips(int offset) {
        if (offset < 0 || offset > this.pattern.length()) {
            throw new IllegalArgumentException();
        }

        // Good Suffix Rule doesn't apply (mismatched on first character)
        if (offset == 0) {
            return 0;
        }

        // This index can be -1 if pattern matched
        int index = this.pattern.length() - offset - 1;
        return table[index+1];
    }


    // Reference: http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/bmen.htm
    private void buildTable() {
        int len = this.pattern.length();
        int[] tmp = new int[len+1];

        int i = len;
        int j = len + 1;

        tmp[i] = j;

        while (i > 0) {
            while (j <= len && this.pattern.charAt(i-1) != this.pattern.charAt(j-1)) {
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
        for (i=0; i<=len; i++) {
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

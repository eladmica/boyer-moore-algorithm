import java.util.HashMap;
import java.util.Map;

public final class BadCharacterRule {
    private final String pattern;
    private final int patternLength;
    private final Map<Character, int[]> table;

    public BadCharacterRule(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.pattern = pattern;
        this.patternLength = pattern.length();
        this.table = new HashMap<>();
        buildTable();
    }

    // Returns the number of skips by the Bad Character Rule
    // offset is the number of characters to the left of the last index of the pattern
    // e.g. ABCD -> D's offset = 0, C's offset = 1, etc.
    // c is the character of the text where the mismatch occurred
    public int getNumSkips(char c, int offset) {
        if (offset < 0 || offset > patternLength) {
            throw new IllegalArgumentException();
        }

        // Bad Character Rule doesn't apply (all characters matched)
        if (offset == patternLength) {
            return 0;
        }

        int index = patternLength - offset - 1;
        if (!table.containsKey(c)) {
            return index;
        }
        return table.get(c)[index];
    }

    // Pre-processing for the Bad Character Rule
    private void buildTable() {
        for (int i=0; i<patternLength; i++) {
            char curr = pattern.charAt(i);
            if (!table.containsKey(curr)) {
                table.put(curr, new int[patternLength]);
            }
            // Mark occurrence
            table.get(curr)[i] = -1;
        }

        for (char c : table.keySet()) {
            int[] row = table.get(c);
            int counter = 0;
            for (int i=0; i<row.length; i++) {
                if (row[i] == -1) {
                    row[i] = counter;
                    counter = 0;
                } else {
                    row[i] = counter;
                    counter++;
                }
            }
        }
    }
}

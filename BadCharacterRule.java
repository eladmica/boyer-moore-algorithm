import java.util.HashMap;
import java.util.Map;

public class BadCharacterRule {
    private final String pattern;
    private Map<Character, int[]> table;

    public BadCharacterRule(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.pattern = pattern;
        this.table = buildTable();
    }

    // Returns the number of skips by the Bad Character Rule
    // offset is the number of characters away the end of pattern
    // e.g. ABCD -> D's offset = 0, C's offset = 1, etc.
    // c is the character of the text where the mismatch occurred
    public int getNumSkips(char c, int offset) {
        int index = this.pattern.length() - offset - 1;
        if (index < 0 || offset < 0) {
            throw new IllegalArgumentException();
        }

        if (!table.containsKey(c)) {
            return index;
        }
        return table.get(c)[index];
    }

    private Map<Character, int[]> buildTable() {
        Map<Character, int[]> table = new HashMap<>();

        for (int i=0; i<this.pattern.length(); i++) {
            char curr = this.pattern.charAt(i);
            if (!table.containsKey(curr)) {
                table.put(curr, new int[this.pattern.length()]);
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

        return table;
    }
}

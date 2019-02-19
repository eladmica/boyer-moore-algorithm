public class BoyerMoore {
    private final String pattern;

    public BoyerMoore(String pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.pattern = pattern;
    }
}

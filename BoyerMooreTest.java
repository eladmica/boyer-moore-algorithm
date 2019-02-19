import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class BoyerMooreTest {

    @Test
    public void testNumSkipsByBadCharRule() {
        BoyerMoore bm = new BoyerMoore("a");
        assertEquals(0, bm.numSkipsByBadCharRule('a', 0));
        assertEquals(0, bm.numSkipsByBadCharRule('b', 0));

        bm = new BoyerMoore("aaa");
        assertEquals(0, bm.numSkipsByBadCharRule('a', 0));
        assertEquals(0, bm.numSkipsByBadCharRule('a', 1));
        assertEquals(0, bm.numSkipsByBadCharRule('a', 2));
        assertEquals(2, bm.numSkipsByBadCharRule('b', 0));
        assertEquals(1, bm.numSkipsByBadCharRule('b', 1));
        assertEquals(0, bm.numSkipsByBadCharRule('b', 2));

        bm = new BoyerMoore("acbbbbbccac");
        assertEquals(1, bm.numSkipsByBadCharRule('c', 0));
        assertEquals(0, bm.numSkipsByBadCharRule('c', 2));
        assertEquals(5, bm.numSkipsByBadCharRule('c', 3));
        assertEquals(1, bm.numSkipsByBadCharRule('c', 9));
        assertEquals(0, bm.numSkipsByBadCharRule('a', 0));
        assertEquals(8, bm.numSkipsByBadCharRule('a', 1));
        assertEquals(3, bm.numSkipsByBadCharRule('b', 0));
        assertEquals(10, bm.numSkipsByBadCharRule('z', 0));
        assertEquals(8, bm.numSkipsByBadCharRule('z', 2));
        assertEquals(1, bm.numSkipsByBadCharRule('z', 9));
        assertEquals(0, bm.numSkipsByBadCharRule('z', 10));
    }

    @Test
    public void testNumSkipsByBadCharRuleIllegalArgumentException() {
        BoyerMoore bm = new BoyerMoore("a");
        runNumSkipsByBadCharRuleException(bm, 'a', -1);
        runNumSkipsByBadCharRuleException(bm, 'b', -1);
        runNumSkipsByBadCharRuleException(bm, 'a', 1);
        runNumSkipsByBadCharRuleException(bm, 'b', 1);
        bm = new BoyerMoore("abcdc");
        runNumSkipsByBadCharRuleException(bm, 'c', -5);
        runNumSkipsByBadCharRuleException(bm, 'c', 5);
    }

    // Helper method for testing numSkipsByBadCharRule() when expecting an exception to be thrown
    private void runNumSkipsByBadCharRuleException(BoyerMoore bm, char c, int offset) {
        try {
            bm.numSkipsByBadCharRule(c, offset);
            fail(String.format("Expected IllegalArgumentException with char '%c' and index %d", c, offset));
        } catch (IllegalArgumentException e) {
            // passed!
        }
    }
}
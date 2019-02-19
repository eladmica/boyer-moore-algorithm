import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class BadCharacterRuleTest {
    private BadCharacterRule rule;
    private BadCharacterRule ruleSingleChar;
    private BadCharacterRule ruleRepeatedChars;


    @BeforeEach
    private void setUp() {
        this.rule = new BadCharacterRule("acbbbbbccac");
        this.ruleSingleChar = new BadCharacterRule("a");
        this.ruleRepeatedChars = new BadCharacterRule("aaa");
    }

    @Test
    public void testGetNumSkips() {
        assertEquals(0, ruleSingleChar.getNumSkips('a', 0));
        assertEquals(0, ruleSingleChar.getNumSkips('b', 0));

        assertEquals(0, ruleRepeatedChars.getNumSkips('a', 0));
        assertEquals(0, ruleRepeatedChars.getNumSkips('a', 1));
        assertEquals(0, ruleRepeatedChars.getNumSkips('a', 2));
        assertEquals(2, ruleRepeatedChars.getNumSkips('b', 0));
        assertEquals(1, ruleRepeatedChars.getNumSkips('b', 1));
        assertEquals(0, ruleRepeatedChars.getNumSkips('b', 2));

        assertEquals(1, rule.getNumSkips('c', 0));
        assertEquals(0, rule.getNumSkips('c', 2));
        assertEquals(5, rule.getNumSkips('c', 3));
        assertEquals(1, rule.getNumSkips('c', 9));
        assertEquals(0, rule.getNumSkips('a', 0));
        assertEquals(8, rule.getNumSkips('a', 1));
        assertEquals(3, rule.getNumSkips('b', 0));
        assertEquals(10, rule.getNumSkips('z', 0));
        assertEquals(8, rule.getNumSkips('z', 2));
        assertEquals(1, rule.getNumSkips('z', 9));
        assertEquals(0, rule.getNumSkips('z', 10));
    }

    @Test
    public void testGetNumSkipsIllegalArgumentException() {
        runNumSkipsException(ruleSingleChar, 'a', -1);
        runNumSkipsException(ruleSingleChar, 'b', -1);
        runNumSkipsException(ruleSingleChar, 'a', 1);
        runNumSkipsException(ruleSingleChar, 'b', 1);

        runNumSkipsException(rule, 'c', -2);
        runNumSkipsException(rule, 'c', 11);
    }

    // Helper method for testing getNumSkips() when expecting an exception to be thrown
    private void runNumSkipsException(BadCharacterRule rule, char c, int offset) {
        try {
            rule.getNumSkips(c, offset);
            fail(String.format("Expected IllegalArgumentException with char '%c' and index %d", c, offset));
        } catch (IllegalArgumentException e) {
            // passed!
        }
    }
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoodSuffixRuleTest {
    private GoodSuffixRule rule, rule2, rule3;
    private GoodSuffixRule ruleSingleChar;
    private GoodSuffixRule ruleRepeatedChars;

    @BeforeEach
    private void setUp() {
        this.rule = new GoodSuffixRule("abbcabbca");
        this.rule2 = new GoodSuffixRule("abaacaac");
        this.rule3 = new GoodSuffixRule("abbcc");
        this.ruleSingleChar = new GoodSuffixRule("a");
        this.ruleRepeatedChars = new GoodSuffixRule("aaa");
    }

    @Test
    public void testGetNumSkips() {
        assertEquals(0, rule.getNumSkips(0));
        assertEquals(7, rule.getNumSkips(1));
        assertEquals(7, rule.getNumSkips(2));
        assertEquals(7, rule.getNumSkips(3));
        assertEquals(7, rule.getNumSkips(4));
        assertEquals(3, rule.getNumSkips(5));
        assertEquals(3, rule.getNumSkips(6));
        assertEquals(3, rule.getNumSkips(7));
        assertEquals(3, rule.getNumSkips(8));
        assertEquals(3, rule.getNumSkips(9)); // match!

        assertEquals(0, rule2.getNumSkips(0));
        assertEquals(7, rule2.getNumSkips(1));
        assertEquals(2, rule2.getNumSkips(3));
        assertEquals(7, rule2.getNumSkips(4));
        assertEquals(7, rule2.getNumSkips(8));

        assertEquals(0, rule3.getNumSkips(0));
        assertEquals(0, rule3.getNumSkips(1));
        assertEquals(4, rule3.getNumSkips(2));
        assertEquals(4, rule3.getNumSkips(3));
        assertEquals(4, rule3.getNumSkips(4));
        assertEquals(4, rule3.getNumSkips(5));

        assertEquals(0, ruleSingleChar.getNumSkips(0));
        assertEquals(0, ruleSingleChar.getNumSkips(1));

        assertEquals(0, ruleRepeatedChars.getNumSkips(0));
        assertEquals(1, ruleRepeatedChars.getNumSkips(1));
        assertEquals(0, ruleRepeatedChars.getNumSkips(2));
        assertEquals(0, ruleRepeatedChars.getNumSkips(3));
    }

}

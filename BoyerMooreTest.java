import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

public class BoyerMooreTest {
    private BoyerMoore boyerMoore;

    @Test
    public void testRunTrivialEdgeCases() {
        boyerMoore = new BoyerMoore("abc");
        assertEquals(0, boyerMoore.run(""));
        assertEquals(0, boyerMoore.run("a"));
        assertEquals(0, boyerMoore.run("z"));
        assertEquals(0, boyerMoore.run("zzz"));
        assertEquals(1, boyerMoore.run("abc"));
    }

    @Test
    public void testRunNoMatch() {
        boyerMoore = new BoyerMoore("a");
        assertEquals(0, boyerMoore.run("b"));
        boyerMoore = new BoyerMoore("abc");
        assertEquals(0, boyerMoore.run("abdc"));
        assertEquals(0, boyerMoore.run("aabbcc"));
        boyerMoore = new BoyerMoore(".");
        assertEquals(0, boyerMoore.run("sadfhjhjdshjdfsajhkfdsajhfkdaskfdshajfdshjjshjjhkj"));
    }

    @Test
    public void testRunSingleMatch() {
        boyerMoore = new BoyerMoore("a");
        assertEquals(1, boyerMoore.run("a"));
        assertEquals(1, boyerMoore.run("ab"));
        assertEquals(1, boyerMoore.run("ba"));
        assertEquals(1, boyerMoore.run("dsbfnjndjdfodbfayudfoydsfofd"));

        boyerMoore = new BoyerMoore("abc");
        assertEquals(1, boyerMoore.run("abcd"));
        assertEquals(1, boyerMoore.run("dabc"));
        assertEquals(1, boyerMoore.run("aaaaaaaaaaaaaabccccccccccccccc"));

        boyerMoore = new BoyerMoore("TGGTACCGGCGTTGGGCAGACCCACCCTGGGATTTTAGAGCTGTA");
        assertEquals(1, boyerMoore.run(DNA));
    }

    @Test
    public void testRunMultipleMatches() {
        boyerMoore = new BoyerMoore("a");
        assertEquals(2, boyerMoore.run("aa"));
        assertEquals(2, boyerMoore.run("aba"));
        assertEquals(2, boyerMoore.run("abab"));
        assertEquals(2, boyerMoore.run("baba"));
        assertEquals(6, boyerMoore.run("aaaaaa"));

        boyerMoore = new BoyerMoore("abc");
        assertEquals(2, boyerMoore.run("abcabc"));
        assertEquals(2, boyerMoore.run("aabcaabcaa"));
        assertEquals(2, boyerMoore.run("abcdefgabc"));

        boyerMoore = new BoyerMoore("abcab");
        assertEquals(3, boyerMoore.run("abcabcabcabc"));
        assertEquals(3, boyerMoore.run("abcabcabcabcac"));

        boyerMoore = new BoyerMoore("G");
        assertEquals(countOccurances('G', DNA), boyerMoore.run(DNA));
        boyerMoore = new BoyerMoore("A");
        assertEquals(countOccurances('A', DNA), boyerMoore.run(DNA));
        boyerMoore = new BoyerMoore("z");
        assertEquals(countOccurances('z', DNA), boyerMoore.run(DNA));

        boyerMoore = new BoyerMoore("TAC");
        assertEquals(15, boyerMoore.run(DNA));
        boyerMoore = new BoyerMoore("GGGG");
        assertEquals(6, boyerMoore.run(DNA));
        boyerMoore = new BoyerMoore("AT");
        assertEquals(63, boyerMoore.run(DNA));
    }

    @Test
    public void testRunRandom() {
        runExperiment(50, 5, 100000);
        runExperiment(10000, 5, 20);
        runExperiment(145624, 100, 10);
        runExperiment(252247, 500, 100);
    }

    // Runs an experiment
    // An experiment consists of numTrials number of trials, where each generates a random
    // text and pattern of the given lengths
    private void runExperiment(int textLength, int patternLength, int numTrials) {
        String text, pattern;
        for (int i=0; i<numTrials; i++) {
            text = generateRandomDNASequence(textLength);
            pattern = generateRandomDNASequence(patternLength);
            boyerMoore = new BoyerMoore(pattern);
            assertEquals(stringMatch(text, pattern), boyerMoore.run(text));
        }
    }

    // Counts the number of times a pattern appears in the text
    // The implementation of Boyer-Moore would be test against this slow and naive approach
    private int stringMatch(String text, String pattern) {
        int count = 0;
        int index = text.indexOf(pattern);
        while (index != -1) {
            count++;
            index = text.indexOf(pattern, index+1);
        }
        return count;
    }

    // Generates a random DNA Sequence with given length
    // DNA sequences are some permutation of the characters A, C, G, and T
    private String generateRandomDNASequence(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<length; i++) {
            // Generate a random number between 0 and 3, inclusive.
            int rand = ThreadLocalRandom.current().nextInt(0, 4);
            switch (rand) {
                case 0:
                    sb.append('A');
                    break;
                case 1:
                    sb.append('C');
                    break;
                case 2:
                    sb.append('G');
                    break;
                case 3:
                    sb.append('T');
                    break;
                default:
                    throw new IllegalStateException("Random number generated should be between 0 and 3, inclusive.");
            }
        }
        return sb.toString();
    }

    // Counts the number of times character c appears in the string
    private int countOccurances(char c, String s) {
        int count = 0;
        for (int i=0; i<s.length(); i++) {
            count += (c == s.charAt(i)) ? 1 : 0;
        }
        return count;
    }

    private final String DNA = "GAGCCTAATTGCCCTTCACGAAAAACACAGATCAGGCACCTATCAGAAGGCGACTAACTA" +
        "GCAGCCGTATCGGGGATATAAAATCGTTGTCATGCATCAGGTTCCCTGAGTACGATCGAC" +
        "TATGGCAATCCTGAGCTCGTGGGATAGTACAAGCCGTATCGCCACTCAGTCAATTTCAGC" +
        "GGAACTAGAAGGCGCTGTGAGGGGCGCTTAATACCCCGGGTATGAATGAGGCAGCTCGCT" +
        "GCTACGGTGGAGGGGAAAGGACGGGAGCAGACTATTAATGTGGCCCTACCCTTCATAATT" +
        "GAAACCGATCTTTTGATACACCCAAGGGGCTTACCGCTTGAGCACCTGATGGACTGCTAG" +
        "TACATTCCGCTTGATTTCTGGGAGGGCGCTGGACTCCCGCATGTGGGATGAGTCCGTCAC" +
        "GTGCCTAACATGGTACCGGCGTTGGGCAGACCCACCCTGGGATTTTAGAGCTGTAGGTTC" +
        "GAATGATAGACCCTCACCCGTCGTTCCCTGACATTTTCAACATTTTCGTCAGCTCCAAAG" +
        "TCCTCAAATTGAAGGGGCTGAACTGCCGTTATCAATGTCCGTCGACCGTCACTCCTGACA" +
        "TAGAGCAGGGGATATGTAAATTTAATGAGAGAGGAATACAGGACCGTACGTAGCTTGACA" +
        "GTTGGCCCTCAGCTACGGCATCGTAAATTTGAAGTCCTGCTGCGACCGACTGGATGTCCT" +
        "GACTCACAATTTCCGGTTCTTAAGCTAAAGACCAGGTCTGGTCAGGGTGTATGTCAGTCA" +
        "GAACAACTACGTGGCTGCCAGTTCAAAGAAGCTAATTTTTGTTGAGGAGGCATGCGTGGG" +
        "CGAGATCCACTCATTGCTCGGCTTCCCCCATGAGTACTTTATAAAGTTTGGTGGGTAGTC" +
        "ATTTTAAGGGAATTTTCACGCGACCGGCTTTCGCGGGATGAGTAAAGCCAGCTGTCTATT" +
        "CATCGGCGAAGGGTACCAGATCATTGGAAACGTGACGGAT";
}

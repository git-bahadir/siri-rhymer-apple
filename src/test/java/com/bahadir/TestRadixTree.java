package com.bahadir;

import static org.junit.Assert.*;


import java.nio.file.Path;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bahadir.RadixTree;


public class TestRadixTree {

    final static String PROJECT_PATH = Path.of("").toAbsolutePath().toString();
    final static String TESTS_PATH = PROJECT_PATH + 
        Path.of("/src/test/java/com/bahadir/resources/testData").toAbsolutePath().toString();

    final static String TEST_EMPTY_TREE_INPUT = "";

    final static String TEST_FULL_TREE_INPUT = "tention";

    final static String TEST_WHOLE_WORD_INPUT = "controversy";

    final static String TEST_DUPLICATE_WORD_INPUT = "abbas";

    final static String TEST_EMPTY_WORD_INPUT = "";

    final static String TEST_NUM_WORD_INPUT = "as1a";

    final static String TEST_NO_MATCH_WORD_INPUT = "aaaa";
    

    //makes sure that returned list of rhymes are matching unsortedly
    private static void assertEqualsRhymes(List<String> expected, List<String> actual) {
        Assert.assertEquals(expected.size(), actual.size());

        for (Object o : expected) {
            assertTrue(actual.contains(o));
        }
    }

    @Test
    public void testEmptyTree() {
        RadixTree radix = new RadixTree();

        radix.buildTree(RadixTree.readFile(TESTS_PATH + "/emptyTree/test.txt"));
        List<String> rhymes = radix.collectRhymes(TEST_EMPTY_TREE_INPUT);
        assertEquals(0, rhymes.size());
    }

    @Test
    public void testFullTree() {
        RadixTree radix = new RadixTree();
        List<String> expectedRhymes = RadixTree.readFile(TESTS_PATH + "/fullTree/truth.txt");

        radix.buildTree(RadixTree.readFile(TESTS_PATH + "/fullTree/test.txt"));
        List<String> rhymes = radix.collectRhymes(TEST_FULL_TREE_INPUT);
        assertEqualsRhymes(expectedRhymes, rhymes);
    }

    // tests rhyme output with input being a complete word
    @Test
    public void testWholeWord() {
        RadixTree radix = new RadixTree();
        List<String> expectedRhymes = RadixTree.readFile(TESTS_PATH + "/wholeWord/truth.txt");

        radix.buildTree(RadixTree.readFile(TESTS_PATH + "/wholeWord/test.txt"));
        List<String> rhymes = radix.collectRhymes(TEST_WHOLE_WORD_INPUT);
        assertEqualsRhymes(expectedRhymes, rhymes);
    }

    //input txt contains duplicate words and input is that word
    @Test
    public void testDuplicateWord() {
        RadixTree radix = new RadixTree();
        List<String> expectedRhymes = RadixTree.readFile(TESTS_PATH + "/duplicateWord/truth.txt");

        radix.buildTree(RadixTree.readFile(TESTS_PATH + "/duplicateWord/test.txt"));
        List<String> rhymes = radix.collectRhymes(TEST_DUPLICATE_WORD_INPUT);
        assertEqualsRhymes(expectedRhymes, rhymes);
    }

    //input word is empty string
    @Test
    public void testEmptyWord() {
        RadixTree radix = new RadixTree();

        radix.buildTree(RadixTree.readFile(TESTS_PATH + "/emptyWord/test.txt"));
        List<String> rhymes = radix.collectRhymes(TEST_EMPTY_WORD_INPUT);
        assertEquals(0, rhymes.size());
    }

    //input word contains numeric character
    @Test
    public void testNumWord() {
        RadixTree radix = new RadixTree();

        radix.buildTree(RadixTree.readFile(TESTS_PATH + "/numWord/test.txt"));
        List<String> rhymes = radix.collectRhymes(TEST_NUM_WORD_INPUT);
        assertEquals(0, rhymes.size());
    }

    //input word is too long for a match so zero matches will return
    @Test
    public void testNoMatchWord() {
        RadixTree radix = new RadixTree();

        radix.buildTree(RadixTree.readFile(TESTS_PATH + "/noMatchWord/test.txt"));
        List<String> rhymes = radix.collectRhymes(TEST_NO_MATCH_WORD_INPUT);
        assertEquals(0, rhymes.size());
    }
    
}

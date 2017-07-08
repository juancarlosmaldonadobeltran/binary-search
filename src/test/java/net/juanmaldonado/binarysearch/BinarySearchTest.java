package net.juanmaldonado.binarysearch;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BinarySearchTest {

    @Test
    public void testAscSortedSearchSpaceContainsKeyMultipleTimes() {

        final int[] searchSpace = {0, 1, 2, 4, 4, 6, 7, 7, 7, 7, 8, 9, 10, 12, 15, 15};
        final int key = 7;
        BinarySearch binarySearch = new BinarySearch(searchSpace, key);
        List<Integer> keyIndexes = binarySearch.search();
        assertNotNull(keyIndexes);
        final int expectedFoundIndexesListSize = 4;
        assertEquals(expectedFoundIndexesListSize, keyIndexes.size());
        assertTrue(keyIndexes.contains(6));
        assertTrue(keyIndexes.contains(7));
        assertTrue(keyIndexes.contains(8));
        assertTrue(keyIndexes.contains(9));
    }

    @Test
    public void testDescSortedSearchSpaceContainsKeyMultipleTimes() {

        final int[] searchSpace = {15, 15, 12, 10, 9, 8, 7, 7, 7, 7, 6, 4, 4, 2, 1, 0};
        final int key = 7;
        BinarySearch binarySearch = new BinarySearch(searchSpace, key);
        List<Integer> keyIndexes = binarySearch.search();
        assertNotNull(keyIndexes);
        final int expectedFoundIndexesListSize = 4;
        assertEquals(expectedFoundIndexesListSize, keyIndexes.size());
        assertTrue(keyIndexes.contains(6));
        assertTrue(keyIndexes.contains(7));
        assertTrue(keyIndexes.contains(8));
        assertTrue(keyIndexes.contains(9));
    }

    @Test
    public void testAscSortedSearchSpaceContainsKeyOnce() {

        final int[] searchSpace = {0, 1, 2, 4, 4, 6, 6, 6, 6, 7, 8, 9, 10, 12, 15, 15};
        final int key = 7;
        BinarySearch binarySearch = new BinarySearch(searchSpace, key);
        List<Integer> keyIndexes = binarySearch.search();
        assertNotNull(keyIndexes);
        final int expectedFoundIndexesListSize = 1;
        assertEquals(expectedFoundIndexesListSize, keyIndexes.size());
        assertTrue(keyIndexes.contains(9));
    }

    @Test
    public void testDescSortedSearchSpaceContainsKeyOnce() {

        final int[] searchSpace = {15, 15, 12, 10, 9, 8, 8, 8, 8, 7, 6, 4, 4, 2, 1, 0};
        final int key = 7;
        BinarySearch binarySearch = new BinarySearch(searchSpace, key);
        List<Integer> keyIndexes = binarySearch.search();
        assertNotNull(keyIndexes);
        final int expectedFoundIndexesListSize = 1;
        assertEquals(expectedFoundIndexesListSize, keyIndexes.size());
        assertTrue(keyIndexes.contains(9));
    }

    @Test
    public void testAscSortedSearchSpaceDoNotContainsKey() {

        final int[] searchSpace = {0, 1, 2, 4, 4, 6, 6, 6, 6, 7, 8, 9, 10, 12, 15, 15};
        final int key = 3;
        BinarySearch binarySearch = new BinarySearch(searchSpace, key);
        List<Integer> keyIndexes = binarySearch.search();
        assertNotNull(keyIndexes);
        final int expectedFoundIndexesListSize = 1;
        assertEquals(expectedFoundIndexesListSize, keyIndexes.size());
        assertTrue(keyIndexes.contains(-1));
    }

    @Test
    public void testDescSortedSearchSpaceDoNotContainsKey() {

        final int[] searchSpace = {15, 15, 12, 10, 9, 8, 8, 8, 8, 7, 6, 4, 4, 2, 1, 0};
        final int key = 3;
        BinarySearch binarySearch = new BinarySearch(searchSpace, key);
        List<Integer> keyIndexes = binarySearch.search();
        assertNotNull(keyIndexes);
        final int expectedFoundIndexesListSize = 1;
        assertEquals(expectedFoundIndexesListSize, keyIndexes.size());
        assertTrue(keyIndexes.contains(-1));
    }

    @Test
    public void testSingleValueSearchSpaceContainsKey() {

        final int[] searchSpace = {5};
        final int key = 5;
        BinarySearch binarySearch = new BinarySearch(searchSpace, key);
        List<Integer> keyIndexes = binarySearch.search();
        assertNotNull(keyIndexes);
        final int expectedFoundIndexesListSize = 1;
        assertEquals(expectedFoundIndexesListSize, keyIndexes.size());
        assertTrue(keyIndexes.contains(0));
    }

    @Test
    public void testSingleValueSearchSpaceDoNotContainsKey() {

        final int[] searchSpace = {5};
        final int key = 2;
        BinarySearch binarySearch = new BinarySearch(searchSpace, key);
        List<Integer> keyIndexes = binarySearch.search();
        assertNotNull(keyIndexes);
        final int expectedFoundIndexesListSize = 1;
        assertEquals(expectedFoundIndexesListSize, keyIndexes.size());
        assertTrue(keyIndexes.contains(-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSearchSpace() {

        final int[] searchSpace = null;
        final int key = 7;
        new BinarySearch(searchSpace, key);
    }

}

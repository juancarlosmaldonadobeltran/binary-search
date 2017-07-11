package net.juanmaldonado.binarysearch;

import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * Binary search algorithm implementation to find an ascendant sorted array of key's indexes given a key value.
 * <p>
 * The algorithm searches inside a sorted (ascendant or descendant) array of integers called search space which could
 * contains the given key once or multiple times.
 */
public class BinarySearch {

    private static final int VALUE_NOT_FOUND_INDEX = -1;
    private static final int[] VALUE_NOT_FOUND_RESULT = {VALUE_NOT_FOUND_INDEX};
    private static final BiFunction<Integer, Integer, Boolean> ASC_SORTED_SEARCH_SPACE_REDUCTION_CONDITION = (a, b) -> a < b;
    private static final BiFunction<Integer, Integer, Boolean> DESC_SORTED_SEARCH_SPACE_REDUCTION_CONDITION = (a, b) -> a > b;

    private enum SearchedOccurrence {
        /**
         * Refers to the key occurrence related to the lowest search space index.
         */
        FIRST,
        /**
         * Refers to the key occurrence related to the highest search space index.
         */
        LAST;
    }

    private final int[] searchSpace;
    private final int key;

    /**
     * @param searchSpace sorted array of integer values.
     * @param key         value to be found.
     */
    public BinarySearch(int[] searchSpace, int key) {
        if (searchSpace == null) {
            throw new IllegalArgumentException("Search space must not be null.");
        }
        this.searchSpace = searchSpace;
        this.key = key;
    }

    /**
     * Search given a search space and key.
     *
     * @return asc sorted array of key's indexes if the search space contains the key. Otherwise, it returns an array which
     * contains the -1 single value.
     */
    public int[] search() {
        return this.findAllKeyIndexes(this.searchSpace, this.key);
    }


    private int[] findAllKeyIndexes(int[] searchSpace, int key) {

        // searches for the first key occurrence over entire search space.
        int initialLowIndex = 0;
        int firstIndex = this.findKeyIndex(searchSpace, key, initialLowIndex, SearchedOccurrence.FIRST);

        // key not found.
        if (firstIndex == VALUE_NOT_FOUND_INDEX) {
            return VALUE_NOT_FOUND_RESULT;
        }

        // searches for last key occurrence over the right side of the first key occurrence.
        if (firstIndex < searchSpace.length - 1) {
            initialLowIndex = firstIndex + 1;
            int lastIndex = this.findKeyIndex(searchSpace, key, initialLowIndex, SearchedOccurrence.LAST);
            if (lastIndex != VALUE_NOT_FOUND_INDEX) {
                // returns all key's indexes found.
                return IntStream.rangeClosed(firstIndex, lastIndex).toArray();
            }
        }
        return new int[]{firstIndex};
    }


    /**
     * Select the condition to be use by the binary search algorithm for search space reduction depending on the search
     * space sorting (descendant or ascendant).
     *
     * @param searchSpace search space.
     * @return the search space reduction condition.
     */
    private BiFunction<Integer, Integer, Boolean> getSearchSpaceReductionCondition(int[] searchSpace) {
        // verify if the given search space sorting is ascendant or descendant sorted.
        if (searchSpace.length > 1 && searchSpace[0] > searchSpace[searchSpace.length - 1]) {
            return DESC_SORTED_SEARCH_SPACE_REDUCTION_CONDITION;
        }
        return ASC_SORTED_SEARCH_SPACE_REDUCTION_CONDITION;
    }

    /**
     * Implements the binary search algorithm to find the first or last key's index if exists.
     *
     * @param searchSpace        search space.
     * @param key                key to be found.
     * @param initialLowIndex    initial lower bound of the current search space.
     * @param searchedOccurrence indicates if first or last key occurrence is gonna be searched.
     * @return key's index if search space contains the key. Otherwise returns -1.
     */
    private int findKeyIndex(int[] searchSpace, int key, int initialLowIndex, SearchedOccurrence searchedOccurrence) {

        // bounds of the current search space.
        int highIndex = searchSpace.length - 1;
        int lowIndex = initialLowIndex;

        int keyIndex = VALUE_NOT_FOUND_INDEX;

        BiFunction<Integer, Integer, Boolean> searchSpaceReductionCondition = getSearchSpaceReductionCondition(searchSpace);

        // Iterates inside the search space reducing de size of the space each iteration.
        while (lowIndex <= highIndex) {
            // calculates the index corresponding to the current space of search.
            int middleIndex = (highIndex + lowIndex) / 2;
            // verify if the search space's middle value corresponds to the given key.
            if (searchSpace[middleIndex] == key) {

                // key found and current key's index update.
                keyIndex = middleIndex;

                if (SearchedOccurrence.FIRST.equals(searchedOccurrence)) {
                    // search to the left bound.
                    highIndex = middleIndex - 1;
                } else {
                    // search to the right bound.
                    lowIndex = middleIndex + 1;
                }

            } else if (searchSpaceReductionCondition.apply(key, searchSpace[middleIndex])) {
                // reduce the search space to the left middle of the current search space.
                highIndex = middleIndex - 1;
            } else {
                // reduce the search space to the right middle of the current search space.
                lowIndex = middleIndex + 1;
            }
        }

        // key not found.
        return keyIndex;
    }

}

package net.juanmaldonado.binarysearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Binary search algorithm implementation to find an ascendant sorted key's indexes list given a key value.
 * <p>
 * The algorithm searches inside a sorted (ascendant or descendant) array of integers called search space which could
 * contains the given key once or multiple times.
 */
public class BinarySearch {

    private static final int VALUE_NOT_FOUND_INDEX = -1;
    private static final List<Integer> VALUE_NOT_FOUND_RESULT = Collections.singletonList(VALUE_NOT_FOUND_INDEX);
    private static final BiFunction<Integer, Integer, Boolean> ASC_SORTED_SEARCH_SPACE_REDUCTION_CONDITION = (a, b) -> a < b;
    private static final BiFunction<Integer, Integer, Boolean> DESC_SORTED_SEARCH_SPACE_REDUCTION_CONDITION = (a, b) -> a > b;

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
     * @return asc sorted key's indexes list if the search space contains the key. Otherwise, it returns an array which
     * contains the -1 single value.
     */
    public List<Integer> search() {

        List<Integer> allKeyIndexes = this.findAllKeyIndexes(this.searchSpace, this.key);
        Collections.sort(allKeyIndexes);
        return allKeyIndexes;
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

    private List<Integer> findAllKeyIndexes(int[] searchSpace, int key) {

        int keyIndex = this.findFirstKeyIndex(searchSpace, key);

        if (keyIndex != VALUE_NOT_FOUND_INDEX) {
            List<Integer> keyIndexes = this.findNeighborsKeyIndexes(searchSpace, key, keyIndex);
            keyIndexes.add(keyIndex);
            return keyIndexes;
        }
        return VALUE_NOT_FOUND_RESULT;
    }

    /**
     * Implements the binary search algorithm to find the first key's index if exists.
     *
     * @param searchSpace search space.
     * @param key         key to be found.
     * @return key's index if search space contains the key. Otherwise returns -1.
     */
    private int findFirstKeyIndex(int[] searchSpace, int key) {

        // bounds of the current search space.
        int lowIndex = 0;
        int highIndex = searchSpace.length - 1;

        BiFunction<Integer, Integer, Boolean> searchSpaceReductionCondition = getSearchSpaceReductionCondition(searchSpace);

        // Iterates inside the search space reducing de size of the space each iteration.
        while (lowIndex <= highIndex) {
            // calculates the index corresponding to the current space of search.
            int middleIndex = (highIndex + lowIndex) / 2;
            // verify if the search space's middle value corresponds to the given key.
            if (searchSpace[middleIndex] == key) {
                // key found and returns the corresponding index.
                return middleIndex;
            } else if (searchSpaceReductionCondition.apply(key, searchSpace[middleIndex])) {
                // reduce the search space to the right middle of the current search space.
                highIndex = middleIndex - 1;
            } else {
                // reduce the search space to the left middle of the current search space.
                lowIndex = middleIndex + 1;
            }
        }
        // key not found.
        return VALUE_NOT_FOUND_INDEX;

    }

    /**
     * Finds all key's indexes around (both left and right sides) of a given key's index.
     *
     * @param searchSpace search space.
     * @param key         value to be found.
     * @param keyIndex    key's index.
     * @return list of key's indexes found around the given key's index. Otherwise returns an empty list.
     */
    private List<Integer> findNeighborsKeyIndexes(int[] searchSpace, int key, int keyIndex) {

        // search neighbors to the left side of the given key's index.
        List<Integer> neighborsKeyIndexes = findOneSideNeighborsKeyIndexes(searchSpace, key,
                keyIndex - 1, i -> i >= 0, i -> i = i - 1);
        // search neighbors to the right side of the given key's index.
        neighborsKeyIndexes.addAll(findOneSideNeighborsKeyIndexes(searchSpace, key,
                keyIndex + 1, i -> i < searchSpace.length, i -> i = i + 1));
        return neighborsKeyIndexes;
    }

    private List<Integer> findOneSideNeighborsKeyIndexes(int[] searchSpace, int key, int startIndex,
                                                         Function<Integer, Boolean> stopCondition,
                                                         Function<Integer, Integer> increment) {
        List<Integer> neighborsKeyIndexes = new ArrayList<>();
        for (int i = startIndex; stopCondition.apply(i); i = increment.apply(i)) {
            if (searchSpace[i] == key) {
                neighborsKeyIndexes.add(i);
            } else {
                break;
            }
        }
        return neighborsKeyIndexes;
    }

}

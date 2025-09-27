package org.example.algos;

import org.example.metrics.Counters;
import org.example.metrics.DepthTracker;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


//Sorting: correctness on random and adversarial arrays; verify recursion depth is bounded (QS depth ≲
//~2*floor(log2 n) + O(1) under randomized pivot).
class MergeSortTest {

    @Test void sortsBasicCases() {
        int[] a = {2,5,3,9,2,1,7};
        MergeSort.sort(a);
        assertArrayEquals(new int[]{1,2,2,3,5,7,9}, a);

        int[] empty = {};
        MergeSort.sort(empty);
        assertArrayEquals(new int[]{}, empty);

        int[] one = {42};
        MergeSort.sort(one);
        assertArrayEquals(new int[]{42}, one);
    }

    @Test void handlesSortedAndReversed() {
        int[] sorted = {1,2,3,4,5};
        MergeSort.sort(sorted);
        assertArrayEquals(new int[]{1,2,3,4,5}, sorted);

        int[] reverse = {5,4,3,2,1};
        MergeSort.sort(reverse);
        assertArrayEquals(new int[]{1,2,3,4,5}, reverse);
    }

    @Test void metricsIncreaseAndDepthIsLog() {
        int n = 1_000;
        int[] arr = new int[n];
        Random rnd = new Random(123);
        for (int i=0;i<n;i++) arr[i] = rnd.nextInt();

        DepthTracker d = new DepthTracker();
        Counters c = new Counters();
        MergeSort.sort(arr, d, c);

        assertTrue(isSorted(arr));
        assertTrue(d.maxDepth() >= 1);
        assertTrue(d.maxDepth() < n);
        assertTrue(c.copies >= n - 1);
    }

    private static boolean isSorted(int[] a) {
        for (int i=1;i<a.length;i++)
            if (a[i-1] > a[i])
                return false;
        return true;
    }
}

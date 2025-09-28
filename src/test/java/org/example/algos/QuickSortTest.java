package org.example.algos;

import org.example.metrics.Counters;
import org.example.metrics.DepthTracker;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i-1] > a[i]) return false;
        return true;
    }

    @Test
    void sorts_basic_small() {
        int[] a = {7,5,31,632,135};
        QuickSort.sort(a);
        assertArrayEquals(new int[]{5,7,31,135,632}, a);

        int[] empty = {};
        QuickSort.sort(empty);
        assertTrue(isSorted(empty));

        int[] one = {2};
        QuickSort.sort(one);
        assertTrue(isSorted(one));
    }

    @Test
    void handles_sorted_reversed_allEqual() {
        int n = 10_000;

        int[] sorted = new int[n];
        for (int i=0;i<n;i++) sorted[i]=i;
        QuickSort.sort(sorted);
        assertTrue(isSorted(sorted));

        int[] rev = new int[n];
        for (int i=0;i<n;i++) rev[i]=n-i;
        QuickSort.sort(rev);
        assertTrue(isSorted(rev));

        int[] eq = new int[n];
        for (int i=0;i<n;i++) eq[i]=7;
        QuickSort.sort(eq);
        assertTrue(isSorted(eq));
    }

    @Test
    void depth_is_bounded_like_log_n() {
        int n = 1<<15;
        int[] a = new int[n];
        Random rnd = new Random(123);
        for (int i=0;i<n;i++) a[i] = rnd.nextInt();

        var d = new DepthTracker();
        var c = new Counters();
        QuickSort.sort(a, d, c);

        assertTrue(isSorted(a));
        int log2 = 31 - Integer.numberOfLeadingZeros(n);
        assertTrue(d.maxDepth() <= 2*log2 + 16, "depth too large: " + d.maxDepth());
        assertEquals(0, c.allocs, "QuickSort shouldn't allocate big buffers");
    }
}


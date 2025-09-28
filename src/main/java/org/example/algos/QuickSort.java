package org.example.algos;

import org.example.metrics.Counters;
import org.example.metrics.DepthTracker;

import java.util.Random;

public final class QuickSort {

    public static void sort(int[] a) {
        sort(a, new DepthTracker(), new Counters());
    }

    public static void sort(int[] a, DepthTracker depth, Counters cnt) {
        if (a == null || a.length < 2) return;
        qs(a, 0, a.length - 1, depth, cnt, new Random(123456789L));
    }

    private static void qs(int[] a, int lo, int hi, DepthTracker depth, Counters cnt, Random rnd) {
        while (lo < hi) {
            // randomized pivot
            int pivot = a[lo + rnd.nextInt(hi - lo + 1)];

            int j = hPartition(a, lo, hi, pivot, cnt);
            int leftSize  = j - lo + 1;
            int rightSize = hi - (j + 1) + 1; // = hi - j

            // recursion only for small
            if (leftSize < rightSize) {
                if (leftSize > 1) {
                    try (var r = depth.enter()) {
                        qs(a, lo, j, depth, cnt, rnd);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                lo = j + 1;           // tail iteration over big right
            } else {
                if (rightSize > 1) {
                    try (var r = depth.enter()) {
                        qs(a, j + 1, hi, depth, cnt, rnd);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                hi = j;               // tail iteration over big left
            }
        }
    }


    private static int hPartition(int[] a, int lo, int hi, int pivot, Counters cnt) {
        int i = lo - 1, j = hi + 1;
        while (true) {

            do {
                i++; cnt.comps++;
            }
            while (a[i] < pivot);

            do {
                j--; cnt.comps++;
            }
            while (a[j] > pivot);

            if (i >= j) return j;
            swap(a, i, j, cnt);
        }
    }

    private static void swap(int[] a, int i, int j, Counters cnt) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
        cnt.copies += 3;
    }
}

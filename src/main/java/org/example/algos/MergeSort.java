package org.example.algos;

import org.example.metrics.Counters;
import org.example.metrics.DepthTracker;


public final class MergeSort {
    private static final int CUTOFF = 24;

    private MergeSort() {}

    //for regular usage
    public static void sort(int[] a) {
        sort(a, new DepthTracker(), new Counters());
    }

    // Entrance with metrics
    public static void sort(int[] a, DepthTracker depth, Counters counters) {
        if (a == null || a.length < 2) return;
        int[] buffer = new int[a.length];         // temporary auxilary space
        counters.allocs++;                              // counting 1 alloction
        sortRange(a, buffer, 0, a.length, depth, counters);
    }

    //Sorting range
    private static void sortRange(int[] a, int[] buffer, int left, int right, DepthTracker depth, Counters counters) {
        try (var __ = depth.enter()) {
            int len = right - left;
            if (len <= 1) return;

            // cutoff for small parts
            if (len <= CUTOFF) {
                insertion(a, left, right, counters);
                return;
            }

            int mid = (left + right) >>> 1;
            sortRange(a, buffer, left,  mid, depth, counters);
            sortRange(a, buffer,  mid, right, depth, counters);

            // micro-optimization: if its already sorted(no merge)
            counters.comps++;
            if (a[mid - 1] <= a[mid]) return;

            merge(a, buffer, left, mid, right, counters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Linear merge and copying to buffer (auxilary space)
    private static void merge(int[] a, int[] buf, int left, int mid, int right, Counters counters) {
        int i = left, j = mid, k = left;

        while (i < mid && j < right) {
            counters.comps++; // comparing a[i] и a[j]
            if (a[i] <= a[j]) {
                buf[k++] = a[i++]; counters.copies++;  // copy to buffer
            } else {
                buf[k++] = a[j++]; counters.copies++;
            }
        }


        while (i < mid) {
            buf[k++] = a[i++]; counters.copies++;
        }

        while (j < right){
            buf[k++] = a[j++]; counters.copies++;
        }

        // copying sorted to our array
        for (int t = left; t < right; t++) {
            a[t] = buf[t]; counters.copies++;
        }
    }


    private static void insertion(int[] a, int l, int r, Counters counters) {
        for (int pos = l + 1; pos < r; pos++) {
            int x = a[pos]; counters.copies++; // читаем/держим ключ (считаем как присваивание ключа)
            int j = pos - 1;
            while (j >= l) {
                counters.comps++;              // comparing in loop
                if (a[j] <= x) break;
                a[j + 1] = a[j]; counters.copies++;
                j--;
            }
            a[j + 1] = x; counters.copies++;
        }
    }
}


package org.example.util;

import java.util.Random;

public final class Utils {
    private Utils() {}

    /* ===== guards ===== */

    public static void checkRange(int[] a, int lo, int hi) {
        if (a == null) throw new IllegalArgumentException("array is null");
        if (lo < 0 || hi >= a.length || lo > hi)
            throw new IllegalArgumentException("bad range: lo=" + lo + " hi=" + hi + " len=" + a.length);
    }

    public static void checkRangeExclusive(int[] a, int from, int toExclusive) {
        if (a == null) throw new IllegalArgumentException("array is null");
        if (from < 0 || toExclusive > a.length || from > toExclusive)
            throw new IllegalArgumentException("bad range: [" + from + "," + toExclusive + ") len=" + a.length);
    }

    /* ===== swap / shuffle ===== */

    public static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public static void shuffle(int[] a, Random rnd) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            swap(a, i, j);
        }
    }

    public static void shuffle(int[] a, long seed) { shuffle(a, new Random(seed)); }

    /* ===== insertion for cutoff ===== */

    public static void insertion(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int x = a[i], j = i - 1;
            while (j >= lo && a[j] > x) { a[j + 1] = a[j]; j--; }
            a[j + 1] = x;
        }
    }

    public static void insertionRange(int[] a, int from, int toExclusive) {
        checkRangeExclusive(a, from, toExclusive);
        if (toExclusive - from <= 1) return;
        insertion(a, from, toExclusive - 1);
    }

    /* ===== partitions ===== */

    public static int lomuto(int[] a, int lo, int hi, int pivotIdx) {
        checkRange(a, lo, hi);
        swap(a, pivotIdx, hi);
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a[j] < pivot) { swap(a, i, j); i++; }
        }
        swap(a, i, hi);
        return i;
    }


    public static int hoare(int[] a, int lo, int hi, int pivot) {
        checkRange(a, lo, hi);
        int i = lo - 1, j = hi + 1;
        while (true) {
            do { i++; } while (a[i] < pivot);
            do { j--; } while (a[j] > pivot);
            if (i >= j) return j;
            swap(a, i, j);
        }
    }

    public static int[] dutch3(int[] a, int lo, int hi, int pivot) {
        checkRange(a, lo, hi);
        int lt = lo, i = lo, gt = hi;
        while (i <= gt) {
            int v = a[i];
            if (v < pivot)      {
                swap(a, lt++, i++);
            }

            else if (v > pivot) {
                swap(a, i, gt--);
            }
            else{
                i++;
            }
        }
        return new int[]{lt, gt};
    }
}


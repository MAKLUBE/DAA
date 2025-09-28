package org.example.algos;

import org.example.metrics.Counters;
import org.example.metrics.DepthTracker;

public final class Select {
    private Select(){}

    public static int select(int[] a, int k) {
        return select(a, k, new DepthTracker(), new Counters());
    }

    public static int select(int[] a, int k, DepthTracker d, Counters c) {
        if (k < 0 || k >= a.length)
            throw new IllegalArgumentException("k out of range");
        return selectRange(a, 0, a.length, k, d, c);
    }

    private static int selectRange(int[] a, int l, int r, int k, DepthTracker d, Counters c) {
        try (var __ = d.enter()) {
            while (true) {
                int n = r - l;
                if (n <= 16) {
                    insertion(a, l, r, c);
                    return a[l + k];
                }
                // medians
                int m = l;
                for (int i = l; i < r; i += 5) {
                    int j = Math.min(i + 5, r);
                    insertion(a, i, j, c);
                    swap(a, m++, i + (j - i - 1) / 2, c);
                }
                // taking median as pivot
                int pivot = selectRange(a, l, m, (m - l) / 2, d, c);

                int i = l, lt = l, gt = r - 1;
                while (i <= gt) {
                    c.comps++;
                    if (a[i] < pivot) { swap(a, lt++, i++, c); }
                    else {
                        c.comps++;
                        if (a[i] > pivot) { swap(a, i, gt--, c); }
                        else i++;
                    }
                }

                int left = lt - l;
                int mid  = gt - lt + 1;
                if (k < left) { r = lt; }
                else if (k < left + mid) { return pivot; }
                else { k -= left + mid; l = gt + 1; }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertion(int[] a, int l, int r, Counters c) {
        for (int p = l + 1; p < r; p++) {
            int x = a[p]; c.copies++;
            int j = p - 1;
            while (j >= l) {
                c.comps++;
                if (a[j] <= x)
                    break;
                a[j + 1] = a[j];
                c.copies++;
                j--;
            }
            a[j + 1] = x; c.copies++;
        }
    }

    private static void swap(int[] a, int i, int j, Counters c) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t; c.copies += 3;
    }
}

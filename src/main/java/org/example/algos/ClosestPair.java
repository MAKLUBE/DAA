package org.example.algos;

import org.example.algos.Point;
import org.example.metrics.Counters;
import org.example.metrics.DepthTracker;

import java.util.Arrays;
import java.util.Comparator;

public final class ClosestPair {
    private ClosestPair() {}

    public static double solve(Point[] pts) {
        return solve(pts, new DepthTracker(), new Counters());
    }

    public static double solve(Point[] pts, DepthTracker d, Counters c) {
        if (pts.length < 2) return Double.POSITIVE_INFINITY;

        Point[] byX = Arrays.copyOf(pts, pts.length);
        Arrays.sort(byX, Comparator.comparingDouble(Point::x));
        Point[] aux = new Point[pts.length]; // for merge by y

        c.allocs++;
        return rec(byX, 0, byX.length, aux, d, c);
    }

    // range [l, r)
    private static double rec(Point[] a, int l, int r, Point[] aux, DepthTracker d, Counters c) {
        try (var __ = d.enter()) {
            int n = r - l;
            if (n <= 3) {
                double best = Double.POSITIVE_INFINITY;
                for (int i = l; i < r; i++)
                    for (int j = i + 1; j < r; j++)
                        best = Math.min(best, dist(a[i], a[j], c));
                Arrays.sort(a, l, r, Comparator.comparingDouble(Point::y));
                return best;
            }
            int m = (l + r) >>> 1;
            double midX = a[m].x();

            double dl = rec(a, l, m, aux, d, c);
            double dr = rec(a, m, r, aux, d, c);
            double dmin = Math.min(dl, dr);

            // merge by y
            int i=l, j=m, k=0;
            while (i<m && j<r) aux[k++] = (a[i].y() <= a[j].y()) ? a[i++] : a[j++];
            while (i<m) aux[k++] = a[i++];
            while (j<r) aux[k++] = a[j++];
            System.arraycopy(aux, 0, a, l, k);

            // strip: |x - midX| < dmin
            int t = 0;
            for (int p = l; p < r; p++) if (Math.abs(a[p].x() - midX) < dmin) aux[t++] = a[p];

            for (int p = 0; p < t; p++) {
                for (int q = p + 1; q < t && (aux[q].y() - aux[p].y()) < dmin; q++) {
                    dmin = Math.min(dmin, dist(aux[p], aux[q], c));
                }
            }
            return dmin;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static double dist(Point a, Point b, Counters c) {
        c.comps += 2;
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return Math.hypot(dx, dy);
    }
}

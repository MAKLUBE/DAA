package org.example.algos;

import org.example.metrics.Counters;
import org.example.metrics.DepthTracker;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class ClosestPairTest {
    @Test
    void matches_bruteforce_on_small_n() {
        Random rnd = new Random(42);
        for (int n : new int[]{10, 50, 200}) {
            Point[] pts = new Point[n];
            for (int i=0;i<n;i++) pts[i] = new Point(rnd.nextDouble(), rnd.nextDouble());
            double fast = ClosestPair.solve(pts, new DepthTracker(), new Counters());
            double slow = brute(pts);
            assertEquals(slow, fast, 1e-9);
        }
    }
    private static double brute(Point[] p) {
        double best = Double.POSITIVE_INFINITY;
        for (int i=0;i<p.length;i++)
            for (int j=i+1;j<p.length;j++)
                best = Math.min(best, Math.hypot(p[i].x()-p[j].x(), p[i].y()-p[j].y()));
        return best;
    }
}
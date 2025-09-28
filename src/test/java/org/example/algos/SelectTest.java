package org.example.algos;

import org.example.metrics.Counters;
import org.example.metrics.DepthTracker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SelectMoM5Test {
    @Test
    void matches_sort_pick_on_random() {
        Random rnd = new Random(123);
        for (int t=0; t<50; t++) {
            int n = 100 + rnd.nextInt(200);
            int[] a = new int[n];
            for (int i=0;i<n;i++) a[i]=rnd.nextInt();
            int k = rnd.nextInt(n);

            int[] b = Arrays.copyOf(a, n);
            Arrays.sort(b);
            int expected = b[k];

            int got = Select.select(a, k, new DepthTracker(), new Counters());
            assertEquals(expected, got);
        }
    }
}


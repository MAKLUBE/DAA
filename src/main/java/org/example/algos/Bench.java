package org.example.algos;

import org.example.algos.MergeSort;
import org.example.metrics.Counters;
import org.example.metrics.DepthTracker;

import java.io.PrintWriter;
import java.util.Random;

public final class Bench {
    static int[] make(int n, String kind) {
        Random rnd = new Random(123);
        int[] a = new int[n];
        switch (kind) {
            case "sorted":    for (int i=0;i<n;i++) a[i]=i; break;
            case "reversed":  for (int i=0;i<n;i++) a[i]=n-i; break;
            case "allEqual":  for (int i=0;i<n;i++) a[i]=1; break;
            default:          for (int i=0;i<n;i++) a[i]=rnd.nextInt();
        }
        return a;
    }

    public static void main(String[] args) throws Exception {
        String[] kinds = {"random","sorted","reversed","allEqual"};
        int[] sizes = {1<<10, 1<<11, 1<<12, 1<<13, 1<<14, 1<<15, 1<<16}; // 1024..65536

        try (PrintWriter out = new PrintWriter("bench.csv")) {
            out.println("algo,input,n,time_ns,depth,comps,copies,allocs");
            var d = new DepthTracker();
            var c = new Counters();

            for (String kind : kinds) {
                for (int n : sizes) {
                    int bestOf = 7;
                    long best = Long.MAX_VALUE, time = 0;
                    for (int rep=0; rep<bestOf; rep++) {
                        int[] a = make(n, kind);
                        d.reset(); c.reset();
                        long t0 = System.nanoTime();
                        MergeSort.sort(a, d, c);
                        long t1 = System.nanoTime();
                        long dt = t1 - t0;
                        if (dt < best) { best = dt; time = dt; }
                    }
                    out.printf("MergeSort,%s,%d,%d,%d,%d,%d,%d%n",
                            kind, n, time, d.maxDepth(), c.comps, c.copies, c.allocs);
                }
            }
        }
        System.out.println("bench.csv written");
    }
}


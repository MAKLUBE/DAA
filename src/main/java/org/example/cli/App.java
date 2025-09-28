package org.example.cli;

import org.example.algos.*;
import org.example.algos.Point;
import org.example.metrics.Counters;
import org.example.metrics.DepthTracker;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public final class App {


    public static void main(String[] args) throws Exception {


        Map<String,String> opt = new HashMap<>();
        for (String a: args) {
            int i=a.indexOf('=');
            if (i>0) opt.put(a.substring(0,i), a.substring(i+1));
        }

        String algo   = opt.getOrDefault("algo", "m");              // m=mergesort, q=quicksort, s=select, c=closest
        int n         = Integer.parseInt(opt.getOrDefault("n","100000"));
        String cas    = opt.getOrDefault("case","random");          // random|sorted|reversed|equal
        int trials    = Integer.parseInt(opt.getOrDefault("trials","5"));
        long seed     = Long.parseLong(opt.getOrDefault("seed","123"));
        Path outPath  = Paths.get(opt.getOrDefault("out","bench/bench.csv"));
        Files.createDirectories(outPath.getParent());

        System.out.println("Writing to: " + outPath.toAbsolutePath());

        boolean writeHeader = Files.notExists(outPath);
        try (BufferedWriter w = Files.newBufferedWriter(outPath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (writeHeader) w.write("algo,n,case,trial,time_ns,depth,comps,copies,allocs,seed,note\n");
            Random rnd = new Random(seed);

            for (int t=0; t<trials; t++) {
                DepthTracker d = new DepthTracker();
                Counters c = new Counters();
                long time; String note="";

                if (algo.equals("c")) { // Closest Pair
                    Point[] pts = genPts(n, rnd);
                    long s = System.nanoTime(); double res = ClosestPair.solve(pts, d, c); time = System.nanoTime()-s;
                    if (res < 0) note="neg?";
                }

                else if (algo.equals("s")) { // Select
                    int[] a = genArr(n, cas, rnd); int k = rnd.nextInt(Math.max(1,n));
                    long s = System.nanoTime(); int x = Select.select(a, k, d, c); time = System.nanoTime()-s;
                    if (n<=2000) { int[] b = a.clone(); Arrays.sort(b); if (x!=b[k]) note="mismatch"; }
                }

                else if (algo.equals("q")) { // QuickSort
                    int[] a = genArr(n, cas, rnd);
                    long s = System.nanoTime(); QuickSort.sort(a, d, c); time = System.nanoTime()-s;
                    if (!isSorted(a)) note="not_sorted";
                }

                else {                      // MergeSort (default)
                    int[] a = genArr(n, cas, rnd);
                    long s = System.nanoTime(); MergeSort.sort(a, d, c); time = System.nanoTime()-s;
                    if (!isSorted(a)) note="not_sorted";
                }

                w.write(String.join(",", algo, String.valueOf(n), cas, String.valueOf(t),
                        String.valueOf(time), String.valueOf(d.maxDepth()),
                        String.valueOf(c.comps), String.valueOf(c.copies),
                        String.valueOf(c.allocs), String.valueOf(seed), note));
                w.write("\n");
            }
        }
    }

    private static int[] genArr(int n, String cas, Random r) {
        int[] a = new int[n];
        switch (cas) {
            case "sorted":   for (int i=0;i<n;i++) a[i]=i; break;
            case "reversed": for (int i=0;i<n;i++) a[i]=n-i; break;
            case "equal":    Arrays.fill(a, r.nextInt(3)); break;
            default:         for (int i=0;i<n;i++) a[i]=r.nextInt();
        }
        return a;
    }
    private static Point[] genPts(int n, Random r) {
        Point[] p = new Point[n];
        for (int i=0;i<n;i++) p[i] = new Point(r.nextDouble(), r.nextDouble());
        return p;
    }
    private static boolean isSorted(int[] a){
        for (int i=1;i<a.length;i++)
            if (a[i-1]>a[i])
                return false;
        return true;
    }
}

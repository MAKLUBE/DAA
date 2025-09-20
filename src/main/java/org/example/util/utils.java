package org.example.util;
import java.util.Random;

public final class utils {
    private static final Random RND = new Random(42);
    private utils(){}

    public static void swap(int[] a, int i, int j){
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
    // Перемешивание Фишера–Йетса (пригодится для quicksort)
    public static void shuffle(int[] a){
        for (int i = a.length - 1; i > 0; i--) {
            int j = RND.nextInt(i + 1);
            swap(a, i, j);
        }
    }
    // Простой insertion sort (пригодится как cutoff)
    public static void insertion(int[] a, int lo, int hi){
        for (int i = lo + 1; i <= hi; i++) {
            int k = a[i], j = i - 1;
            while (j >= lo && a[j] > k) { a[j+1] = a[j]; j--; }
            a[j+1] = k;
        }
    }
}

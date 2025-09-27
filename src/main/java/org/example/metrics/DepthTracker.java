package org.example.metrics;

public final class DepthTracker {
    private int current = 0;
    private int max = 0;


    public AutoCloseable enter() {
        current++;
        if (current > max) max = current;
        return () -> current--;
    }

    public int maxDepth() {
        return max;
    }
    public void reset()   {
        current = 0; max = 0;
    }
}

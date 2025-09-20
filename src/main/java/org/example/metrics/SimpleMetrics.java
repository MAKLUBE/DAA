package org.example.metrics;

public final class SimpleMetrics implements Metrics {
    private long comps, assigns;
    private int maxDepth;

    @Override public void incComparisons() { comps++; }
    @Override public void incAssignments() { assigns++; }
    @Override public void updateRecursionDepth(int d) { if (d > maxDepth) maxDepth = d; }
    @Override public long comparisons() { return comps; }
    @Override public long assignments() { return assigns; }
    @Override public int maxRecursionDepth() { return maxDepth; }
}

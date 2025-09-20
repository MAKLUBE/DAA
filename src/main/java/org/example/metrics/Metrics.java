package org.example.metrics;

public interface Metrics {
    void incComparisons();
    void incAssignments();
    void updateRecursionDepth(int depth);
    long comparisons();
    long assignments();
    int maxRecursionDepth();
}

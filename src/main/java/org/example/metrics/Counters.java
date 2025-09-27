package org.example.metrics;

public final class Counters {
    //comparisons
    public long comps;
    // Copies
    public long copies;
    //Allocations like for buffer
    public long allocs;

    public void reset() {
        comps = copies = allocs = 0;
    }

}

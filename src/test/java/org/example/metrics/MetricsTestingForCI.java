package org.example.metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetricsSmokeTest {
    @Test void depthAndCountersWork() throws Exception {
        var d = new DepthTracker();
        try (var _1 = d.enter()) { try (var _2 = d.enter()) {} }
        assertTrue(d.maxDepth() >= 2);

        var c = new Counters();
        c.comps++; c.copies += 2; c.allocs++;
        assertEquals(1, c.comps);
        assertEquals(2, c.copies);
        assertEquals(1, c.allocs);
    }
}

# DAA - Assignment_1


# Analysis

## Deterministic Select (Median-of-Medians, MoM5)

**Idea.** Find the k-th smallest element in linear time without fully sorting.
- Split the array into groups of **5**, sort each small group, take their **medians**.
- **Select recursively** the median of these medians → pivot.
- **3-way partition** around the pivot (`<`, `=`, `>`), then recurse **only** into the side that contains k  
  (prefer recursing into the smaller side to keep depth small).
- For tiny ranges use **insertion sort** (cutoff).

**Complexity.**
- Recurrence: `T(n) = T(n/5) + T(≤7n/10) + Θ(n)` ⇒ **Θ(n)** (Akra–Bazzi / constant-fraction shrink).
- Space: in-place `O(1)` extra memory (besides recursion stack).
- Depth: **O(log n)** (because each step shrinks n by a constant fraction).

**Metrics (what we track).**
- `comps` — comparisons (in insertion/partition decisions);
- `copies` — assignments (swaps, shifts in insertion sort);
- `depth` — max recursion depth (counted on actual recursive enter);
- `allocs` — `0` (no big buffers).

**Tests.**
- **Correctness**: compare against `Arrays.sort(a)[k]` on 50 random trials (various n and k).
- **Edge cases**: small n, duplicates, k at edges (0 and n−1).
=======
- Correctness on random, already-sorted, reversed, and all-equal arrays.
- Depth check: `depth ≤ ~ 2 * ⌊log2 n⌋ + O(1)` on random inputs (tested at powers of two).
- Case comparison: `sorted`, `reversed`, `allEqual`.

# DAA - Assignment_1

# Analysis

## QuickSort

**Idea.** Classical QuickSort with robustness tweaks:
- **Randomized pivot:** choose pivot uniformly from `[lo..hi]` (fixed seed in tests for reproducibility).
- **Smaller-first recursion:** recurse **only** into the smaller side; handle the larger side via tail iteration → stack is typically `O(log n)`.
- **Partition:** Hoare 2-way scheme.

**Complexity.**
- Expected/average: `T(n) = T(X) + T(n−1−X) + Θ(n)` with random pivot ⇒ `Θ(n log n)`.
- Worst case (without randomness): `Θ(n^2)`; with randomness the probability of bad splits is low.
- Space: in-place `O(1)` extra memory; recursion depth ≈ `O(log n)` (due to smaller-first).

**Metrics (what we track).**
- `comps` — comparisons against the pivot (inside Hoare partition);
- `copies` — assignments due to swaps (3 per swap);
- `depth` — max recursion depth (counted only on the actual recursive calls to the smaller side);
- `allocs` — `0` (no big buffers).

**Tests.**
- Correctness on random, already-sorted, reversed, and all-equal arrays.
- Depth check: `depth ≤ ~ 2 * ⌊log2 n⌋ + O(1)` on random inputs (tested at powers of two).
- Case comparison: `sorted`, `reversed`, `allEqual`.


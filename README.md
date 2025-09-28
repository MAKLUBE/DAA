## Closest Pair of Points (2D, Divide & Conquer)

**Idea.** Given `n` points in the plane, find the minimum Euclidean distance.
- Sort points by **x** once.
- Recursively split into left/right halves; each recursive call **keeps its subarray sorted by y**
  using a linear-time **merge-by-y** on the way back up.
- Let `d = min(d_left, d_right)`. Build a **vertical strip** around the midline with
  points whose `|x - midX| < d` ordered by y. For each point in the strip check only the next
  ~**7–8 neighbors** by y (packing argument) — update `d`.

**Complexity.**
- Recurrence: `T(n) = 2T(n/2) + Θ(n)` (split + linear merge-by-y + strip scan)
  ⇒ **Θ(n log n)** by Master Theorem (Case 2).
- Space: **O(n)** for one reusable aux buffer used by the y-merge (`allocs = 1`).
- Recursion depth: **O(log n)**.

**Implementation notes.**
- Store the current segment sorted by **y** after each recursive return—this guarantees
  the strip can be scanned in linear time.
- Use a single reusable `aux[]` of size `n` (allocated once).
- Work on half-open ranges `[l, r)` to avoid off-by-one errors.
- Distance: `hypot(dx, dy)`

**Metrics (what we track).**
- `depth` — max recursion depth (one `enter()` per actual recursion).
- `allocs` — should be **1** (auxiliary buffer for all merges).
- `comps` — count conceptual comparisons (e.g., in strip checks and merge ordering).
- `copies` — assignments when merging `by y`

**Tests.**
- **Correctness (small n):** compare against a **brute-force O(n²)** implementation for
  several sizes (e.g., `n ∈ {10, 50, 200}`) with random points. Tolerance `1e-9`.
- **Larger n:** run only the D&C version (brute force disabled) and ensure it finishes fast.
- **Edge cases:** `n < 2` → `+∞`; duplicate points → distance `0`.

# Analysis

## Deterministic Select (Median-of-Medians, MoM5)

**Idea.** Find the k-th smallest element in linear time without fully sorting.
- Split the array into groups of **5**, sort each small group, take their **medians**.
- **Select recursively** the median of these medians → pivot.
- **3-way partition** around the pivot (`<`, `=`, `>`), then recurse **only** into the side that contains k  
  (prefer recursing into the smaller side to keep depth small).
- For tiny ranges use **insertion sort** (cutoff).


**Complexity.**
- Recurrence: `T(n) = 2T(n/2) + Θ(n)` (split + linear merge-by-y + strip scan)
  ⇒ **Θ(n log n)** by Master Theorem (Case 2).
- Space: **O(n)** for one reusable aux buffer used by the y-merge (`allocs = 1`).
- Recursion depth: **O(log n)**.

**Implementation notes.**
- Store the current segment sorted by **y** after each recursive return—this guarantees
  the strip can be scanned in linear time.
- Use a single reusable `aux[]` of size `n` (allocated once).
- Work on half-open ranges `[l, r)` to avoid off-by-one errors.
- Distance: `hypot(dx, dy)`

**Metrics (what we track).**
- `depth` — max recursion depth (one `enter()` per actual recursion).
- `allocs` — should be **1** (auxiliary buffer for all merges).
- `comps` — count conceptual comparisons (e.g., in strip checks and merge ordering).
- `copies` — assignments when merging `by y`

**Tests.**

- **Correctness (small n):** compare against a **brute-force O(n²)** implementation for
  several sizes (e.g., `n ∈ {10, 50, 200}`) with random points. Tolerance `1e-9`.
- **Larger n:** run only the D&C version (brute force disabled) and ensure it finishes fast.
- **Edge cases:** `n < 2` → `+∞`; duplicate points → distance `0`.

- **Correctness**: compare against `Arrays.sort(a)[k]` on 50 random trials (various n and k).
- **Edge cases**: small n, duplicates, k at edges (0 and n−1).

- Correctness on random, already-sorted, reversed, and all-equal arrays.
- Depth check: `depth ≤ ~ 2 * ⌊log2 n⌋ + O(1)` on random inputs (tested at powers of two).
- Case comparison: `sorted`, `reversed`, `allEqual`.

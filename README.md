# DAA – Assignment 1

- **Memory & depth control**
  - **MergeSort**: one **reusable buffer** per sort (`allocs = 1`), **linear merge**, **cutoff** to insertion on tiny ranges.
  - **QuickSort**: **randomized pivot** + **smaller-first recursion** (tail‐recursing only on the larger side) ⇒ stack ≈ `O(log n)` w.h.p.; in-place partition (`allocs = 0`).
  - **Select (MoM5)**: deterministic **median-of-medians (groups of 5)**, **3-way partition**; recurse **only** into the side with `k`, preferring the smaller side (`allocs = 0`).
  - **Closest Pair (2D)**: classic D&C; arrays kept **sorted by y** across recursion via one shared **aux buffer** (`allocs = 1`); strip checks ~7–8 neighbors.

## Algorithms & Recurrences (method → Θ-result)

- **MergeSort**
  - Recurrence: `T(n) = 2T(n/2) + Θ(n)` → **Θ(n log n)** (Master Theorem, Case 2).
  - Depth: `Θ(log n)`. One buffer reduces GC/alloc overhead; cutoff shrinks constants.

- **QuickSort (randomized)**
  - Expected: `E[T(n)] = E[T(X)] + E[T(n−1−X)] + Θ(n)` with uniform pivot rank `X` → **Θ(n log n)**.
  - Depth: **O(log n)** with high probability via randomized pivot + smaller-first. Worst case `Θ(n²)` avoided in practice.

- **Deterministic Select (MoM5)**
  - Recurrence: `T(n) ≤ T(n/5) + T(7n/10) + Θ(n)` → **Θ(n)** (Akra–Bazzi; constant-fraction shrink).
  - Depth: `O(log n)`. In-place; `allocs = 0`.

- **Closest Pair of Points (2D)**
  - Recurrence: `T(n) = 2T(n/2) + Θ(n)` (merge-by-y + strip) → **Θ(n log n)** (Master Case 2).
  - Depth: `Θ(log n)`. One aux buffer for y-merge.

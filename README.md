# DAA - Assignment_1


# MergeSort (D&C, Master Case 2)
• Linear merge;
• reusable buffer;
• small-n cut-off (e.g., insertion sort)

# Analysis
MergeSort (Case 2 of Master)
* Recurrence (worst/avg):
    * T(n) = 2T(n/2) + O(thetha)(n)
* Master Theorem:
  a = 2, b = 2
  f(n) => O(n) => O(n^log_b a) => Case 2
  => T(n) = O(n log n)

Depth: Log_2 n levels (stack = O(log n))
Constant factors(implementation):
* Single reusable buffer (1 alloc)
* Linear merge
* Cutoff(insertion)
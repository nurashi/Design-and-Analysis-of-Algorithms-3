# Analytical Report: Minimum Spanning Tree Algorithm Implementation

**Student:** [Your Name]  
**Course:** Design and Analysis of Algorithms  
**Assignment:** 3 - City Transportation Network Optimization  
**Date:** October 25, 2025

---

## 1. Introduction

### 1.1 Problem Statement
The city administration requires an optimized transportation network that connects all city districts with minimal construction costs. This problem is modeled as finding a Minimum Spanning Tree (MST) in a weighted undirected graph where:
- **Vertices** represent city districts
- **Edges** represent potential roads between districts
- **Edge weights** represent construction costs

The objective is to ensure all districts are connected while minimizing the total construction cost.

### 1.2 Algorithms Implemented
Two classical MST algorithms were implemented and compared:
1. **Prim's Algorithm** - A greedy algorithm using a priority queue
2. **Kruskal's Algorithm** - An edge-sorting algorithm with Union-Find data structure

---

## 2. Implementation Details

### 2.1 Data Structures

#### Graph Representation
- **TransportNetwork class**: Stores districts (vertices) and roads (edges)
- **Road class**: Represents weighted edges with source, destination, and cost
- **Adjacency Map**: Used by Prim's algorithm for efficient neighbor lookup

#### Supporting Structures
- **UnionFind (Disjoint Set)**: Implements path compression and union by rank for Kruskal's algorithm
- **Priority Queue**: Min-heap for Prim's algorithm to select minimum weight edges

### 2.2 Algorithm Implementations

#### Prim's Algorithm
```
1. Start with an arbitrary vertex
2. Add all edges from this vertex to priority queue
3. While queue is not empty and MST is incomplete:
   a. Extract minimum weight edge
   b. If destination vertex not visited:
      - Add edge to MST
      - Mark destination as visited
      - Add all edges from destination to queue
4. Return MST with total cost
```

**Time Complexity:** O((V + E) log V)  
**Space Complexity:** O(V + E)

#### Kruskal's Algorithm
```
1. Sort all edges by weight in ascending order
2. Initialize Union-Find structure for all vertices
3. For each edge in sorted order:
   a. If vertices are in different components:
      - Add edge to MST
      - Merge components using Union-Find
4. Return MST with total cost
```

**Time Complexity:** O(E log E)  
**Space Complexity:** O(V + E)

---

## 3. Experimental Results

### 3.1 Test Data Summary

#### Graph 1: Five-District Network
- **Vertices:** 5 (A, B, C, D, E)
- **Edges:** 7
- **Edge density:** Medium (70% of complete graph)

#### Graph 2: Four-District Network
- **Vertices:** 4 (A, B, C, D)
- **Edges:** 5
- **Edge density:** High (83% of complete graph)

### 3.2 Results Table

| Graph | Algorithm | Total Cost | Operations | Execution Time (ms) | MST Edges |
|-------|-----------|------------|------------|---------------------|-----------|
| 1     | Prim      | 16         | 42         | 0.41-0.47          | 4         |
| 1     | Kruskal   | 16         | 37         | 0.36-0.38          | 4         |
| 2     | Prim      | 6          | 29         | 0.04-0.07          | 3         |
| 2     | Kruskal   | 6          | 31         | 0.05               | 3         |

### 3.3 Key Observations

1. **Cost Verification:** ✅ Both algorithms produce identical MST costs for each graph, confirming correctness
2. **Operation Count:** Varies based on algorithm characteristics:
   - Graph 1: Kruskal performs fewer operations (37 vs 42)
   - Graph 2: Similar operation counts (29 vs 31)
3. **Execution Time:** Both algorithms execute in sub-millisecond time for small graphs

---

## 4. Algorithm Comparison and Analysis

### 4.1 Performance Comparison

#### Graph 1 Analysis (5 vertices, 7 edges)
- **Winner: Kruskal's Algorithm**
  - 12% fewer operations (37 vs 42)
  - 7-13% faster execution time
  - Reason: Sparse-to-medium density graph benefits from edge sorting approach

#### Graph 2 Analysis (4 vertices, 5 edges)
- **Tie: Similar Performance**
  - Comparable operation counts (within 7%)
  - Comparable execution times (within 40%)
  - Reason: Small graph size minimizes algorithmic differences

### 4.2 Operation Count Analysis

**Prim's Algorithm Operations:**
- Priority queue insertions: O(E)
- Priority queue extractions: O(V)
- Neighbor exploration: O(E)
- Formula: `5V + 4E - 11` (based on empirical counting)

**Kruskal's Algorithm Operations:**
- Edge sorting: O(E log E)
- Union-Find operations: O(E α(V)) where α is inverse Ackermann function
- Formula: `4V + E + 10` (based on empirical counting)

### 4.3 Theoretical vs Empirical Comparison

| Aspect | Prim's Algorithm | Kruskal's Algorithm |
|--------|------------------|---------------------|
| **Best Use Case** | Dense graphs (E ≈ V²) | Sparse graphs (E ≈ V) |
| **Data Structure** | Priority Queue + Adjacency List | Edge List + Union-Find |
| **Edge Processing** | All edges from visited vertices | All edges in sorted order |
| **Early Termination** | Possible when all vertices visited | Possible when V-1 edges found |
| **Implementation Complexity** | Moderate | Moderate-High (requires Union-Find) |

---

## 5. Conclusions

### 5.1 Algorithm Selection Guidelines

**Choose Prim's Algorithm when:**
1. Graph is **dense** (many edges relative to vertices)
2. Graph is stored as **adjacency matrix** or **adjacency list**
3. Need to find MST starting from a specific vertex
4. Memory for priority queue is available

**Choose Kruskal's Algorithm when:**
1. Graph is **sparse** (few edges relative to vertices)
2. Graph is stored as **edge list**
3. Edges can be efficiently sorted
4. Union-Find data structure is available

### 5.2 Implementation Quality

Both implementations demonstrate:
- ✅ **Correctness:** Produce valid MSTs with matching costs
- ✅ **Efficiency:** Sub-millisecond execution on test graphs
- ✅ **Robustness:** Handle edge cases (empty graphs, disconnected components)
- ✅ **Maintainability:** Clean code structure with clear separation of concerns

### 5.3 Real-World Applicability

For the city transportation network problem:
- **Small cities (< 100 districts):** Either algorithm works well
- **Large cities (> 1000 districts):**
  - Sparse road networks: Prefer Kruskal's
  - Dense road networks: Prefer Prim's
- **Dynamic updates:** Prim's allows incremental updates more easily

### 5.4 Performance Insights

1. **Operation counts** correlate well with theoretical complexity
2. **Execution times** are negligible for graphs with < 10 vertices
3. **Algorithm choice** matters more for large-scale graphs (1000+ vertices)
4. **Union-Find optimization** (path compression + union by rank) is crucial for Kruskal's efficiency

---

## 6. Future Improvements

### 6.1 Potential Enhancements
1. **Parallel processing** for edge sorting in Kruskal's algorithm
2. **Fibonacci heap** implementation for Prim's algorithm (theoretical O(E + V log V))
3. **Dynamic MST** algorithms for handling edge insertions/deletions
4. **Visualization tools** to display MST construction step-by-step

### 6.2 Extended Testing
1. Test on larger graphs (100-10,000 vertices)
2. Test on various graph densities (sparse, medium, dense)
3. Benchmark against optimized library implementations
4. Test with different edge weight distributions

---

## 7. References

1. Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). *Introduction to Algorithms* (3rd ed.). MIT Press.

2. Prim, R. C. (1957). "Shortest Connection Networks and Some Generalizations". *Bell System Technical Journal*, 36(6): 1389–1401.

3. Kruskal, J. B. (1956). "On the Shortest Spanning Subtree of a Graph and the Traveling Salesman Problem". *Proceedings of the American Mathematical Society*, 7(1): 48–50.

4. Tarjan, R. E. (1975). "Efficiency of a Good But Not Linear Set Union Algorithm". *Journal of the ACM*, 22(2): 215–225.

5. Sedgewick, R., & Wayne, K. (2011). *Algorithms* (4th ed.). Addison-Wesley Professional.

---

## Appendix A: Sample Output

### Graph 1 MST Edges
```
B - C : weight 2
A - C : weight 3
B - D : weight 5
D - E : weight 6
Total Cost: 16
```

### Graph 2 MST Edges
```
A - B : weight 1
B - C : weight 2
C - D : weight 3
Total Cost: 6
```

---

**End of Report**

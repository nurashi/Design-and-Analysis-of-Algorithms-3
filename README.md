# MST Algorithms - Assignment 3

Prim's and Kruskal's algorithms implementation in Java.

---

## Prim's and Kruskal's algorithms for finding Minimum Spanning Trees (MST)

```bash
javac -d out $(find src/citynetwork -name "*.java")
java -cp out citynetwork.NetworkOptimizer
```

---

## What It Does

### Compile and Run (Linux/macOS/WSL)

Reads `ass_3_input.json` → Computes MST → Writes `ass_3_output.json`

---

## Algorithms

- **Prim's**: Priority queue based  
- **Kruskal's**: Union-Find based  

Both produce the same MST cost.


## How to Run

### Linux

```bash
# Compile all source files
javac -d out $(find src/citynetwork -name "*.java")

# Run the network optimizer
java -cp out citynetwork.NetworkOptimizer
```


You also can pass custom input/output paths as arguments to see different kind of outputs

--- 

### Expected Output

```
…/git/DAA_3 ✗ java -cp out citynetwork.NetworkOptimizer
Analysis complete. Results written to ass_3_output.json

…/git/DAA_3 ❯
```

---

## Input/Output

- **Input**: `ass_3_input.json` (must be in project root)  
- **Output**: `ass_3_output.json` (created in project root)

---

### Input Format
by id
```json
{
  "graphs": [
    {
      "id": 1,
      "nodes": ["A", "B", "C", "D", "E"],
      "edges": [
        {"from": "A", "to": "B", "weight": 4},
        {"from": "A", "to": "C", "weight": 3},
        {"from": "B", "to": "C", "weight": 2},
        {"from": "B", "to": "D", "weight": 5},
        {"from": "C", "to": "D", "weight": 7},
        {"from": "C", "to": "E", "weight": 8},
        {"from": "D", "to": "E", "weight": 6}
      ]
    },
    {
      "id": 2,
      "nodes": ["A", "B", "C", "D"],
      "edges": [
        {"from": "A", "to": "B", "weight": 1},
        {"from": "A", "to": "C", "weight": 4},
        {"from": "B", "to": "C", "weight": 2},
        {"from": "C", "to": "D", "weight": 3},
        {"from": "B", "to": "D", "weight": 5}
      ]
    }
  ]
}
```
---

## Algorithm Details

- **Prim's Algorithm**: Uses a priority queue-based greedy approach to build the MST incrementally  
- **Kruskal's Algorithm**: Sorts edges by weight and uses Union-Find for efficient cycle detection  
- **Data Structures**: Custom Union-Find implementation with path compression and union by rank  
- **JSON Parsing**: Lightweight custom parser (no external dependencies)  
- **Operation Counting**: Tracks key algorithmic operations for performance analysis

---

### Prim's Algorithm

- Uses priority queue (min-heap) to greedily select minimum weight edges  
- Time complexity: O((V+E)logV)  
- Best for dense graphs

---

### Kruskal's Algorithm

- Sorts all edges and uses Union-Find for cycle detection  
- Time complexity: O(ElogE)  
- Best for sparse graphs

---

## Algorithm Comparison

Both algorithms produce the same total MST cost but may differ in:

- Execution time based on graph density  
- Number of operations performed  
- Memory usage patterns  

The output includes detailed metrics for comparing both approaches on the given transportation networks.

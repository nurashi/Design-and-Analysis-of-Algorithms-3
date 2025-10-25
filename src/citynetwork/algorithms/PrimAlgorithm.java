package citynetwork.algorithms;

import java.util.*;
import citynetwork.model.Road;
import citynetwork.model.TransportNetwork;

/**
 * Implementation of Prim's algorithm for finding the Minimum Spanning Tree.
 * 
 * Prim's algorithm is a greedy algorithm that builds the MST by starting from
 * an arbitrary vertex and repeatedly adding the minimum weight edge that connects
 * a visited vertex to an unvisited vertex.
 * 
 * Time Complexity: O((V + E) log V) where V is vertices and E is edges
 * Space Complexity: O(V + E) for adjacency map and priority queue
 * 
 * Best suited for dense graphs where E ≈ V²
 */
public class PrimAlgorithm {
    
    /**
     * Result container for Prim's algorithm execution.
     * Contains the MST edges, total cost, operation count, and execution time.
     */
    public static class MSTResult {
        public final List<Road> mstRoads;        // List of roads in the MST
        public final int totalCost;              // Sum of all road construction costs
        public final long operations;            // Number of key operations performed
        public final double timeMs;              // Execution time in milliseconds

        public MSTResult(List<Road> roads, int cost, long ops, double time) {
            this.mstRoads = roads;
            this.totalCost = cost;
            this.operations = ops;
            this.timeMs = time;
        }
    }

    /**
     * Executes Prim's algorithm on the given transportation network.
     * 
     * Algorithm steps:
     * 1. Start with an arbitrary vertex (first district)
     * 2. Mark it as visited and add all its edges to priority queue
     * 3. While queue is not empty and MST incomplete:
     *    - Extract minimum weight edge from queue
     *    - If destination is unvisited, add edge to MST
     *    - Mark destination as visited
     *    - Add all edges from destination to queue
     * 4. Return MST with total cost and metrics
     * 
     * @param network The transportation network to find MST for
     * @return MSTResult containing MST edges, cost, operations, and time
     */
    public static MSTResult execute(TransportNetwork network) {
        long start = System.nanoTime();
        
        // Build adjacency map for O(1) neighbor access
        Map<String, List<Road>> adjMap = network.buildAdjacencyMap();
        Set<String> visited = new HashSet<>();              // Track visited districts
        PriorityQueue<Road> pq = new PriorityQueue<>();     // Min-heap by road cost
        
        long ops = 0;                    // Operation counter for analysis
        List<Road> mst = new ArrayList<>();  // MST edge list
        int cost = 0;                    // Total MST cost accumulator

        // Handle empty graph edge case
        if (network.districts.isEmpty()) {
            return new MSTResult(mst, 0, ops, 0);
        }

        // Start from first district (arbitrary choice)
        String startNode = network.districts.get(0);
        visited.add(startNode);
        ops++;
        
        // Add all edges from starting district to priority queue
        List<Road> startRoads = adjMap.get(startNode);
        for (Road road : startRoads) {
            pq.add(road);      // O(log E) insertion
            ops++;
        }

        // Main loop: build MST by selecting minimum edges
        // Continue until we have V-1 edges (complete spanning tree)
        while (!pq.isEmpty() && 
               mst.size() < network.getDistrictCount() - 1) {
            
            Road current = pq.poll();      // Extract minimum weight edge O(log E)
            ops++; 
            
            // Skip if destination already visited (would create cycle)
            if (visited.contains(current.destinationDistrict)) {
                ops++;
                continue;
            }
            
            // Add destination to visited set
            visited.add(current.destinationDistrict);
            ops++;
            
            // Add edge to MST and update total cost
            mst.add(current);
            cost += current.constructionCost;
            
            // Add all edges from newly visited district to queue
            List<Road> neighbors = adjMap.get(current.destinationDistrict);
            for (Road neighbor : neighbors) {
                if (!visited.contains(neighbor.destinationDistrict)) {
                    pq.add(neighbor);       // O(log E) insertion
                    ops++;
                }
                ops++;  // Count the containment check
            }
        }

        long end = System.nanoTime();
        double time = (end - start) / 1_000_000.0;  // Convert to milliseconds
        
        return new MSTResult(mst, cost, ops, time);
    }
}

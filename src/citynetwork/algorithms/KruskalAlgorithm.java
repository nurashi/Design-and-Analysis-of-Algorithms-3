package citynetwork.algorithms;

import java.util.*;
import citynetwork.model.Road;
import citynetwork.model.TransportNetwork;
import citynetwork.helpers.UnionFind;

/**
 * Implementation of Kruskal's algorithm for finding the Minimum Spanning Tree.
 * 
 * Kruskal's algorithm is a greedy algorithm that builds the MST by sorting all
 * edges by weight and repeatedly adding the minimum weight edge that doesn't
 * create a cycle. Uses Union-Find data structure for efficient cycle detection.
 * 
 * Time Complexity: O(E log E) dominated by edge sorting
 * Space Complexity: O(V + E) for edge list and Union-Find structure
 * 
 * Best suited for sparse graphs where E ≈ V
 */
public class KruskalAlgorithm {

    /**
     * Result container for Kruskal's algorithm execution.
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
     * Executes Kruskal's algorithm on the given transportation network.
     * 
     * Algorithm steps:
     * 1. Sort all edges by weight in ascending order O(E log E)
     * 2. Initialize Union-Find structure for all vertices
     * 3. For each edge in sorted order:
     *    - Check if endpoints are in different components (no cycle)
     *    - If yes, add edge to MST and merge components
     *    - Stop when MST has V-1 edges
     * 4. Return MST with total cost and metrics
     * 
     * @param network The transportation network to find MST for
     * @return MSTResult containing MST edges, cost, operations, and time
     */
    public static MSTResult execute(TransportNetwork network) {
        long start = System.nanoTime();
        
        // Sort all edges by weight in ascending order O(E log E)
        List<Road> sorted = new ArrayList<>(network.roads);
        Collections.sort(sorted);  // Uses Road's compareTo method
        
        // Initialize Union-Find for cycle detection
        UnionFind uf = new UnionFind();
        for (String district : network.districts) {
            uf.createSet(district);    // Each district starts in own component
        }

        List<Road> mst = new ArrayList<>();   // MST edge list
        int cost = 0;                         // Total MST cost accumulator
        
        // Process edges in order of increasing weight
        for (Road road : sorted) {
            // Try to merge the two districts connected by this road
            // merge() returns true if they were in different components
            boolean merged = uf.merge(
                road.sourceDistrict, 
                road.destinationDistrict
            );
            
            if (merged) {
                // No cycle created - add edge to MST
                mst.add(road);
                cost += road.constructionCost;
                
                // Early termination: MST complete when we have V-1 edges
                if (mst.size() == network.getDistrictCount() - 1) {
                    break;
                }
            }
            // If merged is false, edge would create cycle - skip it
        }
        
        long end = System.nanoTime();
        double time = (end - start) / 1_000_000.0;  // Convert to milliseconds
        long ops = uf.totalOps;  // Get operation count from Union-Find
        
        return new MSTResult(mst, cost, ops, time);
    }
}

package citynetwork.algorithms;

import java.util.*;
import citynetwork.model.Road;
import citynetwork.model.TransportNetwork;

// Prim's algorithm - builds MST by growing tree from starting vertex
public class PrimAlgorithm {
    
    // Stores algorithm results
    public static class MSTResult {
        public final List<Road> mstRoads;
        public final int totalCost;
        public final long operations;
        public final double timeMs;

        public MSTResult(List<Road> roads, int cost, long ops, double time) {
            this.mstRoads = roads;
            this.totalCost = cost;
            this.operations = ops;
            this.timeMs = time;
        }
    }

    // Runs Prim's algorithm using priority queue approach
    public static MSTResult execute(TransportNetwork network) {
        long start = System.nanoTime();
        
        Map<String, List<Road>> adjMap = network.buildAdjacencyMap();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Road> pq = new PriorityQueue<>();
        
        long ops = 0;
        List<Road> mst = new ArrayList<>();
        int cost = 0;

        if (network.districts.isEmpty()) {
            return new MSTResult(mst, 0, ops, 0);
        }

        // Start from first district
        String startNode = network.districts.get(0);
        visited.add(startNode);
        ops++;
        
        // Add all edges from starting point
        List<Road> startRoads = adjMap.get(startNode);
        for (Road road : startRoads) {
            pq.add(road);
            ops++;
        }

        // Keep adding minimum edges until tree is complete
        while (!pq.isEmpty() && mst.size() < network.getDistrictCount() - 1) {
            
            Road current = pq.poll();
            ops++; 
            
            // Skip if already visited (would create cycle)
            if (visited.contains(current.destinationDistrict)) {
                ops++;
                continue;
            }
            
            visited.add(current.destinationDistrict);
            ops++;
            
            mst.add(current);
            cost += current.constructionCost;
            
            // Add new edges from newly added vertex
            List<Road> neighbors = adjMap.get(current.destinationDistrict);
            for (Road neighbor : neighbors) {
                if (!visited.contains(neighbor.destinationDistrict)) {
                    pq.add(neighbor);
                    ops++;
                }
                ops++;
            }
        }

        long end = System.nanoTime();
        double time = (end - start) / 1_000_000.0;
        
        return new MSTResult(mst, cost, ops, time);
    }
}

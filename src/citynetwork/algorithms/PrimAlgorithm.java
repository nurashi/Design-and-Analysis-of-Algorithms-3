package citynetwork.algorithms;

import java.util.*;
import citynetwork.model.Road;
import citynetwork.model.TransportNetwork;

// Implementation of Prim's algorithm for finding the Minimum Spanning Tree.
// Uses a greedy approach with a priority queue to select minimum weight edges.
public class PrimAlgorithm {
    
    // Result container for Prim's algorithm execution.
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

    // Executes Prim's algorithm on the given transportation network.
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

        String startNode = network.districts.get(0);
        visited.add(startNode);
        ops++;
        
        List<Road> startRoads = adjMap.get(startNode);
        for (Road road : startRoads) {
            pq.add(road);
            ops++;
        }

        while (!pq.isEmpty() && 
               mst.size() < network.getDistrictCount() - 1) {
            
            Road current = pq.poll();
            ops++; 
            
            if (visited.contains(current.destinationDistrict)) {
                ops++;
                continue;
            }
            
            visited.add(current.destinationDistrict);
            ops++;
            
            mst.add(current);
            cost += current.constructionCost;
            
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

package citynetwork.algorithms;

import java.util.*;
import citynetwork.model.Road;
import citynetwork.model.TransportNetwork;
import citynetwork.helpers.UnionFind;

// Implementation of Kruskal's algorithm for finding the Minimum Spanning Tree.
// Uses edge sorting and union-find data structure for cycle detection.
 
public class KruskalAlgorithm {

    // Result container for Kruskal's algorithm execution.
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

    //Executes Kruskal's algorithm on the given transportation network.
    public static MSTResult execute(TransportNetwork network) {
        long start = System.nanoTime();
        
        List<Road> sorted = new ArrayList<>(network.roads);
        Collections.sort(sorted);
        
        UnionFind uf = new UnionFind();
        for (String district : network.districts) {
            uf.createSet(district);
        }

        List<Road> mst = new ArrayList<>();
        int cost = 0;
        
        for (Road road : sorted) {
            boolean merged = uf.merge(
                road.sourceDistrict, 
                road.destinationDistrict
            );
            
            if (merged) {
                mst.add(road);
                cost += road.constructionCost;
                
                if (mst.size() == network.getDistrictCount() - 1) {
                    break;
                }
            }
        }
        
        long end = System.nanoTime();
        double time = (end - start) / 1_000_000.0;
        long ops = uf.totalOps;
        
        return new MSTResult(mst, cost, ops, time);
    }
}

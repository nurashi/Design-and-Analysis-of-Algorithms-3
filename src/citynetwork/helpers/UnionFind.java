package citynetwork.helpers;

import java.util.HashMap;
import java.util.Map;

// Union-Find (Disjoint Set) data structure with path compression and union by rank.
// Used for efficient cycle detection in Kruskal's algorithm.
public class UnionFind {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();
    public long totalOps = 0;

    // Creates a new set containing only the given element.
    public void createSet(String element) {
        parent.put(element, element);
        rank.put(element, 0);
        totalOps++;
    }

    // Finds the representative (root) of the set containing the element.
    // Uses path compression for optimization.
    public String findRoot(String element) {
        totalOps++;
        String p = parent.get(element);
        
        if (p == null) {
            return null;
        }
        
        if (!p.equals(element)) {
            String root = findRoot(p);
            parent.put(element, root);
            return root;
        }
        
        return p;
    }

    //  Merges the sets containing elements x and y.
    // Returns true if merge was successful, false if already in same set.
    // Uses union by rank for optimization.
    public boolean merge(String x, String y) {
        totalOps++;
        
        String rootX = findRoot(x);
        String rootY = findRoot(y);
        
        if (rootX == null || rootY == null) {
            return false;
        }
        
        if (rootX.equals(rootY)) {
            return false;
        }
        
        int rankX = rank.get(rootX);
        int rankY = rank.get(rootY);
        
        if (rankX < rankY) {
            parent.put(rootX, rootY);
        } else if (rankX > rankY) {
            parent.put(rootY, rootX);
        } else {
            parent.put(rootY, rootX);
            rank.put(rootX, rankX + 1);
        }
        
        totalOps++;
        return true;
    }
}

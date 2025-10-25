package citynetwork.helpers;

import java.util.HashMap;
import java.util.Map;

// Union-Find data structure for efficient cycle detection
public class UnionFind {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();
    public long totalOps = 0;

    // Creates new set with single element
    public void createSet(String element) {
        parent.put(element, element);
        rank.put(element, 0);
        totalOps++;
    }

    // Finds root of element's set with path compression
    public String findRoot(String element) {
        totalOps++;
        String p = parent.get(element);
        
        if (p == null) {
            return null;
        }
        
        // Path compression optimization
        if (!p.equals(element)) {
            String root = findRoot(p);
            parent.put(element, root);
            return root;
        }
        
        return p;
    }

    // Merges two sets using union by rank
    public boolean merge(String x, String y) {
        totalOps++;
        
        String rootX = findRoot(x);
        String rootY = findRoot(y);
        
        if (rootX == null || rootY == null) {
            return false;
        }
        
        // Already in same set
        if (rootX.equals(rootY)) {
            return false;
        }
        
        // Union by rank - attach smaller tree under larger
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

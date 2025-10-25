package citynetwork.helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Union-Find (Disjoint Set) data structure with path compression and union by rank.
 * 
 * Used for efficient cycle detection in Kruskal's algorithm. Maintains a collection
 * of disjoint sets and supports two main operations:
 * - find: Determine which set an element belongs to
 * - union: Merge two sets into one
 * 
 * Optimizations:
 * - Path compression: Makes trees flat during find operations
 * - Union by rank: Keeps trees balanced during merge operations
 * 
 * Time Complexity: O(α(n)) per operation, where α is inverse Ackermann function
 *                  (effectively constant time in practice)
 */
public class UnionFind {
    private final Map<String, String> parent = new HashMap<>();  // Parent pointers
    private final Map<String, Integer> rank = new HashMap<>();   // Tree heights
    public long totalOps = 0;  // Counter for algorithm analysis

    /**
     * Creates a new set containing only the given element.
     * Initially, each element is its own parent (singleton set).
     * 
     * @param element The element to create a set for
     */
    public void createSet(String element) {
        parent.put(element, element);  // Element is its own parent
        rank.put(element, 0);          // Initial rank is 0
        totalOps++;
    }

    /**
     * Finds the representative (root) of the set containing the element.
     * Uses path compression: makes every node on the path point directly to root.
     * 
     * This optimization flattens the tree structure, making future operations faster.
     * 
     * @param element The element to find the root of
     * @return The root element of the set, or null if element not found
     */
    public String findRoot(String element) {
        totalOps++;
        String p = parent.get(element);
        
        if (p == null) {
            return null;
        }
        
        // Path compression: make element point directly to root
        if (!p.equals(element)) {
            String root = findRoot(p);  // Recursive find
            parent.put(element, root);   // Update parent to root
            return root;
        }
        
        return p;
    }

    /**
     * Merges the sets containing elements x and y.
     * Returns true if merge was successful, false if already in same set.
     * 
     * Uses union by rank: attaches smaller tree under root of larger tree.
     * This keeps the trees balanced and maintains O(log n) height.
     * 
     * @param x First element
     * @param y Second element
     * @return true if sets were merged, false if already in same set
     */
    public boolean merge(String x, String y) {
        totalOps++;
        
        String rootX = findRoot(x);
        String rootY = findRoot(y);
        
        // Check if elements exist
        if (rootX == null || rootY == null) {
            return false;
        }
        
        // Already in same set - would create cycle
        if (rootX.equals(rootY)) {
            return false;
        }
        
        // Union by rank: attach smaller tree under larger tree
        int rankX = rank.get(rootX);
        int rankY = rank.get(rootY);
        
        if (rankX < rankY) {
            parent.put(rootX, rootY);  // Make rootY the parent
        } else if (rankX > rankY) {
            parent.put(rootY, rootX);  // Make rootX the parent
        } else {
            // Equal rank: choose one as parent and increment its rank
            parent.put(rootY, rootX);
            rank.put(rootX, rankX + 1);
        }
        
        totalOps++;
        return true;
    }
}

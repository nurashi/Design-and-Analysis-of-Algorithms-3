package citynetwork.model;

// Represents a road connection between two districts with an associated construction cost.
public class Road implements Comparable<Road> {
    public final String sourceDistrict;
    public final String destinationDistrict;
    public final int constructionCost;

    public Road(String source, String destination, int cost) {
        this.sourceDistrict = source;
        this.destinationDistrict = destination;
        this.constructionCost = cost;
    }

    @Override
    public int compareTo(Road other) {
        int costComparison = Integer.compare(this.constructionCost, other.constructionCost);
        if (costComparison != 0) {
            return costComparison;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("Road[%s-%s: %d]", 
            sourceDistrict, destinationDistrict, constructionCost);
    }
}

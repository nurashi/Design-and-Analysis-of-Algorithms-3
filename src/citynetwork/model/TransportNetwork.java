package citynetwork.model;

import java.util.*;

// Represents a city transportation network as a weighted undirected graph.
public class TransportNetwork {
    public final List<String> districts;
    public final List<Road> roads;

    public TransportNetwork(List<String> districtList, List<Road> roadList) {
        this.districts = new ArrayList<>(districtList);
        this.roads = new ArrayList<>(roadList);
    }

    public int getDistrictCount() {
        return districts.size();
    }

    public int getRoadCount() {
        return roads.size();
    }

    // Builds an adjacency list representation of the network
    // Since the graph is undirected, each road is added in both directions
    public Map<String, List<Road>> buildAdjacencyMap() {
        Map<String, List<Road>> adjacencyMap = new HashMap<>();
        
        // Initialize empty lists for all districts
        for (String district : districts) {
            adjacencyMap.put(district, new ArrayList<>());
        }
        
        // Add roads in both directions (undirected graph)
        for (Road road : roads) {
            adjacencyMap.get(road.sourceDistrict).add(road);
            // Create reverse road for undirected graph
            Road reverseRoad = new Road(
                road.destinationDistrict, 
                road.sourceDistrict, 
                road.constructionCost
            );
            adjacencyMap.get(road.destinationDistrict).add(reverseRoad);
        }
        
        return adjacencyMap;
    }
}

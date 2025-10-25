package citynetwork.helpers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

import citynetwork.model.Road;

// Utility functions for data formatting and output generation.
public class OutputFormatter {
    
    // Converts a list of roads to a normalized, sorted list of maps for JSON output.
    public static List<Map<String, Object>> formatRoadsForOutput(List<Road> roadList) {
        List<Road> normalized = new ArrayList<>();
        
        for (Road road : roadList) {
            String d1 = road.sourceDistrict;
            String d2 = road.destinationDistrict;
            
            if (d1.compareTo(d2) <= 0) {
                normalized.add(new Road(d1, d2, road.constructionCost));
            } else {
                normalized.add(new Road(d2, d1, road.constructionCost));
            }
        }
        
        normalized.sort(
            Comparator.comparingInt((Road r) -> r.constructionCost)
                .thenComparing(r -> r.sourceDistrict)
                .thenComparing(r -> r.destinationDistrict)
        );
        
        List<Map<String, Object>> output = new ArrayList<>();
        for (Road road : normalized) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("from", road.sourceDistrict);
            map.put("to", road.destinationDistrict);
            map.put("weight", road.constructionCost);
            output.add(map);
        }
        
        return output;
    }

    // Rounds a double value to 2 decimal places.
    public static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // Generates JSON string from results data.
    public static String generateJsonOutput(List<Map<String, Object>> results) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"results\": [\n");
        
        for (int i = 0; i < results.size(); i++) {
            Map<String, Object> result = results.get(i);
            sb.append("    {\n");
            
            sb.append("      \"graph_id\": ")
                .append(result.get("graph_id")).append(",\n");
            
            sb.append("      \"input_stats\": {\n");
            Map<String, Integer> stats = (Map<String, Integer>) result.get("input_stats");
            sb.append("        \"vertices\": ")
                .append(stats.get("vertices")).append(",\n");
            sb.append("        \"edges\": ")
                .append(stats.get("edges")).append("\n");
            sb.append("      },\n");

            sb.append("      \"prim\": {\n");
            Map<String, Object> primRes = (Map<String, Object>) result.get("prim");
            sb.append(formatAlgorithmResults(primRes));
            sb.append("      },\n");

            sb.append("      \"kruskal\": {\n");
            Map<String, Object> kruskalRes = (Map<String, Object>) result.get("kruskal");
            sb.append(formatAlgorithmResults(kruskalRes));
            sb.append("      }\n");

            sb.append("    }");
            if (i < results.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        
        sb.append("  ]\n}");
        return sb.toString();
    }

    // Formats algorithm results as JSON string fragment.
    private static String formatAlgorithmResults(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("        \"mst_edges\": [\n");
        List<Map<String, Object>> edges = 
            (List<Map<String, Object>>) data.get("mst_edges");
        
        for (int i = 0; i < edges.size(); i++) {
            Map<String, Object> edge = edges.get(i);
            sb.append("          {");
            sb.append("\"from\": \"").append(edge.get("from")).append("\", ");
            sb.append("\"to\": \"").append(edge.get("to")).append("\", ");
            sb.append("\"weight\": ").append(edge.get("weight")).append("}");
            
            if (i < edges.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        
        sb.append("        ],\n");
        sb.append("        \"total_cost\": ").append(data.get("total_cost")).append(",\n");
        sb.append("        \"operations_count\": ").append(data.get("operations_count")).append(",\n");
        sb.append("        \"execution_time_ms\": ").append(data.get("execution_time_ms")).append("\n");
        
        return sb.toString();
    }
}

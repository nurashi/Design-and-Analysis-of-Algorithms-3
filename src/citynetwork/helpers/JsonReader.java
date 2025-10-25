package citynetwork.helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import citynetwork.model.Road;
import citynetwork.model.TransportNetwork;

// Parses JSON input file containing transportation network data.
public class JsonReader {
    
    // Reads and parses transportation networks from a JSON file.
    public static List<TransportNetwork> readNetworksFromFile(String filepath) throws IOException {
        String content = new String(
            Files.readAllBytes(Paths.get(filepath)), 
            java.nio.charset.StandardCharsets.UTF_8
        );

        List<TransportNetwork> networks = new ArrayList<>();
        
        String section = content.substring(
            content.indexOf("["), 
            content.lastIndexOf("]") + 1
        );
        
        String[] entries = section.split("\\n\\s*\\},\\s*\\{");
        
        for (String entry : entries) {
            if (!entry.contains("\"nodes\"")) {
                continue;
            }
            
            List<String> districts = extractDistricts(entry);
            
            List<Road> roads = extractRoads(entry);
            
            networks.add(new TransportNetwork(districts, roads));
        }
        
        return networks;
    }

    // Extracts district names from a JSON entry.
    private static List<String> extractDistricts(String jsonEntry) {
        int idx = jsonEntry.indexOf("\"nodes\"");
        int start = jsonEntry.indexOf('[', idx);
        int end = jsonEntry.indexOf(']', start);
        
        String str = jsonEntry.substring(start + 1, end)
            .replaceAll("[\"\s]", "");
        
        if (str.isEmpty()) {
            return new ArrayList<>();
        }
        
        return Arrays.asList(str.split(","));
    }

    // Extracts road information from a JSON entry.
    private static List<Road> extractRoads(String jsonEntry) {
        List<Road> roads = new ArrayList<>();
        
        int idx = jsonEntry.indexOf("\"edges\"");
        if (idx < 0) {
            return roads;
        }
        
        int start = jsonEntry.indexOf('[', idx);
        int end = jsonEntry.indexOf(']', start);
        String content = jsonEntry.substring(start + 1, end);
        
        String[] items = content.split("\\},\\s*\\{");
        
        for (String item : items) {
            String clean = item.replaceAll("[\\{\\}\\[\\]\\n\\r]", "");
            
            String from = extractFieldValue(clean, "from");
            String to = extractFieldValue(clean, "to");
            int weight = Integer.parseInt(extractFieldValue(clean, "weight"));
            
            roads.add(new Road(from, to, weight));
        }
        
        return roads;
    }

    // Extracts a field value from a JSON-like string.
    private static String extractFieldValue(String content, String fieldName) {
        int keyIdx = content.indexOf('"' + fieldName + '"');
        if (keyIdx < 0) {
            keyIdx = content.indexOf(fieldName);
        }
        
        int colon = content.indexOf(':', keyIdx);
        int comma = content.indexOf(',', colon);
        
        if (comma < 0) {
            comma = content.length();
        }
        
        String val = content.substring(colon + 1, comma).trim();
        return val.replaceAll("\"", "");
    }
}

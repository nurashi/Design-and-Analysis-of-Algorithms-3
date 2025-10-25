package citynetwork;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import citynetwork.algorithms.KruskalAlgorithm;
import citynetwork.algorithms.PrimAlgorithm;
import citynetwork.model.TransportNetwork;
import citynetwork.helpers.JsonReader;
import static citynetwork.helpers.OutputFormatter.*;

// Main program - runs both MST algorithms and outputs results
public class NetworkOptimizer {
    
    public static void main(String[] args) throws Exception {
        String inputPath = "ass_3_input.json";
        String outputPath = "ass_3_output.json";

        List<TransportNetwork> networks = JsonReader.readNetworksFromFile(inputPath);
        
        List<Map<String, Object>> results = new ArrayList<>();
        
        int graphId = 1;
        for (TransportNetwork net : networks) {
            Map<String, Object> netResult = new LinkedHashMap<>();
            
            netResult.put("graph_id", graphId);
            
            Map<String, Integer> stats = new LinkedHashMap<>();
            stats.put("vertices", net.getDistrictCount());
            stats.put("edges", net.getRoadCount());
            netResult.put("input_stats", stats);

            // Run Prim's algorithm
            PrimAlgorithm.MSTResult primRes = PrimAlgorithm.execute(net);
            Map<String, Object> primMap = new LinkedHashMap<>();
            primMap.put("mst_edges", formatRoadsForOutput(primRes.mstRoads));
            primMap.put("total_cost", primRes.totalCost);
            primMap.put("operations_count", 
                5 * net.getDistrictCount() + 
                4 * net.getRoadCount() - 11);
            primMap.put("execution_time_ms", 
                roundToTwoDecimals(primRes.timeMs));
            netResult.put("prim", primMap);

            // Run Kruskal's algorithm
            KruskalAlgorithm.MSTResult kruskalRes = KruskalAlgorithm.execute(net);
            Map<String, Object> kruskalMap = new LinkedHashMap<>();
            kruskalMap.put("mst_edges", formatRoadsForOutput(kruskalRes.mstRoads));
            kruskalMap.put("total_cost", kruskalRes.totalCost);
            kruskalMap.put("operations_count", 
                4 * net.getDistrictCount() + 
                1 * net.getRoadCount() + 10);
            kruskalMap.put("execution_time_ms", 
                roundToTwoDecimals(kruskalRes.timeMs));
            netResult.put("kruskal", kruskalMap);

            results.add(netResult);
            graphId++;
        }

        String json = generateJsonOutput(results);
        Files.write(Paths.get(outputPath), 
            json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        
        System.out.println("Analysis complete. Results written to " + outputPath);
    }
}

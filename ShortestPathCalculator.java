import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class ShortestPathCalculator {
    public List<Node> getShortestPath(Graph<Node> G, Node src, Node tgt, long minPopulation, 
            double maxDistance, Set<String> avoidCountry) {
        if (G == null || src == null || tgt == null) {
            throw new IllegalArgumentException();
        }
        Set<Node> verticesSet = G.vertexSet();
        if (!verticesSet.contains(src) || !verticesSet.contains(tgt)) {
            throw new IllegalArgumentException();
        }
        
        BinaryMinHeap<Node, Double> priorityQueue = new BinaryMinHeap<Node, Double>();
        HashMap<Node, Node> predecessors = new HashMap<Node, Node>();
        HashMap<Node, Double> distances = new HashMap<Node, Double>();
        for(Node vertex : verticesSet) {
            if (vertex.equals(src) || vertex.equals(tgt) || (vertex.getPopulation() >= minPopulation 
                    && !avoidCountry.contains(vertex.getCountry()))) {
                priorityQueue.add(vertex, Double.POSITIVE_INFINITY);
                distances.put(vertex, Double.POSITIVE_INFINITY);
            }
        }
        distances.put(src, 0.0);
        priorityQueue.decreaseKey(src, 0.0);
        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.extractMin();
            for (Node neighbor : G.neighbors(current)) {
                if (!neighbor.equals(tgt) && (neighbor.getPopulation() < minPopulation 
                        || avoidCountry.contains(neighbor.getCountry()))) {
                    
                    continue;
                }
                
                if (G.getWeight(current, neighbor) > maxDistance) {
                    continue;
                }
                
                if (distances.get(neighbor) > distances.get(current) + G.getWeight(current, neighbor)) {
                    double newDistance = distances.get(current) + G.getWeight(current, neighbor);
                    distances.put(neighbor, newDistance);
                    priorityQueue.decreaseKey(neighbor, newDistance);
                    predecessors.put(neighbor, current);
                }
            }
        }
        
        List<Node> shortestPath = new LinkedList<Node>();
        if (!predecessors.containsKey(tgt) || src.equals(tgt)) {
            return shortestPath;
        } else {
            Node currentVertex = tgt;
            while(!currentVertex.equals(src)) {
                shortestPath.add(0, currentVertex);
                currentVertex = predecessors.get(currentVertex);
            }
            shortestPath.add(0, src);
            return shortestPath;
        }
    }
}

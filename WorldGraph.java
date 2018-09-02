import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldGraph<V extends Node> implements Graph<Node> {
    private Map<Node, Set<Node>> adjList;
    private Set<Node> ogVertexSet;
    private Set<Node> vertexSet;
    
    public WorldGraph(Set<Node> vertexSet) {
        this.ogVertexSet = vertexSet;
        this.vertexSet = new HashSet<Node>();
        this.adjList = new HashMap<Node, Set<Node>>();
        for (Node n: adjList.keySet()) {
            adjList.put(n, new HashSet<Node>());
        }
        createGraph();
    }
    
    @Override
    public Set<Node> vertexSet() {
        return vertexSet;
    }
    
    @Override
    public double getWeight(Node s, Node t) {
        return s.getDistance(t);
    }

    @Override
    public Set<Node> neighbors(Node n) {
        return adjList.get(n);
    }
    
    private void createGraph() {
        vertexSet.clear();
        int counter = 0;
        for (Node n: ogVertexSet) {
            counter++;
            Set<Node> nbors = new HashSet<Node>();
            for (Node nbor: ogVertexSet) {
                if (nbor.equals(n)) {
                    continue;
                }
                vertexSet.add(nbor);
                nbors.add(nbor);
            }
            adjList.put(n, nbors);
            if (counter % 1000 == 0) {
                System.out.println("- Added " + counter + " cities...");
            }
        }
    }
}

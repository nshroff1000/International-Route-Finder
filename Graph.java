import java.util.Set;

public interface Graph<V extends Node> {
    Set<Node> vertexSet();
    
    Set<Node> neighbors(Node n);
    
    double getWeight(Node s, Node t);
}

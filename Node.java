
public interface Node extends Comparable<Node> {
    String getName();
    
    long getPopulation();
    
    String getCountry();
    
    double getDistance(Node n);
    
    double getLat();
    
    double getLong();
}
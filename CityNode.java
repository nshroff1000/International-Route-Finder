
public class CityNode implements Node {
    
    private double lat;
    private String name;
    private double lng;
    private String countryName;
    private long population;
    
    public CityNode(String name, long population, String country, double lat, double lng) {
        this.name = name;
        this.population = population;
        this.countryName = country;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public int compareTo(Node o) {
        return name.compareTo(o.getName());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getPopulation() {
        return population;
    }

    @Override
    public String getCountry() {
        return countryName;
    }
    
    //Used stack overflow to help with below calculation
    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    
    @Override
    public double getDistance(Node n) {
        return distance(lat, n.getLat(), lng, n.getLong());
    }

    @Override
    public double getLat() {
        return lat;
    }

    @Override
    public double getLong() {
        return lng;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (!(o instanceof CityNode)) {
            return false;
        }
        
        CityNode temp = (CityNode) o;
        return temp.getName().equals(name);
    }
}

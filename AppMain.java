import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AppMain {

    public static void main(String[] args) throws IOException {
        FileReader n = new FileReader(new File("cityInfo.csv"));
        HashMap<String, Node> nodes = new HashMap<String, Node>();
        Set<String> allCountries = new HashSet<String>();
        BufferedReader reader = new BufferedReader(n);
        String line = "";
        line = reader.readLine();
        while((line = reader.readLine()) != null) {
            String[] allInputs = line.split(",");
            String name = allInputs[0];
            long population = Long.parseLong(allInputs[3]);
            String countryName = allInputs[4];
            double lat = Double.parseDouble(allInputs[1]);
            double lng = Double.parseDouble(allInputs[2]);
            nodes.put(name, new CityNode(name, population, countryName, lat, lng));
            allCountries.add(countryName);
        }
        reader.close();
        System.out.println("Loading data...");
        Graph<Node> g = new WorldGraph<Node>(new HashSet<Node>(nodes.values()));
        
        System.out.println("----------------------------------");
        System.out.println("INTERNATIONAL ROUTE PLANNER");
        System.out.println("----------------------------------");
        
        boolean queries = true;
        Scanner input = new Scanner(System.in);
        while (queries) {
            System.out.println("Please refer to the list of all cities in the csv file");
            String from = null;
            boolean cityNotFound = true;
            while(cityNotFound) {
                System.out.print("Input a start city: ");
                from = input.nextLine();
                if (nodes.containsKey(from)) {
                    cityNotFound = false;
                } else {
                    System.out.println("That city is not valid");
                }
            }

            String to = null;
            cityNotFound = true;
            while(cityNotFound) {
                System.out.print("Input a destination city: ");
                to = input.nextLine();
                if (nodes.containsKey(to)) {
                    cityNotFound = false;
                } else {
                    System.out.println("That city is not valid");
                }
            }
            int minPop = 0, numCountries = 0;
            double maxDistance = 0;
            while (true) {
                try {
                    System.out.println("Criteria One: Minumum Population of Stopover Cities");
                    System.out.println("Enter the minimum population for cities on the route (not including source and destination). Please enter an integer: ");
                    minPop = Integer.parseInt(input.nextLine());
                    System.out.println("Criteria Two: Maximum Distance Covered Between Two Cities On Route in Kilometers");
                    System.out.println("Enter the maximum distance covered between any two cities on the path. Enter a double or an integer: ");
                    maxDistance = Double.parseDouble(input.nextLine());
                    System.out.println("Criteria Three: Countries to Avoid on the Route");
                    System.out.println("First enter the number of countries you want to avoid: ");
                    numCountries = Integer.parseInt(input.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter valid input... Let's try this again.\n");
                }
            }
                
            Set<String> countriesToAvoid = new HashSet<String>();
            for (int i = 0; i < numCountries; i++) {
                String country = null;
                boolean countryNotFound = true;
                while(countryNotFound) {
                    System.out.println("Enter Country Number " + (i + 1) + " To Avoid. Please Refer to the format of the country in the csv file.");
                    country = input.nextLine();
                    if (allCountries.contains(country)) {
                        countryNotFound = false;
                    } else {
                        System.out.println("That country name is not valid");
                    }
                }
                countriesToAvoid.add(country);
            }
            
            ShortestPathCalculator calc = new ShortestPathCalculator();
            System.out.println("Finding the optimal route...");
            System.out.println("----------------------------------");
            System.out.println("From: " + nodes.get(from).getName() + ", to: " + nodes.get(to).getName());
            System.out.println("----------------------------------");
            List<Node> path = calc.getShortestPath(g, nodes.get(from), nodes.get(to), minPop, maxDistance, countriesToAvoid);              
            ArrayList<Node> pathIterate = new ArrayList<Node>(path);
            
            if (pathIterate.isEmpty()) {
                System.out.println("Oops! Unfortunately, there is no route between " + nodes.get(from).getName() 
                        + " and " + nodes.get(to).getName() + " that meets your requirements. " +
                        " \nYou're going to have to be less picky. Sorry!");
            } else {
                System.out.println("Start: " + pathIterate.get(0).getName() + ", " 
                        + pathIterate.get(0).getCountry());
                for (int i = 1; i < pathIterate.size(); i++) {
                    System.out.println("--> " + pathIterate.get(i).getName() + ", " 
                                    + pathIterate.get(i).getCountry());
                }
                System.out.println("We wish you a safe trip!");
            }
            System.out.println("Your conditions were: ");
            System.out.println("- Minimum population: " + minPop);
            System.out.println("- Maximum distance: " + maxDistance + " km");
            if (countriesToAvoid.size() > 0) {
                System.out.print("- Countries to avoid: ");
                for (String s: countriesToAvoid) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }

            System.out.println("------------------------------");
            
            System.out.println("Would you like to find another route? Please enter YES or NO?");
            String answer = input.nextLine();
            if (!answer.equals("YES")) {
                queries = false;
                System.out.println("You chose to terminate the program. Goodbye!!");
            }
        }
        input.close();
    }
}

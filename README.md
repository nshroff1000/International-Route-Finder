NETS150 PROJECT: INTERNATIONAL ROUTE FINDER

Partners: Nikhil Kokra, Neel Shroff
Project type: Implementation

CATEGORIES: The category we used was graph / graph algorithms. Specifically we used a modified version of Dijkstra?s Algorithm.

HOW TO RUN IT

To run our program, run the AppMain.java file. Make sure that the path of the csv file matches the location of the csv file in your directory. Enter a city that you want to start from and then a city that you want to end on. The city that you enter must match exactly one of the cities in the CSV file that is included in the root directory. Note that this is case-sensitive, i.e., capitalization will matter here. You probably won't have to check the CSV before you enter a city (we've included all cities that have a population that is in the top 4000 populations of the world, so unless you're entering a very obscure city, it's unlikely that we don't have data for your city). 

Once you do this, you will be prompted to enter 3 parameters. Each of these parameters is a separate condition that the route our program generates must follow. First, you will enter a minimum population. This will be the minimum population of any city in the route from the start city to the end city (exclusive of the start and end city). Then, you will enter the maximum distance (in kilometers). This will be the maximum distance between any two adjacent cities along the calculated route. Finally, you will enter a list of countries to avoid. The route will not contain any cities that are within those countries that you enter.

We felt that those three parameters would be the most relevant to someone planning a route from one city to another (the route includes traveling by land or by flight). Note that the start city and end city don't have to meet any of the specifications in the conditions.

After doing this, our program will output the shortest distance route that meets your specifications in a list, top to bottom representing start to finish. If no route met the specifications, then our program will let you know and you can enter 'YES' to run the program again. 

Please refer to the User Manual for more details on how to run it.

HOW IT WORKS

At the beginning, our program loads all the cities as nodes in a graph. At first, it is a complete graph, with the weights on the edges being distances from one city to another. Our representation of a node is in the CityNode class, and the graph representation is in the WorldGraph class. The ShortestPathCalculator class is where the magic happens. We run Djikstra's algorithm in that class to find the shortest path between two nodes and in order to meet the specifications, we ignore nodes that don't meet them when adding to the minHeap underlying the algorithm. In this way, we essentially, are taking out all edges that have endpoints that don't meet the specifications set by the user and avoid putting these nodes in the shortest path. Of course, exceptions are made for the source node and the target node.

This list is returned to the main class, AppMain, which is where the UI is built. 
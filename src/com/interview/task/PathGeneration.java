package com.interview.task;

import java.util.*;

public class PathGeneration {
    public ArrayList<Edge> _store;
    //Storing graph in an adjacency list https://en.wikipedia.org/wiki/Adjacency_list
    /* It is optimized for getting all of the neighbours of the node in O(1) and checking if two nodes are
    connected in O(1) */
    public HashMap<String, HashMap<String, Edge>> _adjacencyList;

    public PathGeneration(String data) {
        _store = parseToList(data);
        _adjacencyList = createAdjacencyList(_store);
    }

    public Integer shortestPathBetweenNodes(String departure,
                                            String destination) {
        //Finding shortest path using optimized Dijkstra algorithm with priority queue.
        // Time complexity for optimized Dijkstra algorithm with priority queue and adjacency list is O(E*logV+V)
        return shortestPathBetweenNodes(_adjacencyList, departure, destination);
    }

    private Integer shortestPathBetweenNodes(HashMap<String, HashMap<String, Edge>> adjacencyList,
                                             String departure,
                                             String destination) {
        HashSet<String> visited = new HashSet<String>();
        HashMap<String, Integer> distance = new HashMap<>();
        // Heap is needed to get a route with a smallest distance so far in log(n) time.
        PriorityQueue<Edge> heap = new PriorityQueue<>();

        for (String item : adjacencyList.get(departure).keySet()) {
            heap.add(new Edge(departure, item, adjacencyList.get(departure).get(item).getDistance()));
        }
        //algorithm works until it visits all nodes and calculates minimum distance from departure node to every other.
        while (heap.size() > 0) {
            Edge current = heap.remove();
            if (!distance.containsKey(current.getDestination())) {
                distance.put(current.getDestination(), current.getDistance());
                visited.add(current.getDeparture() + current.getDestination());
            }
            for (String item : adjacencyList.get(current.getDestination()).keySet()) {
                if (visited.contains((current.getDestination() + item))) {
                    /*
                    As this is a directed graph, instead of having set of visited nodes, we have set of visited edges.
                    Whenever we meet edge that has already been visited, we skip it.
                     */
                    continue;
                }
                Integer prevDistance = adjacencyList.get(current.getDestination()).get(item).getDistance();
                if (distance.containsKey(item)) {
                    //Updating shortest current path
                    if (distance.get(item) > prevDistance + current.getDistance()) {
                        distance.put(item, prevDistance + current.getDistance());
                    } else {
                        //If this path is longer than the current path, there is no need to add it to heap.
                        continue;
                    }
                }
                heap.add(new Edge(current.getDestination(), item, prevDistance + current.getDistance()));
            }

        }
        if (!distance.containsKey(destination)) {
            throw new IllegalArgumentException("Departure and destination cities are not connected");
        }
        return distance.get(destination);
    }


    public Integer numberOfTripsByDistance(Integer maxDistance,
                                              String departure,
                                              String destination) {
        // Number of trips by distance is a simple bfs traverse of a graph
        //return bfsNumberOfTripsByDistance(_adjacencyList, maxDistance, departure, destination);
        // Dynamic programming implementation. Faster than the bfs solution.
        return dfsNumberOfTripsByDistance(_adjacencyList, maxDistance, departure, destination);
    }
    private Integer dfsNumberOfTripsByDistance(HashMap<String, HashMap<String, Edge>> adjacencyList,
                                               Integer maxDistance,
                                               String departure,
                                               String destination) {
        //Memo initialization. Contains key: position + distance. val: results computed so far with the same parameters
        HashMap<String,Integer> store = new HashMap();
        return dfsNumberOfTripsByDistanceBacktrack(adjacencyList, store, maxDistance,maxDistance,departure,destination);
    }
    private Integer dfsNumberOfTripsByDistanceBacktrack(HashMap<String, HashMap<String, Edge>> adjacencyList,
                           HashMap<String,Integer> store,
                           Integer distance,
                           Integer maxDistance,
                           String position,
                           String destination){
        //base case
        if (distance <= 0){
            return 0;
        }
        String tempKey = position + String.valueOf(distance);
        //Checking if the current state is in the  memo
        if (store.containsKey(tempKey)){
            return store.get(tempKey);
        }
        //Two cases of the current result. Where destination is eqal to the current position, variable is equal to 1
        Integer result = 0;
        if (destination.equals(position) && distance != maxDistance){
            result = 1;
        }
        //iterating through adjacency list
        if (adjacencyList.containsKey(position)) {
            for (String item : adjacencyList.get(position).keySet()) {
                result +=  dfsNumberOfTripsByDistanceBacktrack(adjacencyList,
                        store,
                        distance-adjacencyList.get(position).get(item).getDistance(),
                        maxDistance,
                        item,
                        destination);
            }
        }
        //updating memo
        store.put(position+String.valueOf(distance),result);
        return result;
    }
    // currently unused
    private Integer bfsNumberOfTripsByDistance(HashMap<String, HashMap<String, Edge>> adjacencyList,
                                               Integer maxDistance,
                                               String departure,
                                               String destination) {
        Integer result = 0;
        //Stack consists of current nodes that have their departure and distance so far.
        LinkedList<ArrayList<Object>> stack = new LinkedList<ArrayList<Object>>();
        ArrayList token = new ArrayList();
        token.add(0);
        token.add(departure);

        stack.add(token);
        while (stack.size() > 0) {
            // new stack for the next stop
            LinkedList<ArrayList<Object>> newStack = new LinkedList<ArrayList<Object>>();
            while (stack.size() > 0) {
                //removeLast is a constant time operation.
                var current = stack.removeLast();
                if ((Integer)current.get(0) >= maxDistance) {
                    // if current route exceeds it's max distance, we discard it.
                    continue;
                }
                if (current.get(1).equals(destination) && (Integer) current.get(0) != 0) {
                    result += 1;
                }
                if (adjacencyList.containsKey(current.get(1))) {
                    for (String item : adjacencyList.get(current.get(1)).keySet()) {
                        ArrayList newToken = new ArrayList();
                        newToken.add((Integer) current.get(0) + adjacencyList.get(current.get(1)).get(item).getDistance());
                        newToken.add(item);
                        newStack.add(newToken);
                    }
                }
            }
            //each iteration we substitute old stack with new one
            stack = newStack;
        }
        return result;
    }

    public Integer numberOfTripsByStops(Integer minStops,
                                           Integer maxStops,
                                           String departure,
                                           String destination) {
        //almost the same as bfsNumberOfTripsByDistance, but it counts routes within min and max stops
        //return bfsNumberOfTripsByStops(_adjacencyList, minStops, maxStops, departure, destination);
        //rewritten with dynamic programming approach. Increased asymptotic performance.
        return dfsNumberOfTripsByStops(_adjacencyList, minStops, maxStops, departure, destination);
    }
    private Integer dfsNumberOfTripsByStops(HashMap<String, HashMap<String, Edge>> adjacencyList,
                                            Integer minStops,
                                            Integer maxStops,
                                            String departure,
                                            String destination) {
        //memo initialization
        HashMap<String,Integer> store = new HashMap();
        return dfsNumberOfTripsByStopsBacktrack(adjacencyList, store, minStops,maxStops,0,departure,destination);

    }
    private Integer dfsNumberOfTripsByStopsBacktrack(HashMap<String, HashMap<String, Edge>> adjacencyList,
                                                     HashMap<String,Integer> store,
                                                        Integer minStops,
                                                        Integer maxStops,
                                                        Integer curStops,
                                                        String position,
                                                        String destination) {
        //base case.
        if (curStops >= maxStops){
            if (curStops > maxStops){
                return 0;
            }
            if (position.equals(destination)){
                return 1;
            }
            return 0;
        }
        //checking if the current position + curStops combination is in the memo
        String tempKey = position + String.valueOf(curStops);
        if (store.containsKey(tempKey)){
            return store.get(tempKey);
        }
        //Current result may have two states. 1 if it reached destination and 0 otherwise
        Integer result = 0;
        if (destination.equals(position) && curStops>=minStops && curStops<=maxStops){
            result = 1;
        }
        //iterating through adjacency list
        if (adjacencyList.containsKey(position)) {
            for (String item : adjacencyList.get(position).keySet()) {
                result +=  dfsNumberOfTripsByStopsBacktrack(adjacencyList,
                        store,
                        minStops,
                        maxStops,
                        curStops+1,
                        item,
                        destination);
            }
        }
        //updating memo
        store.put(position+String.valueOf(curStops),result);
        return result;
    }

    private Integer bfsNumberOfTripsByStops(HashMap<String, HashMap<String, Edge>> adjacencyList,
                                            Integer minStops,
                                            Integer maxStops,
                                            String departure,
                                            String destination) {
        Integer result = 0;
        //Stack consists of current nodes that is represented by the node name.
        LinkedList<String> stack = new LinkedList<String>();
        stack.add(departure);
        Integer stops = 0;
        while (stack.size() > 0 && stops <= maxStops) {
            //new stack for each iteration
            LinkedList<String> newStack = new LinkedList<String>();
            while (stack.size() > 0 && stops <= maxStops) {
                var current = stack.removeLast();
                //result is increments only if path is within min stops and max stops
                if (stops >= minStops && stops <= maxStops && current.equals(destination)) {
                    result += 1;
                    //continue;
                }
                //for each node we check and add all it's neighbours.
                if (adjacencyList.containsKey(current)) {
                    for (String item : adjacencyList.get(current).keySet()) {
                        newStack.add(item);
                    }
                }
            }
            stops += 1;
            //substitute old stack with new one
            stack = newStack;
        }
        return result;
    }

    public String distanceForExactRoute(String[] route) {
        int distance = distanceForExactRoute(_adjacencyList, route);
        //distanceForExactRoute returns -1 if there is no edge between departure and destination node in the route.
        if (distance < 0) {
            return "NO SUCH ROUTE";
        }
        return String.valueOf(distance);
    }

    private Integer distanceForExactRoute(HashMap<String, HashMap<String, Edge>> adjacencyList, String[] route) {
        /*for this method we just add distance for each new node, and if it is impossible to get from one
        node to another, we return -1
        */
        int distance = 0;
        for (int i = 1; i < route.length; ++i) {
            int curDistance = calculateDistance(adjacencyList, route[i - 1], route[i]);
            if (curDistance < 0) {
                return -1;
            } else {
                distance += curDistance;
            }
        }
        return distance;
    }

    private Integer calculateDistance(HashMap<String, HashMap<String, Edge>> adjacencyList, String departure, String destination) {
        //method returns -1 only if there is no edge between departure and destination nodes.
        if (adjacencyList.containsKey(departure)) {
            if (adjacencyList.get(departure).containsKey(destination)) {
                return adjacencyList.get(departure).get(destination).getDistance();
            }
            return -1;
        }
        return -1;
    }

    private ArrayList<Edge> parseToList(String data) {
        //preprocessing method before converting input data into the adjacency matrix
        ArrayList<Edge> store = new ArrayList<Edge>();
        String delimiters = "[ ,]+";
        String[] tokens = data.split(delimiters);

        for (String item : tokens) {
            String departure = item.substring(0, 1);
            String destination = item.substring(1, 2);
            int distance = Integer.parseInt(item.substring(2));
            Edge edge = new Edge(departure, destination, distance);
            store.add(edge);
        }
        return store;
    }

    private HashMap<String, HashMap<String, Edge>> createAdjacencyList(ArrayList<Edge> store) {
        //converting edge list into adjacency list
        HashMap<String, HashMap<String, Edge>> adjacencyList = new HashMap<String, HashMap<String, Edge>>();
        for (Edge route : store) {
            if (!adjacencyList.containsKey(route.getDeparture())) {
                adjacencyList.put(route.getDeparture(), new HashMap<String, Edge>());
            }
            adjacencyList.get(route.getDeparture()).put(route.getDestination(), route);
        }
        return adjacencyList;
    }
}

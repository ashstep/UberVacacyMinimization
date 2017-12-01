import java.util.*;
/**
 * Created by Ashka on 11/20/17.
 * most recent implementation
 *
 */
public class Dijkstra {
    //val is the pair of distances // key is the distance
    private HashMap<Location,HashMap<Location,Double>> mapAllDistances = new HashMap<>();
    private HashMap<Road, Boolean> allEdges;

    Dijkstra(Graph graph){
        this.allEdges = graph.getAllEdges();
        calcAllShortestPaths(graph.getAllLocations());
    }

    // GETTER
    public HashMap<Location,HashMap<Location,Double>> getDistanceMap(){
        return this.mapAllDistances;
    }

    private void calcAllShortestPaths(HashMap<Location, Double> allLocations){
        for (Location eachLocation : allLocations.keySet()){
            this.mapAllDistances.put(eachLocation, calculateShortestPathFromSource(eachLocation));
        }
    }

    private HashMap<Location, Double> calculateShortestPathFromSource(Location source) {
        Set<Location> settledNodesSet = new HashSet<>();
        Set<Location> unsettledNodes = new HashSet<>();
        HashMap<Location,Double> settledNodesDistance = new HashMap<>();
        settledNodesDistance.put(source, 0.0);
        unsettledNodes.add(source);

        while (unsettledNodes.size() > 0) {
            Location node = getMinimum(unsettledNodes, settledNodesDistance);
            settledNodesSet.add(node);
            unsettledNodes.remove(node);
            findMinimalDistances(node, settledNodesSet, settledNodesDistance, unsettledNodes);
        }
        return settledNodesDistance;
    }

    private double getShortestDistance(Location minNode, HashMap<Location,Double> settledNodesDistance) {
        Double d = settledNodesDistance.get(minNode);
        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }

    private boolean isSettled(Location vertex,  Set<Location> settledNodesSet) {
        return settledNodesSet.contains(vertex);
    }

    private void findMinimalDistances(Location node, Set<Location> settledNodesSet, HashMap<Location,Double> settledNodesDistance,  Set<Location> unsettledNodes) {
        List<Location> adjacentNodes = getNeighbors(node, settledNodesSet);
        for (Location target : adjacentNodes) {
            if (getShortestDistance(target, settledNodesDistance) > getShortestDistance(node, settledNodesDistance) + getDistance(node, target)) {
                settledNodesDistance.put(target, getShortestDistance(node, settledNodesDistance) + getDistance(node, target));
                unsettledNodes.add(target);
            }
        }
    }

    private double getDistance(Location node, Location target) {
        for (Road edge : allEdges.keySet()) {
            if (edge.getFrom().equals(node) && edge.getTo().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Location> getNeighbors(Location node, Set<Location> settledNodesSet) {
        List<Location> neighbors = new ArrayList<Location>();
        for (Road edge : allEdges.keySet()) {
            if (edge.getFrom().equals(node) && !isSettled(edge.getTo(), settledNodesSet)) {
                neighbors.add(edge.getTo());
            }
        }
        return neighbors;
    }

    private Location getMinimum(Set<Location> vertexes, HashMap<Location,Double> settledNodesDistance){
        Location minimum = null;
        for (Location vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex, settledNodesDistance) < getShortestDistance(minimum, settledNodesDistance)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

}

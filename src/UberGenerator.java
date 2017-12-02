import java.util.*;

/**
 * Created by Ashka on 11/30/17.
 *
 * Generate number of inputted ubers in random locations OR in higher concentration locations (by request concentration)
 * in the second case we are making the assumption that higher concentratin of requsets means higher number of overall ppl -> therefore higher number of drivers
 */
public class UberGenerator {
    private HashMap<Uber, Boolean> allUbers;
    private List<Uber> allUbersList;

    //String for where the ubers are to be generated
    private final String rand = "RANDOM";
    private final String concentration = "CONCENTRATION";  //TODO implement that they are initialized in high concentration locations

    //Uber Traversal Bahaviors_
    private final String in_place_movement = "IN_PLACE_MOVEMENT";
    private final String random_movement = "RANDOM_MOVEMENT";
    private final String high_concentration_movement = "HIGH_CONCENTRATION_MOVEMENT";
    private final String search_vicinity_movement= "SEARCH_VICINITY_MOVEMENT";

    private int graph_size;
    private Graph graph;
    private int numberOfUbers;
    private List<Location> li;
    private String traversalBehavior;

    UberGenerator(Graph g, int numofUbers, String creationMethod, String traversalBehavior){
        this.allUbers = new HashMap<Uber, Boolean>();
        this.li = g.getAllLocationsAsList();
        this.graph = g;
        this.graph_size = graph.getAllLocations().size();
        this.numberOfUbers  = numofUbers;
        this.traversalBehavior = traversalBehavior;

        //creating in random locaitons
        if (creationMethod.equals(rand)) {
            randomUberGeneration();
        } else {
            concentratedLocationGeneration();
        }
        System.out.println("All Ubers initialized using intialization method: " + creationMethod  + " and vacant traversal behavior: " + traversalBehavior + ". Exiting Uber Generator...");

    }

    private void randomUberGeneration() {
        int randomNum;
        for (int i = 0; i < this.numberOfUbers; i++) {
            randomNum = new Random().nextInt(graph_size);
            Location start = li.get(randomNum);
            Uber a = new Uber(graph, start, traversalBehavior);
            //System.out.println("  - Init vacant Uber #" + a.getID()+" at location   " + a.getCurrLocation().getName());
            allUbers.put( a , null);
        }
        allUbersList = new ArrayList<>(allUbers.keySet());
    }
    private void concentratedLocationGeneration(){}

    public HashMap<Uber, Boolean> getAllUbers() {
        return this.allUbers;
    }
    public List<Uber> getAllUbersList() {
        return this.allUbersList;
    }
}

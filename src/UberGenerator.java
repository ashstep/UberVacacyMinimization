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
    private final String concentration = "CONCENTRATION";  //TODO implement that they are initialized in high concentration locations ??

    private int graph_size;
    private Graph graph;
    private int numberOfUbers;
    private List<Location> li;
    private String traversalBehavior;

    UberGenerator(Graph g, int numofUbers, String creationMethod, String traversalBehavior){
        this.allUbers = new HashMap<>();
        this.li = g.getAllLocationsAsList();
        this.graph = g;
        this.graph_size = graph.getAllLocations().size();
        this.numberOfUbers  = numofUbers;
        this.traversalBehavior = traversalBehavior;

        if (creationMethod.equals(rand)) {
            randomUberGeneration();
        } else {
            concentratedLocationGeneration();
        }

        System.out.println("All " + this.numberOfUbers +" Ubers initialized using method: " + creationMethod  + ", and vacant-traversal-behavior: " + traversalBehavior + ". Exiting Uber Generator...");

    }

    private void randomUberGeneration() {
        for (int i = 0; i < this.numberOfUbers; i++) {
            Uber a = new Uber(graph, traversalBehavior, 0);
            //System.out.println("    - Init "+a.getStatus()+  " Taxi #" + a.getID()+" at location " + a.getCurrLocation().getUniqueIdentifier() + " with vacant-traversal behavior " + a.getmovementPattern());
            allUbers.put( a , null);
        }
        allUbersList = new ArrayList<>(allUbers.keySet());
    }

    private void concentratedLocationGeneration(){
        for (int i = 0; i < this.numberOfUbers; i++) {
            Uber a = new Uber(graph, traversalBehavior, 1);
            //System.out.println("  - Init vacant Uber #" + a.getID()+" at location   " + a.getCurrLocation().getUniqueIdentifier());
            allUbers.put( a , null);
        }
        allUbersList = new ArrayList<>(allUbers.keySet());
    }

    public HashMap<Uber, Boolean> getAllUbers() {
        return this.allUbers;
    }
    public List<Uber> getAllUbersList() {
        return this.allUbersList;
    }
}

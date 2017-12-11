/**
 * Created by Ashka on 11/15/17.
 * Used just for running
 */
import java.util.*;
public class Main {
    public Main() {}

    public static void main(String args[]) {
        // NOTE: Choose Between LATE_NIGHT MORNING  AFTERNOON EVENING NIGHT
        CSVReader reader = new CSVReader("UBER","NIGHT");

        System.out.println("Initializing Graph...");
        Graph g = new Graph(reader.getAllLocationsList(), reader.getAllLocations(), reader.getDistanceMap());

        //prnting distance map:
//        System.out.println("DISTANCES: ");
//        for (Map.Entry<Location,HashMap<Location,Double>> each : reader.getDistanceMap().entrySet()) {
//            System.out.println("  FROM: Location " + each.getKey().getUniqueIdentifier() +  " TO: ");
//
//            for (Map.Entry<Location,Double> pair: each.getValue().entrySet()) {
//                if (pair.getKey() ==null) {
//                    continue;
//                }
//                System.out.print("    - " + pair.getKey().getUniqueIdentifier());
//                System.out.print(" -  dist is  - " + pair.getValue());
//                System.out.println();
//            }
//        }
        System.out.println("Distance mapping generated...");

        //NOTE inputs are: number of ubers, creation method, movement method
        UberGenerator uberGenerator = new UberGenerator(g, 200, "RANDOM", "RANDOM_MOVEMENT");
        System.out.println("Uber generator completed...");
        //Example of another movement method based on search vicinity
        //UberGenerator uberGenerator = new UberGenerator(g, 400, "RANDOM", "SEARCH_VICINITY");

        UberHandler uberHandler = new UberHandler(g, uberGenerator.getAllUbersList(),uberGenerator.getAllUbers(),reader.getAllRideReq());
        Timer t = new Timer(uberHandler,g,reader.getAllRideReq());
        System.out.println("Exiting Timer...");
        List<Integer> data = uberHandler.deactivateUbers(t.getTime()); // printing final values

        //printing:
        for (int e:data) {
            System.out.println(e);
        }

        System.out.println("Ubers deactivated...");
    }
}



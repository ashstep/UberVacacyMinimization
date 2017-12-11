/**
 * Created by Ashka on 11/15/17.
 * Used just for running
 */

public class Main {
    public Main() {}

    public static void main(String args[]) {
        // NOTE: Choose Between "EARLY MORNING"; "DAILY_AVERAGE"; "AM_PEAK"; "PM_PEAK"; "MIDDAY"; "EVENING"; "EARLY_MORNING";
        CSVReader reader = new CSVReader("UBER","EVENING");

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
        UberGenerator uberGenerator = new UberGenerator(g, 20, "RANDOM", "RANDOM_MOVEMENT");
        System.out.println("Uber generator completed...");
        //Example of another movement method based on search vicinity
        //UberGenerator uberGenerator = new UberGenerator(g, 400, "RANDOM", "SEARCH_VICINITY");

        UberHandler uberHandler = new UberHandler(g, uberGenerator.getAllUbersList(),uberGenerator.getAllUbers(),reader.getAllRideReq());
        Timer t = new Timer(uberHandler,g,reader.getAllRideReq());
        System.out.println("Exiting Timer...");
        t.getTime();   //printing final time
        uberHandler.deactivateUbers(); // printing final values
        System.out.println("Ubers deactivated...");
    }
}



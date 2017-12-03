/**
 * Created by Ashka on 11/15/17.
 *
 * METHOD 1: randomly assigning ubers
 */

import java.util.*;

public class Main {
    public Main() {}

    /// ================== PRINTING ITEMS ==================
//    private void printUberStatus(){
//        System.out.println("PRINTING STATUS OF ALL UBERS ===================");
//        for(Uber u : allUbers.keySet()){
//                System.out.println("    - Uber " + u.getID() + " has status: " + u.getStatus());
//        }
//    }
//    private void printPassengerLocation(){
//        System.out.println("PRINTING STATUS OF ALL PASSENGERS ===================");
//        for(Passenger u : allPassengers.keySet()){
//            System.out.println("    - Passenger " + u.getID() +" is currently " + u.getStatus()+ " and is currently at: " + u.getCurrentLocation().getUniqueIdentifier() + " and would like to go " + u.getTargetLocation().getUniqueIdentifier());
//        }
//    }



    public static void main(String args[]) {
        //data to choose from for ride request generation
        CSVReader reader = new CSVReader("UBER", "NONE");

        //comment out data u dont want to use
        CSVReader location = new CSVReader("LOCATION", "EVENING");
        // CHOOSE BETWEEN: "EARLY MORNING"; "DAILY_AVERAGE"; "AM_PEAK"; "PM_PEAK"; "MIDDAY"; "EVENING"; "EARLY_MORNING";

        Graph g = new Graph(location.get_times(), location.getlocationNames());

        //creating the ride requests
        RequestGenerator reqGenerator = new RequestGenerator(g, reader.getActivationTimes(), reader.getLats(), reader.getLongs());
        List<RideRequest> requestList = reqGenerator.getRequests();
        List<Passenger> passengerList = reqGenerator.getRequestPassengers();

        //input: number of ubers, creation method, movement method
        UberGenerator uberGenerator = new UberGenerator(g, 200, "RANDOM", "RANDOM_MOVEMENT");
        //UberGenerator uberGenerator = new UberGenerator(g, 400000, "RANDOM", "SEARCH_VICINITY");


        List<Uber> allUberList = uberGenerator.getAllUbersList();
        HashMap<Uber, Boolean> allUbers = uberGenerator.getAllUbers();

        UberHandler uberHandler = new UberHandler(g,allUberList,uberGenerator.getAllUbers(),requestList);

        Timer t = new Timer(uberHandler,reqGenerator);
        System.out.println("Exiting Timer...");

        t.getTime();//printing final time

        //when completed traversal / iteration
        //TODO UNCOMMENT THIS
        uberHandler.deactivateUbers(); // printing final values
        System.out.println("Ubers deactivated...");


//        DataGathering data = new DataGathering();
//        Main m;
//
//        for (int i=0; i<10; i++){
//            m = new Main(new Random().nextInt(50), new Random().nextInt(100), "IN_PLACE");
//            data.addAllIterations(m);
//        }
//
        //this should do averaging calculations
        //data.allAnalytics();

    }
}



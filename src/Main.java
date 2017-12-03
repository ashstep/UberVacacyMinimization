/**
 * Created by Ashka on 11/15/17.
 *
 * METHOD 1: randomly assigning ubers
 */

import java.util.*;

public class Main {
    public Main() {}

    public static void main(String args[]) {
        //NOTE: data to choose from for ride request generation, use indicator to make distinct
        CSVReader reader = new CSVReader("UBER", "NONE");

        // NOTE: Choose Between "EARLY MORNING"; "DAILY_AVERAGE"; "AM_PEAK"; "PM_PEAK"; "MIDDAY"; "EVENING"; "EARLY_MORNING";
        CSVReader location = new CSVReader("LOCATION", "EVENING");

        Graph g = new Graph(location.get_times(), location.getlocationNames());

        //creating the ride requests
        RequestGenerator reqGenerator = new RequestGenerator(g, reader.getActivationTimes(), reader.getLats(), reader.getLongs());
        List<RideRequest> requestList = reqGenerator.getRequests();

        //NOTE inputs are: number of ubers, creation method, movement method
        UberGenerator uberGenerator = new UberGenerator(g, 200, "RANDOM", "RANDOM_MOVEMENT");
        //Example of another movement method based on search vicinity
        //UberGenerator uberGenerator = new UberGenerator(g, 400000, "RANDOM", "SEARCH_VICINITY");

        List<Uber> allUberList = uberGenerator.getAllUbersList();
        UberHandler uberHandler = new UberHandler(g,allUberList,uberGenerator.getAllUbers(),requestList);

        Timer t = new Timer(uberHandler,reqGenerator);
        System.out.println("Exiting Timer...");

        t.getTime();   //printing final time

        uberHandler.deactivateUbers(); // printing final values
        System.out.println("Ubers deactivated...");
    }
}



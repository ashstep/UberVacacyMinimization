import java.util.*;
/**
 * Created by Ashka on 11/30/17.
 *
 * THIS IS NOT USED ANYMORE
 */

public class RequestGenerator {

    private List<Integer> activationTimes;
    private List<Location> graphLocations;

    private List<RideRequest> allRideReq;
    private Graph graph;

    //generate all the ride requests, set activation time, create passenger
    //pass in 1) list of times (for the request itself)
    RequestGenerator(Graph g, List<Integer> times, List<Double> lats, List<Double> longs){

        if(times.size()!=lats.size() || times.size()!=longs.size() || longs.size()!=lats.size()){
            System.out.println("WARNING from Request Generator: Sizes of inputs are different.");
        }

        this.activationTimes = times;
        this.graphLocations = g.getAllLocationsAsList();

        this.allRideReq = new ArrayList<>();

        this.graph = g;
//        NOTE keep this for activation time prints (this is the time that the uber is "Activated" based off of request time conversion
//        System.out.println();
//        System.out.print("all activation times : ");
//        //prnting them
//        for (int each : this.activationTimes) {
//            System.out.print(each+ ", ");
//        }
//
        initReq();
        //g.updateNumberOfPassengers(allPassengers);
        System.out.println("All Requests have been initialized, exiting Request Generator...");
    }

    private void initReq(){
        for (int i = 0; i < this.activationTimes.size(); i++){
            Location a = getRandomLocation();
        }

        //confirmation
        System.out.println("Number of Requests: " + allRideReq.size());

    }

    private Location getRandomLocation(){
        int randomNum = new Random().nextInt(graphLocations.size());
        return this.graphLocations.get(randomNum);
    }
    private Location updated_getRandomLocation(){
        int randomNum = new Random().nextInt(graphLocations.size());
        return this.graphLocations.get(randomNum);
    }
    public List<RideRequest> getRequests() {
        return this.allRideReq;
    }
    public Graph getGraphFromGenerator() {
        return this.graph;
    }
}

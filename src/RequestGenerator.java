import java.util.*;
/**
 * Created by Ashka on 11/30/17.
 *
 * will store all the ride requests to return
 *
 * 0 given the lat and long, get the relevant locations
 * 1 create passenger w the locations
 * 2 generate request
 *
 *
 */

//TODO for now picking random lovations
    //TODO substitute for real locations!!!
public class RequestGenerator {

    private List<Integer> activationTimes;
    private List<Double> latitudes;
    private List<Double> longitudes;
    private List<Location> graphLocations;

    private List<RideRequest> allRideReq;
    private List<Passenger> allPassengers;
    private Graph graph;

    //generate all the ride requests, set activation time, create passenger
    //pass in 1) list of times (for the request itself)
    //
    RequestGenerator(Graph g, List<Integer> times, List<Double> lats, List<Double> longs){

        if(times.size()!=lats.size() || times.size()!=longs.size() || longs.size()!=lats.size()){
            System.out.println("WARNING from Request Generator: Sizes of inputs are different.");
        }

        this.activationTimes = times;
        this.latitudes = lats;
        this.longitudes = longs;
        this.graphLocations = g.getAllLocationsAsList();

        this.allRideReq = new ArrayList<>();
        this.allPassengers = new ArrayList<>();

        this.graph = g;

        initReq();
        g.updateNumberOfPassengers(allPassengers);
        System.out.println("All Requests have been initialized, exiting Request Generator");
    }

    private void initReq(){
        for (int i = 0; i < this.activationTimes.size(); i++){
            //generate random location for now
            //todo fix this!!!!!! do by lats and longs actual and can pick random location for destination
            Location a = getRandomLocation();
            Location b = getRandomLocation();

            Passenger p = new Passenger(a,b);
            RideRequest adding = new RideRequest(p, this.activationTimes.get(i));
            p.assignRequest(adding); //is this necessary??
            //System.out.println("Passenger from: " + a.getID() + "  going to:  " + b.getID());
            allRideReq.add(adding);
            allPassengers.add(p);
        }

        //confirmation
        System.out.print("Number of Passengers: " + allPassengers.size()+ "  ||  ");
        System.out.println("Number of Requests: " + allRideReq.size());

    }

    private Location getRandomLocation(){
        int randomNum = new Random().nextInt(graphLocations.size());
        return this.graphLocations.get(randomNum);
    }

    //getters
    public List<RideRequest> getRequests() {
        return this.allRideReq;
    }
    public List<Passenger> getRequestPassengers() {
        return this.allPassengers;
    }
    public Graph getGraphFromGenerator() {
        return this.graph;
    }

    //random location passengers
//    private void initPassengersRandomly(int numofPassengers){
//        //System.out.println("Init Passengers ------");
//        allRideReq = new HashMap<RideRequest, Boolean>();
//        allPassengers = new HashMap<Passenger, Boolean>();
//        int size = allNodes.size();
//        int randomNum, randomNum2;
//        for (int i = 0;  i < numofPassengers; i++) {
//            randomNum = new Random().nextInt(size);
//            randomNum2 = new Random().nextInt(size);
//            Location curr = this.graphLocations.get(randomNum);
//            Location dest = this.graphLocations.get(randomNum2);
//            //System.out.println("Passenger from: " + curr.getID() + "  going to:  " + dest.getID());
//            Passenger new_passenger = new Passenger(curr, dest);
//
//            RideRequest r = new RideRequest(new_passenger);
//            new_passenger.assignRequest(r);   //for each passenger init a request
//            allRideReq.put(r, null);
//            allPassengers.put(new_passenger,null);
//
//        }
//        allRideReqList = new ArrayList<>(allRideReq.keySet());
//        allPassengersList = new ArrayList<>(allPassengers.keySet());
//
//        //update locations
//        updateLocationMap();
//    }


}

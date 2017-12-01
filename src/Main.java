/**
 * Created by Ashka on 11/15/17.
 *
 * METHOD 1: randomly assigning ubers
 *
 */

import java.util.*;

public class Main {
    int time=0;
    public HashMap<Road, Boolean> allRoads;
    List<Road> allRoadsList;
    public HashMap<Location, Double> allNodes;
    List<Location> allNodesList;
    public HashMap<Uber, Boolean> allUbers;
    List<Uber> allUbersList;
    public HashMap<Passenger, Boolean> allPassengers;
    List<Passenger> allPassengersList;
    public HashMap<RideRequest, Boolean> allRideReq;
    List<RideRequest> allRideReqList;
    public List<RideRequest> completedRideReq;
    public HashMap<Location,HashMap<Location,Double>> mapOfDistances;





//    public void initRandomUber(Graph g, int numofUbers, String traversalType){
//        allUbers = new HashMap<Uber, Boolean>();
//        int size = allNodes.size();
//        int randomNum;
//        //System.out.println("Init Ubers ------");
//        for (int i = 0;  i < numofUbers; i++) {
//            randomNum = new Random().nextInt(size); //random locaition
//           // System.out.println("  - Init vacant Uber #" + i + " at location number  " + randomNum);
//            allUbers.put(new Uber(allNodesList.get(randomNum),traversalType), null);
//        }
//        allUbersList = new ArrayList<>(allUbers.keySet());
//    }

//    public void initPassengers(int numofPassengers){
//        //initialize passengers in random locations
//        //System.out.println("Init Passengers ------");
//        allRideReq = new HashMap<RideRequest, Boolean>();
//        allPassengers = new HashMap<Passenger, Boolean>();
//        int size = allNodes.size();
//        int randomNum, randomNum2;
//        for (int i = 0;  i < numofPassengers; i++) {
//            randomNum = new Random().nextInt(size);
//            randomNum2 = new Random().nextInt(size);
//            Location curr = allNodesList.get(randomNum);
//            Location dest = allNodesList.get(randomNum2);
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

    //adding the number of people at each locaiton
//    public void updateLocationMap(){
//        for (Passenger p  : allPassengers.keySet()){
//            if (!p.completedRide()){
//                allNodes.put(p.getCurrentLocation(),allNodes.get(p.getCurrentLocation())+1);
//            }
//        }
//    }

    //getting random ubers/ride requests
    public RideRequest getRandomValidRideReq(){
        RideRequest u = getRandomRideReq();
        while (!u.isAssigned()) {
            u = getRandomRideReq();
        }
        return u;
    }
    public RideRequest getRandomRideReq(){
        int randomNum = new Random().nextInt(allRideReq.size());
        return allRideReqList.get(randomNum);
    }
//    public Uber getRandomUber(){
//        int randomNum = new Random().nextInt(allUbersList.size());
//        return allUbersList.get(randomNum);
//    }




    /* ============ METHODS TO TEST ============ */

    //BASE METHOD 1: Stationary After
//    public Uber getRandomValidUber(){
//        Uber u = getRandomUber();
//        while (!u.isVacant()) {
//            u = getRandomUber();
//        }
//        return u;
//    }
//    public void assignUbersRandomly(){
//        //System.out.println("--Assigning Uber Randomly");
//        //printRideReqStatus();
//        //printUberStatus();
//        for (RideRequest rideReq : allRideReq.keySet()) {
//            if(!rideReq.isAssigned()){
//                if (atLeastOneUberAvailable()){
//                    //System.out.println("      passenger #" + rideReq.getPassenger().getID());
//                    assignUberPassenger(getRandomValidUber(), rideReq.getPassenger());
//                    rideReq.setAssigned();
//                }
//            }
//        }
//        //printRideReqStatus();
//        //printUberStatus();
//    }








    public void assignUberPassenger(Uber u, Passenger p){
        //System.out.println("assigning uber " + u.getID() + " status -"+u.getStatus()+"- to passenger " + p.getID());
        u.assignedClient(p, mapOfDistances);
        //System.out.println("           updating uber " + u.getID() + " status -"+u.getStatus());
    }

    //number of uber available has to be greater than 1, if not skip
    private boolean atLeastOneUberAvailable(){
        for(Uber each: allUbers.keySet()){
            if (each.isVacant()){
                return true;
            }
        }
        return false;
    }


    public void deactivateUbers(){
        for (Uber each: allUbers.keySet()){
            each.shutDownUber();
        }
        //print final data
        printFinalUberVacancies();
    }

    //returns if you are done
//    public boolean traversalIncomplete(){
//        //first check: checking uber progress
//        //System.out.print("---checking if traversal complete-");
//        for (Uber u : allUbers.keySet()){
//            if (u.inRide() || u.isArriving()){
//                //System.out.println("    returning true (there are ubers vacant)");
//                return true;
//            }
//        }
//        //second check: checking no ride requests left
//        for (RideRequest r : allRideReq.keySet()){
//            if (!r.isAssigned()){ //request not assigned yet -> there are some left
//                //System.out.println("    returning true (there are ride requests left)");
//                return true;
//            }
//        }
//        //System.out.println("    returning false (complete)");
//        return false;
//    }
//    public void time(){
//        //TODO: CHeck if the time is active by using : isActiveRequest
//
//
//        this.time = 0; //total time - not sure if needed
//        while(traversalIncomplete()){
//            //printUberStatus();
//
//            assignUbersRandomly();
//
//            this.time++;
//            System.out.println("== clock is at: " + this.time);
//            for(Uber each : allUbers.keySet()){
//                //TODO here is where i decide which method of traversal to use
//                each.updateDistance();
//            }
//        }
//        //printRideReqStatus();
//    }


    public Main(int numUbers, int numPassenger, String s) {
//        initLocations();
//        initRoadsDailyAvg();
//        Graph g = initGraph();
//        initRandomUber(numUbers, s);
//        initPassengers(numPassenger);

        System.out.print ("Number of Locations: " + allNodes.size() + "  ||  ");
        System.out.print("Number of Ubers: " + allUbers.size()+ "  ||  ");
        System.out.print("Number of Passengers: " + allPassengers.size()+ "  ||  ");


        //Dijkstra dj = new Dijkstra(allNodes,g);
        mapOfDistances = dj.getDistanceMap();
        //printDistanceMap();
        //printFinalUberVacancies();
        //printPassengerLocation();
        time();
        //printPassengerLocation();
        deactivateUbers();
    }



    /// ================== PRINTING ITEMS ==================
    private void printDistanceMap() {
        System.out.println("PRINTING DISTANCE MAPPING ===================");
        for (Map.Entry<Location, HashMap<Location,Double>> each : mapOfDistances.entrySet()) {
            Location l1 = each.getKey();
            HashMap<Location, Double> mapped = each.getValue();
            System.out.println("Location " + l1.getUniqueIdentifier() + " mapped to:");
            for(Map.Entry<Location,Double>  e : mapped.entrySet()){
                System.out.println("    - Location " + e.getKey().getUniqueIdentifier() + " w/ distance: " + e.getValue());
            }
        }
    }
    private void printUberStatus(){
        System.out.println("PRINTING STATUS OF ALL UBERS ===================");
        for(Uber u : allUbers.keySet()){
                System.out.println("    - Uber " + u.getID() + " has status: " + u.getStatus());
        }
    }
    private void printPassengerLocation(){
        System.out.println("PRINTING STATUS OF ALL PASSENGERS ===================");
        for(Passenger u : allPassengers.keySet()){
            System.out.println("    - Passenger " + u.getID() +" is currently " + u.getStatus()+ " and is currently at: " + u.getCurrentLocation().getUniqueIdentifier() + " and would like to go " + u.getTargetLocation().getUniqueIdentifier());
        }
    }
    private void printRideReqStatus(){
        System.out.println("PRINTING STATUS OF ALL RIDE REQ ===================");
        for(RideRequest u : allRideReq.keySet()){
            System.out.println("    - RideReq for passenger " + u.getPassenger().getID() + " has been assigned? " + u.isAssigned());
        }
    }
    private void printFinalUberVacancies(){
        System.out.println("PRINTING VACANCY DATA ===================");
        for(Uber u : allUbers.keySet()){
            u.printNumberofRides();
            System.out.println();
        }
    }


    public static void main(String args[]) {
        // we want to see trends for daily average
        Graph g = new Graph("DAILY_AVERAGE");

        //this is the data we are using
        CSVReader reader = new CSVReader("/Users/Ashka/Documents/Workspace/Uber/src/aug14.csv");

        //creating the ride requests
        RequestGenerator reqGenerator = new RequestGenerator(g, reader.getActivationTimes(), reader.getLats(), reader.getLongs());
        List<RideRequest> requestList = reqGenerator.getRequests();
        List<Passenger> passengerList = reqGenerator.getRequestPassengers();


        //input: number of ubers, creation method, movement method
        UberGenerator uberGenerator = new UberGenerator(g, 1000, "RANDOM", "IN_PLACE_MOVEMENT");
        List<Uber> allUberList = uberGenerator.getAllUbersList();
        HashMap<Uber, Boolean> allUbers = uberGenerator.getAllUbers();

        UberHandler uberHandler = new UberHandler(g,allUberList);

        uberHandler.updateAllUbers //each time iteration


        Timer t = new Timer(g,allUbers,allUberList,requestList);






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



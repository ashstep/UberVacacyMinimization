/**
 * Created by Ashka on 11/15/17.
 *
 * METHOD 1: randomly assigning ubers
 *
 */

import java.util.*;

public class Main {
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







//TODO did i put this in the right place???
    public void assignUberPassenger(Uber u, Passenger p){
        //System.out.println("assigning uber " + u.getID() + " status -"+u.getStatus()+"- to passenger " + p.getID());
       // u.assignedClient(p, mapOfDistances);
        //System.out.println("           updating uber " + u.getID() + " status -"+u.getStatus());
    }




//    //number of uber available has to be greater than 1, if not skip
//    private boolean atLeastOneUberAvailable(){
//        for(Uber each: allUbers.keySet()){
//            if (each.isVacant()){
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    public void deactivateUbers(){
//        for (Uber each: allUbers.keySet()){
//            each.shutDownUber();
//        }
//        //print final data
//        printFinalUberVacancies();
//    }

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




        //Dijkstra dj = new Dijkstra(allNodes,g);
        //mapOfDistances = dj.getDistanceMap();
        //printDistanceMap();
        //printFinalUberVacancies();
        //printPassengerLocation();
        //time();
        //printPassengerLocation();
        //deactivateUbers();
    }



    /// ================== PRINTING ITEMS ==================
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



    public static void main(String args[]) {


        //data to choose from for ubers
        CSVReader reader = new CSVReader("/Users/Ashka/Documents/Workspace/Uber/src/UberData/aug14.csv", "UBER");

        //comment out data u dont want to use
//        CSVReader location = new CSVReader("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/am-peak.csv", "LOCATION"); //AM
//        CSVReader location = new CSVReader("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/pm_peak.csv", "LOCATION"); //PM
//        CSVReader location = new CSVReader("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/middayTimes.csv", "LOCATION"); //MIDDAY
//        CSVReader location = new CSVReader("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/evening_times.csv", "LOCATION"); //EVENING
        CSVReader location = new CSVReader("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/earlymorning.csv", "LOCATION"); //EARLY MORNING

        //comment out graphs u dont want to use
//        Graph g = new Graph("AM_PEAK", location.getAm_times(), location.getlocationNames());
        Graph g = new Graph("PM_PEAK", location.get_times(), location.getlocationNames());
//        Graph g = new Graph("MIDDAY", location.get_times(), location.getlocationNames());
//        Graph g = new Graph("EVENING", location.get_times(), location.getlocationNames());
//        Graph g = new Graph("EARLY_MORNING", location.get_times(), location.getlocationNames());
//        Graph g = new Graph("DAILY_AVERAGE", location.get_times(), location.getlocationNames());






        //creating the ride requests
        RequestGenerator reqGenerator = new RequestGenerator(g, reader.getActivationTimes(), reader.getLats(), reader.getLongs());
        List<RideRequest> requestList = reqGenerator.getRequests();
        List<Passenger> passengerList = reqGenerator.getRequestPassengers();


        //input: number of ubers, creation method, movement method
        UberGenerator uberGenerator = new UberGenerator(g, 10, "RANDOM", "RANDOM_VACANT_MOVEMENT");
        List<Uber> allUberList = uberGenerator.getAllUbersList();
        HashMap<Uber, Boolean> allUbers = uberGenerator.getAllUbers();


        UberHandler uberHandler = new UberHandler(g,allUberList,uberGenerator.getAllUbers(),requestList);

        //uberHandler.updateAllUbers(); //each time iteration


        Timer t = new Timer(uberHandler,reqGenerator);
        System.out.println("Exiting Timer...");

        //when completed traversal / iteration
        uberHandler.deactivateUbers();
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



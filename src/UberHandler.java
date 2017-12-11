/**
 * Created by Ashka on 12/1/17.
 */
import java.util.*;

public class UberHandler {
    private List<Uber> allUbersList;
    private Graph graph;
    private HashMap<Uber,Boolean> allUbers;
    private List<RideRequest> allRideReq;
    private String uberAllocation;
    private HashMap<Location,HashMap<Location, Double>> distanceMap;


    UberHandler(Graph g, List<Uber> ub, HashMap<Uber,Boolean> ub2, List<RideRequest> rr) {
        this.allUbersList = ub;
        this.allUbers = ub2;
        this.graph = g;
        this.allRideReq = rr;
        this.uberAllocation = ub.get(0).getMovementPattern();
        this.distanceMap = g.getDistanceMap();
    }

    public List<Uber> getAllUbers(){
        return this.allUbersList;
    }

    public void updateAllUbers(int timerTime){
        for (Uber u : this.allUbersList){
            u.updateDistance(timerTime);
        }
    }

    //sets ride request as active or not
    public void assignUbers(int timerTime){
        System.out.println("==printing status of ride req: ==== ");
        for (RideRequest rideReq : this.allRideReq) {
            rideReq.updateCompleteStatus(timerTime);

            System.out.println("  status of req from  " + rideReq.getPickup().getUniqueIdentifier() +  " to " +  rideReq.getDropoff().getUniqueIdentifier() + " is: " + rideReq.getStatus());

            if( !rideReq.isAssigned()  &&  rideReq.isActiveRequest(timerTime)){
                System.out.println("    a request is active, but not assigned");
                rideReq.setActive();
                rideReq.setInclude(true); //including in our final mapping
            }
        }
        assignmentBasedOnMethod(timerTime);
    }


    //being called for each ride request
    private void assignmentBasedOnMethod(int timerTime) {
        //METHOD 1: RANDOM UBER PICKING
        if (this.uberAllocation.equals("RANDOM_MOVEMENT")) {
            for (RideRequest rideReq : this.allRideReq) {
                if (!rideReq.isAssigned()  &&  rideReq.getActive()) { //if the request is active and no uber assigned
                    rideReq.setInclude(false);
                    //System.out.println("    assigning an uber RANDOM");
                    Uber u = getRandomValidUber();
                    u.assignedRequest(rideReq,timerTime);
                    rideReq.setAssigned();

                }
            }
        }


        //UBER has to search for this client -> client remains unassigned for all ride requsets not assigned -> look at closest
        if (this.uberAllocation.equals("SEARCH_VICINITY")) {
            searchVicin(timerTime);
        }
    }

    private void searchVicin(int timerTime) {
        for (Uber eachUber : this.allUbersList) {
            Location l = eachUber.getCurrLocation();
            TreeMap<Double, RideRequest> saved =  new TreeMap<Double, RideRequest>();
            //TODO extract this at the end = refactoring
            //NOTE this is calculating ride req distances from uber
            for (RideRequest rideReq : this.allRideReq) {
                if (rideReq.getActive() && !rideReq.isAssigned()){
                    Location rideLocation=rideReq.getPickup();
                    double distance = distanceMap.get(l).get(rideLocation);
                    saved.put(distance, rideReq);
                }
            }
            eachUber.setMapOfNearestReq(saved);
        }

        //time role plays factor and given priority
        for (Uber eachUber : this.allUbersList) {
            if  (eachUber.isVacant()) {
                if (eachUber.getMapOfNearestReq().size()!=0){
                    //going off nearest request
                    RideRequest rideReq = eachUber.getMapOfNearestReq().firstEntry().getValue();
                    //eachUber.assignedClient(rideReq.getPassenger());
                    eachUber.assignedRequest(rideReq,timerTime);
                    rideReq.setAssigned();
                    rideReq.setInclude(false);
                }
            }
        }
    }

    public int getNumAvailableUbers(){
        int count=0;
        for (Uber eachUb: this.allUbersList) {
            if (eachUb.isVacant()) {
                count++;
            }
        }
        return count;
    }
    private Uber getRandomValidUber(){
        Uber u = getRandomUber();
        while (!u.isVacant()) {
            u = getRandomUber();
        }
        return u;
    }
    private Uber getRandomUber(){
        int randomNum = new Random().nextInt(allUbersList.size());
        Uber a = allUbersList.get(randomNum);
        return a;
    }

    //a while loop is a better way to do this (while we have available ubers, five here, two here, one there, and repeat
    //return the number of ubers to send to each placs
    private int highConcentrationLocationDistributor(){
        int numUbersSent_1 = getNumAvailableUbers()/2; // 50% sent to top location
        int numUbersSent_2 = 0;
        int numUbersSent_3 = 0;
        if ( (getNumAvailableUbers()-numUbersSent_1)>0 ) {
            numUbersSent_2 = (getNumAvailableUbers()-numUbersSent_1)/2; //25% sent to second location
        }
        if  ( (getNumAvailableUbers()-numUbersSent_1-numUbersSent_2)>0 ) {
            numUbersSent_3 = (getNumAvailableUbers()-numUbersSent_1)/2; //25% sent to third location
        }
        return numUbersSent_1;
    }
    public List<Integer> deactivateUbers(int t){
        for (Uber each: allUbers.keySet()){
            each.shutDownUber(t);
        }

        //FINAL all vacancies in this time!!!
        //TODO just to highlight
        return printFinalUberVacancies();
    }
    private List<Integer> printFinalUberVacancies(){
        System.out.println("PRINTING VACANCY DATA ===================");
        List<Integer> retthis = new ArrayList<>();
        for(Uber u : allUbers.keySet()){
            u.printNumberofRides(retthis);
            System.out.println();
        }
        return retthis;
    }
}

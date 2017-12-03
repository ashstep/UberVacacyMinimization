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

    public void updateAllUbers(){
        for (Uber u : this.allUbersList){
            u.updateDistance();
        }
    }

    //being called for each ride request
    private void assignmentBasedOnMethod() {

        //randomly pick an uber
        if (this.uberAllocation.equals("RANDOM_MOVEMENT")) {
            for (RideRequest rideReq : this.allRideReq) {
                if (!rideReq.isAssigned()  &&  rideReq.getActive()) { //if the request is active and no uber assigned
                    Uber u = getRandomValidUber();
                    rideReq.setInclude(false);
                    u.assignedClient(rideReq.getPassenger());
                    rideReq.setAssigned();
                }
            }
        }
        //UBER has to search for this client -> client remains unassigned for all ride requsets not assigned -> look at closest

        if (this.uberAllocation.equals("SEARCH_VICINITY")) {

            //creating the maps!!!
            for (Uber eachUber : this.allUbersList) {
                Location l = eachUber.getCurrLocation();
                TreeMap<Double, RideRequest> saved =  new TreeMap<Double, RideRequest>();
                //extract this at the end
                //calc ride req distances from suber
                for (RideRequest rideReq : this.allRideReq) {
                    if (rideReq.getActive() && !rideReq.isAssigned()){
                        Location rideLocation = rideReq.getPassengerLocation();
                        double distance = distanceMap.get(l).get(rideLocation);
                        saved.put(distance, rideReq);
                    }
                }
                eachUber.setMapOfNearestReq(saved);
            }

            // TODO what if multuple close ubers of same dist
            //time role plays factor
            for (Uber eachUber : this.allUbersList) {
                if  (eachUber.isVacant()) {
                    if (eachUber.getMapOfNearestReq().size()!=0){
                        //TODO going off nearest request
                        RideRequest rideReq = eachUber.getMapOfNearestReq().firstEntry().getValue();
                        eachUber.assignedClient(rideReq.getPassenger());
                        rideReq.setAssigned();
                        rideReq.setInclude(false);

                    }
                }
            }
        }
    }


    //sets ride request as active or not
    public void assignUbers(int timerTime){
        //printRideReqStatus();
        //printUberStatus();
        for (RideRequest rideReq : this.allRideReq) {
            //System.out.print("rideReq activation time: " + rideReq.getActivationTime());
            //System.out.println("  / current  time: " + timerTime);
            if(  !rideReq.isAssigned()  &&  rideReq.isActiveRequest(timerTime)  ){ //if it hasnt been assigned and its active now
              rideReq.setActive();
              rideReq.setInclude(true);
            }
        }
        assignmentBasedOnMethod();
        //printRideReqStatus();
        //printUberStatus();
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

    private boolean atLeastOneUberAvailable(){
        //number of uber available has to be greater than 1, if not skip
        for(Uber each: this.allUbers.keySet()){
            if (each.isVacant()){
                return true;
            }
        }
        return false;
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




    //TODO improve this ratio calcualtion
    //TODO this makes noooooooooo sense  LOL
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

    public void deactivateUbers(){
        for (Uber each: allUbers.keySet()){
            each.shutDownUber();
        }
        printFinalUberVacancies();
    }
    private void printFinalUberVacancies(){
        System.out.println("PRINTING VACANCY DATA ===================");
        for(Uber u : allUbers.keySet()){
            u.printNumberofRides();
            System.out.println();
        }
    }
}

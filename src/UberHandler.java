/**
 * Created by Ashka on 12/1/17.
 */
import java.util.*;

public class UberHandler {
    private List<Uber> allUbersList;
    private Graph graph;
    private HashMap<Uber,Boolean> allUbers;


    UberHandler(Graph g, List<Uber> ub, HashMap<Uber,Boolean> ub2) {
        this.allUbersList = ub;
        this.allUbers = ub2;
        this.graph = g;
    }


    public void updateAllUbers(){
        for (Uber u : this.allUbersList){
            u.updateDistance();
        }
    }


    public void assignUbers(int timerTime){
        //printRideReqStatus();
        //printUberStatus();
        for (RideRequest rideReq : this.allRideReq) {
            if(  !rideReq.isAssigned()  &&  rideReq.isActiveRequest(timerTime)  ){ //if it hasnt been assigned and its active now
                if (atLeastOneUberAvailable()){
                    Uber u = getRandomValidUber();
                    //substitute
                    u.assignedClient(rideReq.getPassenger());

                    //old
                    //assignUberPassenger(getRandomValidUber(), rideReq.getPassenger());

                    rideReq.setAssigned();
                }
            }
        }
        //printRideReqStatus();
        //printUberStatus();
    }


    private int getNumAvailableUbers(){
        int count=0;
        for (Uber eachUb: this.allUbers) {
            if (eachUb.isVacant()) {
                count++;
            }
        }
        return count;
    }
    //number of uber available has to be greater than 1, if not skip
    private boolean atLeastOneUberAvailable(){
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
        return allUbersList.get(randomNum);
    }





    //TODO improve this ratio calcualtion
    //TODO this makes noooooooooo sense  LOL
    //a while loop is a better way to do this (while we have available ubers, five here, two here, one there, and repeat
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
}

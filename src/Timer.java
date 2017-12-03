import java.util.*;
/**
 * Created by Ashka on 11/30/17.
 *
 * incerements and carries out all operations
 *
 */
public class Timer {
    private int time;
    private UberHandler myHandler;

    Timer(UberHandler hand, RequestGenerator rqgen){
        this.time = 0;
        this.myHandler = hand;
        System.out.println("Starting Timer...");
        ticker(rqgen);
    }


    //updates the distances of the ubers in progress
    //updates the status of ride requests
    //adds new requests in on time
    public void ticker(RequestGenerator rqgen){
        //TODO: Check if the request is active by using : isActiveRequest
        while(traversalIncomplete(rqgen)){
            //printUberStatus();

            this.myHandler.assignUbers(this.time);

            rqgen.getGraphFromGenerator().updateNumberOfPassengers(rqgen.getRequests());
            //rqgen.getGraphFromGenerator().

            this.time++;

            //System.out.println("Clock at: " + this.time);
            //printRideReqStatus(rqgen.getRequests());
            this.myHandler.updateAllUbers(); //calls "update distance"


        }
    }

    private boolean traversalIncomplete(RequestGenerator rq){
        //first check: checking uber progress
        for (Uber u : this.myHandler.getAllUbers()){
            if (u.inRide() || u.isArriving()){
                return true;
            }
        }
        //second check: checking no ride requests left
        for (RideRequest r : rq.getRequests()){
            if (!r.isAssigned()){ //request not assigned yet -> there are some left
                return true;
            }
        }
        return false;
    }
    private void printRideReqStatus(List<RideRequest> all){
        int count=0;
        int c2=0;
        int o=0;
        System.out.println("PRINTING STATUS OF ALL RIDE REQ ===================");
        for(RideRequest u : all){
            if (u.isComplete()) {
                //complletely finished - dropped off
                count++;
            }
            if (u.isinProgress() || u.getPassenger().isWaiting()) {
                //in progress of ride
                c2++;
            }
            if (!u.isAssigned() && !u.isComplete() && !u.isinProgress() ) {
                //untouched
                o++;
            }
            //System.out.println("    - RideReq for passenger " + u.getPassenger().getID() + " has been assigned? " + u.isAssigned());
        }
        System.out.println("    NUMBER REQ COMPLETED: " + count);
        System.out.println("    NUMBER REQ IN PROGRESS: " + c2);
        System.out.println("    NUMBER REQ LEFT: " + o);
    }

    public void getTime(){
        System.out.println("Time: " + this.time);
    }
}

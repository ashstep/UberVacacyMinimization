import java.util.*;
/**
 * Created by Ashka on 11/30/17.
 *
 * incerements and carries out all operations
 *
 */
public class Timer {
    private int time;
    private HashMap<Uber,Boolean> allUbers;
    private List<RideRequest> allRideReq;
    private UberHandler myHandler;

    Timer(Graph g, HashMap<Uber,Boolean> ubas, List<RideRequest> rq, UberHandler hand){
        this.time = 0;
        this.allUbers = ubas;
        this.allRideReq = rq;
        this.myHandler = hand;

        ticker();
    }


    //updates the distances of the ubers in progress
    //updates the status of ride requests
    //adds new requests in on time
    public void ticker(){
        //TODO: Check if the request is active by using : isActiveRequest
        while(traversalIncomplete()){
            //printUberStatus();
            //old: assignUbers();

            this.myHandler.assignUbers(this.time);

            this.time++;

            System.out.println("Clock is at: " + this.time);

            this.myHandler.updateAllUbers(); //calls "update distance"


        }
        //printRideReqStatus();
    }

    private boolean traversalIncomplete(){
        //first check: checking uber progress
        for (Uber u : this.allUbers.keySet()){
            if (u.inRide() || u.isArriving()){
                return true;
            }
        }
        //second check: checking no ride requests left
        for (RideRequest r : this.allRideReq){
            if (!r.isAssigned()){ //request not assigned yet -> there are some left
                return true;
            }
        }
        return false;
    }


}

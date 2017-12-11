import java.util.*;
/**
 * Created by Ashka on 11/30/17.
 * Increments
 */
public class Timer {
    private int time;
    private UberHandler myHandler;
    //old
//    Timer(UberHandler hand, RequestGenerator rqgen){
//        this.time = 0;
//        this.myHandler = hand;
//        System.out.println("Starting Timer...");
//        ticker(rqgen);
//    }

    //new
    Timer(UberHandler hand, Graph g, List<RideRequest> allRideReq){
        this.time = 0;
        this.myHandler = hand;
        System.out.println("Starting Timer...");
        ticker(g, allRideReq);
    }

    //new
    public void ticker(Graph g, List<RideRequest> allRideReq){
        while(traversalIncomplete(allRideReq)){
            this.myHandler.assignUbers(this.time);

            g.updateNumberOfPassengers(allRideReq);
            this.time++;
            this.myHandler.updateAllUbers(this.time); //calls "update distance"
            System.out.println("  === Clock at:  ====" + this.time);
            printRideReqStatus(allRideReq);
        }
    }

    private boolean traversalIncomplete(List<RideRequest> allRideReq){
        //second check: checking no ride requests left
        for (RideRequest r : allRideReq){
            if (!r.isAssigned() || !r.isComplete()){ //request not assigned yet -> there are some left
                return true;
            }
        }
        return false;
    }

    private void printRideReqStatus(List<RideRequest> all){
        int count=0;
        int c2=0;
        int o=0;
        int b=0;
        int a =0;
        int w=0;
        System.out.println("PRINTING STATUS OF ALL RIDE REQ ===================");
        for(RideRequest u : all){
            if (u.isComplete()) {
                //complletely finished - dropped off
                count++;
            } else if (u.isAssigned() && u.getActive() && !u.isComplete()) {
                //in progress of ride
                c2++;
            } else if (u.isAssigned() && !u.isInUber()) {
                //waiting for uber
                w++;
            } else if (u.isAssigned()) {
                a++;
            } else if (!u.getActive() && !u.isInUber()) {
                //untouched
                o++;
            } else {
                b++;
            }
            //System.out.println("    - RideReq for passenger " + u.getPassenger().getID() + " has been assigned? " + u.isAssigned());
        }
        System.out.println("    NUMBER REQ COMPLETED: " + count);
        System.out.println("    NUMBER REQ IN PROGRESS: " + c2);
        System.out.println("    NUMBER REQ ASSIGNED: " + a);
        System.out.println("    NUMBER REQ WAITING FOR PICKUP: " + w);

        System.out.println("    NUMBER REQ NOT ACTIVE YET: " + o);
        System.out.println("    NUMBER REQ NOT ACCOUNTED: " + b);
        System.out.println("    TOTAL NUM: " + all.size());


    }

    public int getTime(){
        System.out.println("Time: " + this.time);return time;
    }
}

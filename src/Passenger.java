/**
 * Created by Ashka on 11/15/17.
 */
public class Passenger {
//  TODO havent touched these yet
    int waitingForAssignment=0;

    private int id=0;
    private static int count = 0;
    private Location currentLocation = null;
    private Location targetLocation = null;
    private Location started;
    private String status;
    private RideRequest myRequest;

    private final String requestedUber = "REQUESTED_UBER";
    private final String inUber = "IN_UBER";
    private final String waitingArrival = "WAITING_FOR_ARRIVAL";
    private final String completed = "COMPLETED";
    private final String noStatus = "NONE";

    Passenger(Location l, Location o){
        this.started = l;
        this.currentLocation = l;
        this.targetLocation = o;
        this.status = noStatus;
        this.id = count++;
    }

    public int getID(){
        return this.id;
    }
    public void assignRequest(RideRequest re){
        this.myRequest = re;
    }
    public Location getCurrentLocation(){
        return this.currentLocation;
    }
    public Location getTargetLocation(){
        return this.targetLocation;
    }
    public void setCurrentLocation(Location set){
        this.currentLocation = set;
    }

    //status getter and setters to find state of passenger
    public Location getStartLocation(){return this.started;}
    public String getStatus(){
        return this.status;
    }
    public void setWaiting(){
        this.status = waitingArrival;
    }
    public boolean isWaiting(){
        return this.status.equals(waitingArrival);
    }
    public void setInUber(){
        this.status =  inUber;
    }
    public boolean isinUber(){
        return this.status.equals(inUber);
    }
    public void setRequestedUber(){
        this.status =  requestedUber;
    }
    public boolean requestedUber(){
        return this.status.equals(requestedUber);
    }
    public void setCompletedRide(){
        this.status =  completed;
    }
    public boolean completedRide(){
        return this.status.equals(completed);
    }
    public boolean noStatus(){
        return this.status.equals(noStatus);
    }
}

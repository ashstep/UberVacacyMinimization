/**
 * Created by Ashka on 11/15/17.
 */
public class RideRequest {
    private Passenger requestor;
    private Uber driver;
    private boolean assigned;
    private int activationTime;


    RideRequest(Passenger p, int activationTime) {
        this.requestor = p;
        this.assigned = false;
        this.activationTime = activationTime;
    }
    public void setAssigned(){
        this.assigned = true;
    }
    public boolean isAssigned(){
        return this.assigned;
    }
    public Passenger getPassenger(){
        return this.requestor;
    }

    public boolean isActiveRequest(int currentTime){
        if (this.activationTime<=currentTime) {
            requestor.setRequestedUber();
            return true;
        }
        return false;
    }
}

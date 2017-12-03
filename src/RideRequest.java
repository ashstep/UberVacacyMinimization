/**
 * Created by Ashka on 11/15/17.
 */
public class RideRequest {
    private Passenger requestor;
    private boolean assigned;
    private int activationTime;
    private boolean active;
    private boolean include;  //include this in map calculations?

    RideRequest(Passenger p, int activationTime) {
        this.requestor = p;
        this.assigned = false;
        this.activationTime = activationTime;
        this.active = false;
        this.include = false;
    }
    public void setAssigned(){
        this.assigned = true;
    }
    public void setActive(){
        this.active = true;
    }
    public boolean getInclude(){
        return this.include;
    }
    public void setInclude(boolean a){
         this.include = a;
    }

    public boolean isAssigned(){
        return this.assigned;
    }
    public boolean isinProgress(){return this.requestor.isinUber() ||this.requestor.requestedUber();}
    public Passenger getPassenger(){
        return this.requestor;
    }
    public boolean isComplete() {
        return this.requestor.completedRide();
    }
    public boolean isActiveRequest(int currentTime){

        if (this.activationTime<=currentTime) {
            requestor.setRequestedUber();
            this.setActive();
            return true;
        }
        return false;
    }
    public int getActivationTime(){
        return this.activationTime;
    }
    public boolean getActive(){
        return (this.active == true);
    }
    public Location getPassengerLocation(){return this.requestor.getCurrentLocation();}

}

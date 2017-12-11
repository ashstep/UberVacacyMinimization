/**
 * Created by Ashka on 11/15/17.
 */
public class RideRequest {
    private boolean assigned;
    private int activationTime;
    private int endTime;

    private int duration;
    private boolean active;
    private boolean include;  //include this in map calculations?
    private Location pickup;
    private Location dropoff;
    private boolean complete;
    private boolean inUber;

    RideRequest(int  activationTime, int end, int duration, Location pickup, Location dropoff) {
        this.assigned = false;
        this.activationTime = activationTime;
        this.endTime = end;
        this.duration = duration;  //time it takes to get to pickup from dropoff
        this.active = false;
        this.include = false;
        this.dropoff = dropoff;
        this.pickup = pickup;
        this.complete = false;
        this.inUber= false;
    }

    //SETTERS GETTERS
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
    public boolean isInUber(){
        return this.inUber;
    }
    public void setComplete(){
        this.complete=true;
    }
    public void setInUber(){
        this.inUber=true;
    }
    public boolean isComplete(){return this.complete;}
    public void updateCompleteStatus(int currentTime) {
        this.complete=false;
        if (this.activationTime<=currentTime && this.endTime<currentTime ) {
            this.complete=true;
        }
    }
    public boolean isActiveRequest(int currentTime){
        if (this.activationTime<=currentTime) {
            this.setActive();
            return true;
        }
        return false;
    }
    public String getStatus() {
        StringBuilder s = new StringBuilder();
        if (this.active) {
            s.append( " ACTIVE ");
        }
        if (this.assigned) {
            s.append( " ASSIGNED ");
        }
        if (this.inUber) {
            s.append( " IN_UBER ");
        }

        if (this.complete) {
            s.append( " COMPLETE ");
        }
        return s.toString();
    }
    //decreases time and checks if completed
    public boolean decreaseDuration(){
        this.duration--;
        if (this.duration<=0) {
            return true;
        }
        return false;
    }
    public int getDuration(){
        return this.duration;
    }
    public int getActivationTime(){
        return this.activationTime;
    }
    public boolean getActive(){
        return (this.active == true);
    }
    public Location getPickup() {
        return pickup;
    }
    public Location getDropoff() {
        return dropoff;
    }
    public void deleteRequest(){
        this.complete=true;
        this.duration=0;
    }
}

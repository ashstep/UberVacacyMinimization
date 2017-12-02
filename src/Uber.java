import java.util.*;

/**
 * Created by Ashka on 11/15/17.
 *
 * in updateDistance -> num indicates which method the uber will use for traversal in diff situations
 *      1 = stay in place
 *      2 = random mvmnt when vacant
 */
public class Uber {
    private double currDistToTravel;
    private List<Double> allDistancesTravelled;
    private int currVacancyTime;
    private List<Integer> allVacancyTimes;
    private int currTripTime;
    private List<Integer> allTripTimes;

    private Location current;
    private Location destination_pickup;
    private Location destination_dropoff;
    private boolean enRoutePickup;
    private boolean enRouteDropoff;
    private int id=0;
    private static int count = 0;
    private Passenger p;

    private HashMap<Location, Double> allLocations;
    private List<Location> allLocationsList;
    private HashMap<Location,HashMap<Location,Double>> distanceMap;


    private String status;
    private final String vacant = "VACANT";
    private final String arriving = "ARRIVING_TO_CLIENT";
    private final String inRide = "RIDE_IN_PROGRESS";
    private final String noStatus = "NONE";

    private String movementPattern;
    private final String inPlace = "IN_PLACE";
    private final String random_movement_vacant = "RANDOM_VACANT_MOVEMENT";
    private final String high_concentration_movement = "HIGH_CONCENTRATION_MOVEMENT";
    private final String search_vicinity_movement= "SEARCH_VICINITY_MOVEMENT";
    private boolean patternSet;

    private UberHandler myHandler;
    private Graph g;




    Uber(Graph g, Location start, String movementPattern) {
        this.p = null;
        this.status = vacant;
        this.allVacancyTimes = new ArrayList<>();
        this.enRoutePickup = false;
        this.enRouteDropoff = false;
        this.currVacancyTime = 0;
        this.currDistToTravel = 0;
        this.current = start;
        this.movementPattern = movementPattern;
        this.patternSet = false;
        
        this.allDistancesTravelled = new ArrayList<>();
        this.currTripTime = 0;
        this.allTripTimes = new ArrayList<>();
        this.id= count++;

        this.g = g;
        this.allLocations = g.getAllLocations();
        this.allLocationsList = g.getAllLocationsAsList();
        this.distanceMap = g.getDistanceMap();

    }


    public void assignedClient(Passenger pass) {
       this.p = pass;
       this.destination_pickup = p.getCurrentLocation();
       this.destination_dropoff = p.getTargetLocation();
//        System.out.println("           uber currently at  " + this.current.getName());
//       System.out.println("           p.getCurrentLocation() " + p.getCurrentLocation().getUniqueIdentifier());
//       System.out.println("           p.getTargetLocation() " + p.getTargetLocation().getUniqueIdentifier());
       this.setArriving();
//       System.out.println("destination_pickup " + destination_pickup.getName());
//       System.out.println(this.distanceMap.get(current).get(destination_pickup));

       this.currDistToTravel = this.distanceMap.get(current).get(destination_pickup);
       // System.out.println("curr distance to travel set at - " +this.currDistToTravel);
       this.allDistancesTravelled.add(this.currDistToTravel);
       pass.setWaiting();
    }

    //updates distance AND vacancy times
    public void updateDistance(){
        //num indicates which method of traversal the uber will use!!!!!
        if(this.isVacant()) {
            //System.out.println("the uber is vacant, curr distance is " + this.currDistToTravel);
            this.currVacancyTime++;
            setVacancyPattern();        //need to decide movment pattern
            this.currDistToTravel--;
            checkCurrDistToTravel();
        }

        if (!this.isVacant()) { //assuming that each increment of currTripTime you travel one unit of distance
            //System.out.println("the uber is NOT vacant, curr distance is " + this.currDistToTravel);
            this.currTripTime++;
            this.currDistToTravel--;
            checkCurrDistToTravel();
        }
    }

    private void setVacancyPattern() {
        //System.out.println("     setting vacancy pattern");
        if (!patternSet) {
            if (this.movementPattern.equals(random_movement_vacant)) {
                //System.out.println("     random movement vacany pattern");
                randomMovement();
            } else if (this.movementPattern.equals(high_concentration_movement)) {
                highConcentrationMovement();
            } else {
                //stationary
                //TODO add this method in
            }
            patternSet = true;
        }
    }

    private void checkCurrDistToTravel() {
        //System.out.println("current distance to travel is :" + this.currDistToTravel);
        if (this.currDistToTravel <= 0) {
            if (this.enRoutePickup) {                             // means client has been picked up
                this.enRoutePickup = false;
                this.enRouteDropoff = true;
                this.status = inRide;
                this.currDistToTravel = this.distanceMap.get(destination_pickup).get(destination_dropoff);
                this.current = destination_pickup;
                this.destination_pickup = null;
                this.p.setInUber();
            } else if (this.enRouteDropoff) {                     //ended the trip
                this.enRouteDropoff = false;
                this.enRoutePickup = false;
                this.currDistToTravel = 0;
                this.p.setCurrentLocation(destination_dropoff);
                this.current = destination_dropoff;
                this.destination_dropoff = null;
                this.status = vacant;
                this.allTripTimes.add(this.currTripTime);
                this.p.setCompletedRide();
                this.p = null;                                    //no passenger now
            } else {
                //if reached destination and its vacant  -> reset its destination based on methodology
                 this.currDistToTravel = 0;
                 this.patternSet = false;
                 this.current = destination_pickup;
            }
        }
    }


    //VACANCY METHOD 1: Stay in place

    // VACANCY METHOD 2: Random Movement While Waiting/Vacant
    private void randomMovement(){
        //System.out.println("in rancom movement ");
        this.destination_pickup = allLocationsList.get(new Random().nextInt(allLocations.size()));
        this.currDistToTravel = this.distanceMap.get(this.current).get(destination_pickup);
        //System.out.println("set this.currDistToTravel " + this.currDistToTravel );
     }

     
    // VACANCY METHOD 3: Ubers Move in direction of highest concentration while
    //TODO doff numbers of ubers go to differnet top locations
    private void highConcentrationMovement(){

        //int i = UberHandler(g,);
        int i =0;

        this.destination_pickup = g.getPopularLocations().get(i);
        this.currDistToTravel = this.distanceMap.get(current).get(destination_pickup);
    }



    //GETTERS SETTERS
    public int getID(){
        return this.id;
    }
    public List<Integer> getVacancyTimes(){
        return this.allVacancyTimes;
    }
    public int getNumVacancies(){
        return this.allVacancyTimes.size();
    }
    public Location getPickup(){return this.destination_pickup;}
    public Location getDropoff(){return this.destination_dropoff;}
    public String getStatus(){
        return this.status;
    }
    public boolean isVacant(){
        return this.status.equals(vacant);
    }
    public void setVacant(){
        this.status =  vacant;
    }
    public boolean isArriving(){
        return this.status.equals(arriving);
    }
    public void setArriving(){
        this.enRoutePickup = true;
        this.status =  arriving;
        //this.vacanciesTime.add(this.currVacancyTime);
        //System.out.println("addng vacancy time -"  + this.currVacancyTime);
    }
    public boolean inRide(){
        return this.status.equals(inRide);
    }
    public void setInRide(){
        this.status =  inRide;
        this.enRouteDropoff = true;
    }
    public void shutDownUber(){
        this.status = noStatus;
        this.allVacancyTimes.add(this.currVacancyTime);
    }
    public List<Integer> getTripTimes(){return this.allTripTimes;}
    public double getAvgOccupiedTime(){
        double total=0;
        for (int each : this.allTripTimes) {
           total+=each;
        }
        //System.out.println("total is == " + total);
        //System.out.println("this.allTripTimes.size() is == " + this.allTripTimes.size());
        //System.out.println("(total)/((double)this.allTripTimes.size()) is == " + (total)/((double)this.allTripTimes.size()));
        return (total)/((double)this.allTripTimes.size());
    }
    public Location getCurrLocation() {
        return this.current;
    }

    //printing data
    public void printNumberofRides(){
        System.out.println("UBER #" + this.getID());
        System.out.print("    - Number of Vacancies: " + this.allVacancyTimes.size() + " -- [");
        for (int all : getVacancyTimes()) {
            System.out.print(all + " ");
        }
        System.out.println("]");
        System.out.print("    - "+ this.allTripTimes.size()  +" Trips with Time Durations: [");
        int total=0;
        for (int each: this.allTripTimes) {
            System.out.print(each + " ");
            total+= each;
        }
        System.out.print("] -- Total Trip Time: "+ total);
        System.out.println();

        System.out.print("    - Distances Travelled: [");
        int to=0;
        for (double e : this.allDistancesTravelled){
            System.out.print(e + " ");
            to+= e;
        }
        System.out.println("] -- Total: "+ to);
    }
}
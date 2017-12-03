import java.util.*;

/**
 * Created by Ashka on 11/15/17.
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
    private final String random_movement_vacant = "RANDOM_MOVEMENT";
    private final String high_concentration_movement = "HIGH_CONCENTRATION_MOVEMENT";
    private final String search_vicinity_movement= "SEARCH_VICINITY";
    private boolean patternSet;
    private Graph g;
    private TreeMap<Double, RideRequest> mapOfNearestReq;
    private boolean vacancyDestinationset;

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
        this.vacancyDestinationset = false;
    }


    public void assignedClient(Passenger pass) {
       // System.out.println("the uber #" + this.getID()+ " assignedClient " );
       this.p = pass;
       this.destination_pickup = p.getCurrentLocation();
       this.destination_dropoff = p.getTargetLocation();
       this.setArriving();
       this.currDistToTravel = this.distanceMap.get(current).get(destination_pickup);
       this.allDistancesTravelled.add(this.currDistToTravel);
       pass.setWaiting();
    }

    public void updateDistance(){
        if(this.isVacant()) {
            this.currVacancyTime++;
            setVacancyPattern();        //need to decide movment pattern
            this.currDistToTravel--;
            checkCurrDistToTravel();
        }

        if (!this.isVacant()) { //assuming that each increment of currTripTime you travel one unit of distance
            this.currTripTime++;
            this.currDistToTravel--;
            this.vacancyDestinationset=false;
            checkCurrDistToTravel();
        }
    }

    private void setVacancyPattern() {
        if (!patternSet) {
            //System.out.println("     vacancy pattern not set");
            if (this.movementPattern.equals(random_movement_vacant)) {
                randomMovement();
            }
            if (this.movementPattern.equals(high_concentration_movement)) {
                highConcentrationMovement();
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
            }
            if (this.enRouteDropoff) {                     //ended the trip
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
                this.patternSet = false;
            }
            if (this.vacancyDestinationset) {
                 this.currDistToTravel = 0;
                 this.patternSet = false;
                 this.vacancyDestinationset = false;
                this.current = destination_pickup;
                this.destination_pickup=null;
            }
        }
    }


    //VACANCY METHOD 1: Stay in place

    // VACANCY METHOD 2: Random Movement While Waiting/Vacant
    private void randomMovement(){
        this.destination_pickup = allLocationsList.get(new Random().nextInt(allLocations.size()));
        this.currDistToTravel = this.distanceMap.get(this.current).get(this.destination_pickup);
        this.vacancyDestinationset = true;
     }

     
    // VACANCY METHOD 3: Ubers Move in direction of highest concentration while
    //TODO diff numbers of ubers go to differnet top locations
    private void highConcentrationMovement(){
        int i =0;
        this.destination_pickup = g.getPopularLocations().get(i);
        this.currDistToTravel = this.distanceMap.get(current).get(destination_pickup);
        this.vacancyDestinationset = true;
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
    public String getMovementPattern(){return this.movementPattern;}
    public void setMapOfNearestReq(TreeMap<Double, RideRequest> map){
        this.mapOfNearestReq = map;
    }
    public TreeMap<Double, RideRequest> getMapOfNearestReq(){
        return this.mapOfNearestReq;
    }
    //printing data
    public void printNumberofRides(){
        System.out.println("UBER #" + this.getID());
        System.out.print( "    - "+ this.allVacancyTimes.size()   + " Vacancy(ies) with Time Duration: [");
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
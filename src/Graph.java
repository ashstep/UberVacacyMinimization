import java.util.*;
/**
 * Created by Ashka on 11/19/17.
 */
public class Graph {
    public HashMap<Location, Double> locationSet = new HashMap<>();
    public HashMap<Road, Boolean> edgeSet = new HashMap<>();
    public List<Road> edgeSetList;
    public List<Location> locationSetList;
    private HashMap<String, Location> locationNameMapping = new HashMap<>();
    private HashMap<Location,HashMap<Location,Double>> distanceMap = new HashMap<>();
    private List<Location> sortedLocationsByPassengerCount;

    Graph (ArrayList<Location> locations, HashMap<Location,Double> mapLocations, HashMap<Location,HashMap<Location,Double>> dm) {
        this.distanceMap = dm;
        this.locationSetList = locations;
        this.locationSet = mapLocations;
        System.out.println("Number of Locations: " + locationSet.entrySet().size() );
    }

    //counts the passengers by their locaiton so that locationSet has updated counts of passengers
    public void updateNumberOfPassengers(List<RideRequest> req){
        locationSet.replaceAll((k, v) -> 0.0);
        for (RideRequest r  : req){
            if (r.getInclude()){ //just active but unassigned r.getActive() && !r.isAssigned() && !r.isComplete()

                locationSet.put(r.getPickup(),locationSet.get(r.getDropoff())+1);
            }
        }
        //NOTE: uncomment this to print the location map  (this gives us the top locations in terms of active passengers/ride requests
//        for (Map.Entry<Location,Double> each : locationSet.entrySet()) {
//            System.out.println("    "  + each.getKey().getName() + " = " + each.getValue());
//        }
        updatePopularLocations();
    }

    private void updatePopularLocations(){
        List<Double> doubles = new ArrayList<>(this.locationSet.values());
        Collections.sort(doubles,Collections.reverseOrder());

        List<Double> final_numberMap = new ArrayList<>();
        List<Location> final_locationMap = new ArrayList<>();

        for (double each : doubles) {
            for (Map.Entry<Location, Double> ent : this.locationSet.entrySet()) {
                if (each==ent.getValue()) {
                    final_numberMap.add(each);
                    final_locationMap.add(ent.getKey());
                }
            }
        }
        this.sortedLocationsByPassengerCount = final_locationMap;

        //setting the number of people at each location so that each location has number of ppl
        for (int i = 0; i< this.sortedLocationsByPassengerCount.size(); i++){
            this.sortedLocationsByPassengerCount.get(i).setNumPeople(final_numberMap.get(i));
        }
    }

    public HashMap<Location, Double> getAllLocations() {return locationSet;
    }
    public List<Location> getAllLocationsAsList() {
        return locationSetList;
    }
    public HashMap<Location,HashMap<Location,Double>> getDistanceMap(){
        return this.distanceMap;
    }
    public List<Location> getPopularLocations(){return this.sortedLocationsByPassengerCount;}

    //NOTE for error checking - delete when done
    public void printDistanceMap() {
        System.out.println("PRINTING DISTANCE MAPPING ===================");
        for (Map.Entry<Location, HashMap<Location,Double>> each : this.distanceMap.entrySet()) {
            Location l1 = each.getKey();
            HashMap<Location, Double> mapped = each.getValue();
            System.out.println("Location " + l1.getUniqueIdentifier()+ " mapped to:");
            for(Map.Entry<Location,Double>  e : mapped.entrySet()){
                System.out.println("    - Location " + e.getKey().getUniqueIdentifier() + " w/ distance: " + e.getValue());
            }
        }
    }
}

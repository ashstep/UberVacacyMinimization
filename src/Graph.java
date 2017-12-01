import java.util.*;
/**
 * Created by Ashka on 11/19/17.
 */
public class Graph {
    public HashMap<Location, Double> locationSet = new HashMap<>();
    public HashMap<Road, Boolean> edgeSet = new HashMap<>();
    public List<Road> edgeSetList;
    public List<Location> locationSetList;
    private HashMap<Location,HashMap<Location,Double>> distanceMap = new HashMap<>();


    //possible graphs to generate
    private final String avg = "DAILY_AVERAGE";
    private final String am = "AM_PEAK";
    private final String pm = "PM_PEAK";
    private final String midday = "MIDDAY";
    private final String evening = "EVENING";
    private final String morn = "EARLY_MORNING";

    //locations will not change -> global variable is okay
    Location Harborwalk;
    Location Hanover;
    Location Commercial;
    Location SnowHill;
    Location Stillman;
    Location Causeway;
    Location Blossom;
    Location Charles;
    Location CharlesRiver;
    Location StateHouse;


    Graph (String timeOfDay) {
        initNodes();
        if(timeOfDay.equals(avg)){
            initAvgEdges();
        }

        Dijkstra dj = new Dijkstra(this);
        this.distanceMap = dj.getDistanceMap();
    }

    private void initNodes(){
        Harborwalk = new Location("Harborwalk, WaterFront");
        Hanover = new Location("200 Hanover St");
        Commercial = new Location("Commercial St - NorthEnd");
        SnowHill = new Location("SnowHill - NorthEnd");
        Stillman = new Location("Stillman St - NorthEnd");
        Causeway = new Location("Causeway St - WestEnd");
        Blossom = new Location("Blossom St - WestEnd");
        Charles = new Location("Charles St - Beacon Hill");
        CharlesRiver = new Location("Charles River Esplanade");
        StateHouse = new Location("MA State House - Beacon Hill");

        locationSet = new HashMap<>();
        locationSet.put(Harborwalk, 0.0);
        locationSet.put(Hanover, 0.0);
        locationSet.put(Commercial, 0.0);
        locationSet.put(Stillman, 0.0);
        locationSet.put(SnowHill, 0.0);
        locationSet.put(Causeway, 0.0);
        locationSet.put(Blossom, 0.0);
        locationSet.put(Charles, 0.0);
        locationSet.put(CharlesRiver, 0.0);
        locationSet.put(StateHouse, 0.0);
        locationSetList = new ArrayList<Location>(locationSet.keySet());
    }
    private void initAvgEdges(){
        //Harborwalk to all other

        Road r1 = new Road(Harborwalk,Hanover, 2.11666667); //2 min 7 sec
        Road r2 = new Road(Harborwalk,Commercial, 3.55);
        Road r3 = new Road(Harborwalk,SnowHill, 4.91666667);
        Road r4 = new Road(Harborwalk,Stillman, 3.38333333);
        Road r5 = new Road(Harborwalk,Causeway, 3.71666667);
        Road r6 = new Road(Harborwalk,Blossom, 5.2);
        Road r7 = new Road(Harborwalk,Charles, 7.1);
        Road r8 = new Road(Harborwalk,CharlesRiver, 8.21666667);
        Road r9 = new Road(Harborwalk,StateHouse, 4.08333333);

        //Hanover to all other

        Road r1_1 = new Road(Hanover, Harborwalk, 3.5); //3 min 30 sec
        Road r2_1 = new Road(Hanover,Commercial, 2.31666667);
        Road r3_1  = new Road(Hanover,SnowHill, 3.31666667);
        Road r4_1  = new Road(Hanover,Stillman, 1.56666667);
        Road r5_1  = new Road(Hanover,Causeway, 3.45);
        Road r6_1  = new Road(Hanover,Blossom, 5.7);
        Road r7_1  = new Road(Hanover,Charles, 8.83333333);
        Road r8_1  = new Road(Hanover,CharlesRiver, 7.93333333);
        Road r9_1  = new Road(Hanover,StateHouse, 8.8);


        //Commercial to all other

        Road r1_2 = new Road(Commercial, Harborwalk, 4.93333333);
        Road r3_2  = new Road(Commercial,Hanover, 2.68333333);
        Road r2_2 = new Road(Commercial,SnowHill, 3.68333333);
        Road r4_2  = new Road(Commercial,Stillman, 4.3);
        Road r5_2  = new Road(Commercial,Causeway, 4.73333333);
        Road r6_2  = new Road(Commercial,Blossom, 8.26666667);
        Road r7_2  = new Road(Commercial,Charles, 10.15);
        Road r8_2  = new Road(Commercial,CharlesRiver, 11.03333333);
        Road r9_2  = new Road(Commercial,StateHouse, 4.08333333);

        //SnowHill to all other

        Road r1_3 = new Road(Causeway, Harborwalk, 6.21666667);
        Road r3_3  = new Road(SnowHill,Hanover, 2.81666667);
        Road r2_3 = new Road(SnowHill,Commercial, 10.4);
        Road r4_3  = new Road(SnowHill,Stillman, 2.13333333);
        Road r5_3  = new Road(SnowHill,Causeway, 4.3);
        Road r6_3  = new Road(SnowHill,Blossom, 9);
        Road r7_3  = new Road(SnowHill,Charles, 10.78333333);
        Road r8_3  = new Road(SnowHill,CharlesRiver, 11.11666667);
        Road r9_3  = new Road(SnowHill,StateHouse, 9.91666667);

        //StillMan to all other

        Road r1_4 = new Road(Stillman, Harborwalk, 4.31666667);
        Road r3_4  = new Road(Stillman,Hanover, 1.95);
        Road r2_4 = new Road(Stillman,Commercial, 2.86666667);
        Road r4_4  = new Road(Stillman,SnowHill, 1.91666667);
        Road r5_4  = new Road(Stillman,Causeway, 2);
        Road r6_4  = new Road(Stillman,Blossom, 7.03333333);
        Road r7_4  = new Road(Stillman,Charles, 9.2);
        Road r8_4  = new Road(Stillman,CharlesRiver, 9.01666667);
        Road r9_4  = new Road(Stillman,StateHouse, 8.43333333);


        //Causeway to all other

        Road r1_5 = new Road(Causeway, Harborwalk, 2.71666667);
        Road r3_5  = new Road(Causeway,Hanover, 2.96666667);
        Road r2_5 = new Road(Causeway,Commercial, 2.71666667);
        Road r4_5  = new Road(Causeway,SnowHill, 2.23333333);
        Road r5_5  = new Road(Causeway,Stillman, 2.63333333);
        Road r6_5  = new Road(Causeway,Blossom, 2.18333333);
        Road r7_5  = new Road(Causeway,Charles, 4.35);
        Road r8_5  = new Road(Causeway,CharlesRiver, 4.43333333);
        Road r9_5  = new Road(Causeway,StateHouse, 3.36666667);


        //Blossom to all other

        Road r1_6 = new Road(Blossom, Harborwalk, 4.66666667);
        Road r3_6  = new Road(Blossom,Hanover, 5.31666667);
        Road r2_6 = new Road(Blossom,Commercial, 5.68333333);
        Road r6_6  = new Road(Blossom,SnowHill, 6.91666667);
        Road r4_6  = new Road(Blossom,Stillman, 6.3);
        Road r5_6  = new Road(Blossom,Causeway, 2.18333333);
        Road r7_6  = new Road(Blossom,Charles, 1.7);
        Road r8_6  = new Road(Blossom,CharlesRiver, 2.58333333);
        Road r9_6  = new Road(Blossom,StateHouse, 2.66666667);

        //Charles to all other

        Road r1_7 = new Road(Charles, Harborwalk, 5.91666667);
        Road r3_7  = new Road(Charles,Hanover, 7.33333333);
        Road r2_7 = new Road(Charles,Commercial, 7.73333333);
        Road r6_7  = new Road(Charles,SnowHill, 8.98333333);
        Road r4_7  = new Road(Charles,Stillman, 8.2);
        Road r5_7  = new Road(Charles,Causeway, 3.9);
        Road r7_7  = new Road(Charles,Blossom, 1.71666667);
        Road r8_7  = new Road(Charles,CharlesRiver, 1.5);
        Road r9_7  = new Road(Charles,StateHouse, 3);


        //CharlesRiver to all other

        Road r1_8 = new Road(CharlesRiver, Harborwalk,7.55 );
        Road r3_8  = new Road(CharlesRiver,Hanover, 8.25);
        Road r2_8 = new Road(CharlesRiver,Commercial, 8.43333333);
        Road r6_8  = new Road(CharlesRiver,SnowHill, 9.81666667);
        Road r4_8  = new Road(CharlesRiver,Stillman, 9.13333333);
        Road r5_8  = new Road(CharlesRiver,Causeway, 4.6);
        Road r8_8  = new Road(CharlesRiver,Blossom, 2.61666667);
        Road r7_8  = new Road(CharlesRiver,Charles, 1.68333333);
        Road r9_8  = new Road(CharlesRiver,StateHouse, 5.11666667);


        //StateHouse to all other

        Road r1_9 = new Road(StateHouse, Harborwalk, 2.7);
        Road r3_9  = new Road(StateHouse,Hanover, 6.4);
        Road r2_9 = new Road(StateHouse,Commercial, 2.7);
        Road r6_9  = new Road(StateHouse,SnowHill, 7.13333333);
        Road r4_9  = new Road(StateHouse,Stillman, 6.01666667);
        Road r5_9  = new Road(StateHouse,Causeway, 3.6);
        Road r9_9  = new Road(StateHouse,Blossom, 3);
        Road r7_9  = new Road(StateHouse,Charles, 2.93333333);
        Road r8_9  = new Road(StateHouse,CharlesRiver, 7.11666667);


        //Adding all edges

        edgeSet = new HashMap<Road, Boolean>();
        edgeSet.put(r1, null);
        edgeSet.put(r2, null);
        edgeSet.put(r3, null);
        edgeSet.put(r4, null);
        edgeSet.put(r5, null);
        edgeSet.put(r6, null);
        edgeSet.put(r7, null);
        edgeSet.put(r8, null);
        edgeSet.put(r9, null);

        edgeSet.put(r1_1, null);
        edgeSet.put(r2_1, null);
        edgeSet.put(r3_1, null);
        edgeSet.put(r4_1, null);
        edgeSet.put(r5_1, null);
        edgeSet.put(r6_1, null);
        edgeSet.put(r7_1, null);
        edgeSet.put(r8_1, null);
        edgeSet.put(r9_1, null);

        edgeSet.put(r1_2, null);
        edgeSet.put(r2_2, null);
        edgeSet.put(r3_2, null);
        edgeSet.put(r4_2, null);
        edgeSet.put(r5_2, null);
        edgeSet.put(r6_2, null);
        edgeSet.put(r7_2, null);
        edgeSet.put(r8_2, null);
        edgeSet.put(r9_2, null);

        edgeSet.put(r1_3, null);
        edgeSet.put(r2_3, null);
        edgeSet.put(r3_3, null);
        edgeSet.put(r4_3, null);
        edgeSet.put(r5_3, null);
        edgeSet.put(r6_3, null);
        edgeSet.put(r7_3, null);
        edgeSet.put(r8_3, null);
        edgeSet.put(r9_3, null);

        edgeSet.put(r1_4, null);
        edgeSet.put(r2_4, null);
        edgeSet.put(r3_4, null);
        edgeSet.put(r4_4, null);
        edgeSet.put(r5_4, null);
        edgeSet.put(r6_4, null);
        edgeSet.put(r7_4, null);
        edgeSet.put(r8_4, null);
        edgeSet.put(r9_4, null);

        edgeSet.put(r1_5, null);
        edgeSet.put(r2_5, null);
        edgeSet.put(r3_5, null);
        edgeSet.put(r4_5, null);
        edgeSet.put(r5_5, null);
        edgeSet.put(r6_5, null);
        edgeSet.put(r7_5, null);
        edgeSet.put(r8_5, null);
        edgeSet.put(r9_5, null);

        edgeSet.put(r1_6, null);
        edgeSet.put(r2_6, null);
        edgeSet.put(r3_6, null);
        edgeSet.put(r4_6, null);
        edgeSet.put(r5_6, null);
        edgeSet.put(r6_6, null);
        edgeSet.put(r7_6, null);
        edgeSet.put(r8_6, null);
        edgeSet.put(r9_6, null);

        edgeSet.put(r1_7, null);
        edgeSet.put(r2_7, null);
        edgeSet.put(r3_7, null);
        edgeSet.put(r4_7, null);
        edgeSet.put(r5_7, null);
        edgeSet.put(r6_7, null);
        edgeSet.put(r7_7, null);
        edgeSet.put(r8_7, null);
        edgeSet.put(r9_7, null);

        edgeSet.put(r1_8, null);
        edgeSet.put(r2_8, null);
        edgeSet.put(r3_8, null);
        edgeSet.put(r4_8, null);
        edgeSet.put(r5_8, null);
        edgeSet.put(r6_8, null);
        edgeSet.put(r7_8, null);
        edgeSet.put(r8_8, null);
        edgeSet.put(r9_8, null);

        edgeSet.put(r1_9, null);
        edgeSet.put(r2_9, null);
        edgeSet.put(r3_9, null);
        edgeSet.put(r4_9, null);
        edgeSet.put(r5_9, null);
        edgeSet.put(r6_9, null);
        edgeSet.put(r7_9, null);
        edgeSet.put(r8_9, null);
        edgeSet.put(r9_9, null);


        edgeSetList = new ArrayList<Road>(edgeSet.keySet());
    }

    public HashMap<Location, Double> getAllLocations() {
        return this.locationSet;
    }
    public HashMap<Road, Boolean> getAllEdges() {
        return this.edgeSet;
    }
    public List<Location> getAllLocationsAsList() {
        return this.locationSetList;
    }
    public List<Road> getAllEdgesAsList() {
        return this.edgeSetList;
    }

    public void updateNumberOfPassengers(List<Passenger> pass){

        for (Passenger p  : pass){
            if (!p.completedRide()){
                locationSet.put(p.getCurrentLocation(),locationSet.get(p.getCurrentLocation())+1);
            }
        }
    }
    public HashMap<Location,HashMap<Location,Double>> getDistanceMap(){
        return this.distanceMap;
    }
    public List<Location> getPopularLocations(){
        TreeMap<Location, Integer> sortedMap = new TreeMap<Location, Integer>();
        for (Map.Entry entry : this.distanceMap.entrySet()) {
            sortedMap.put((Location) entry.getValue(), (Integer)entry.getKey());
        }

        List<Location> li = new ArrayList<>();
        for (Location e : sortedMap.keySet()){
            System.out.println("sorted location #"+ e.getUniqueIdentifier() + " with " + e.getmyPeople().size() + " ppl ");
            li.add(e);
        }
        return li;
    }


}

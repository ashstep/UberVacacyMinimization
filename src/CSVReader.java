/**
 * Created by Ashka on 11/30/17.
 */

import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.util.*;

public class CSVReader {
    //old
    private ArrayList<Double> lati = new ArrayList<>();
    private ArrayList<Double> longi = new ArrayList<>();


    private ArrayList<Integer> activationTime = new ArrayList<>();
    private ArrayList<Integer> dropoffTime = new ArrayList<>();
    private HashMap<Location,HashMap<Location,Double>> distanceMap = new HashMap<>();

    private ArrayList<Integer> allLocationsInt = new ArrayList<>();
    private ArrayList<Location> allLocationsList = new ArrayList<>();
    private HashMap<Location,Double> allLocations = new HashMap<>();
    private ArrayList<RideRequest> allRideReq = new ArrayList<>();

    private ArrayList<Integer> pickupLocation = new ArrayList<>();
    private ArrayList<Integer> dropoffLocation = new ArrayList<>();


    private final String taxi = "UBER";

    //possible graphs to generate

    private String[] locationNames;
    private double[][] times;

    CSVReader (String fileType, String timeofday)  {
        if (fileType.equals(taxi)) {
            System.out.println("Inputting Taxi Files for: " + timeofday + "...  ");
            readTaxiFile("/Users/Ashka/Documents/Workspace/Uber/src/CSVTaxiData/green_tripdata_2017-01.csv", timeofday);
            readTaxiFile("/Users/Ashka/Documents/Workspace/Uber/src/CSVTaxiData/green_tripdata_2017-02.csv", timeofday);
            //readTaxiFile("/Users/Ashka/Documents/Workspace/Uber/src/CSVTaxiData/green_tripdata_2017-03.csv", timeofday);

        }

    }
//    private void locationGenerator( String time){
//        if (time.equals(am)) {
//            readFile("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/am-peak.csv");
//        } else if (time.equals(pm)) {
//            readFile("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/pm_peak.csv");
//        } else if (time.equals(midday)){
//            readFile("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/middayTimes.csv");
//        } else if (time.equals(evening)) {
//            readFile("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/evening_times.csv");
//        }else if (time.equals(morn)) {
//            readFile("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/earlymorning.csv");
//        }else{
//            System.out.println("WARNING: Invalid Time Period Requested");
//        }
//    }


    //all encompassing method
    //save distances to a map -> all distances mapped w locations objects
    //save integers we want to create locations of
    private void readTaxiFile(String csvFile, String timeCategory) {
        int linecount = 0;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                String[] taxi = line.split(cvsSplitBy);

                int seconds1 = Integer.parseInt(taxi[0].substring(taxi[0].indexOf(" ")+1, taxi[0].indexOf(":")))*60;
                int min1 = Integer.parseInt(taxi[0].substring(taxi[0].indexOf(":")+1, taxi[0].indexOf(":")+3));
                int timeRequestMadeMinutes1 = (min1) + ( seconds1 );

                int seconds2 = Integer.parseInt(taxi[1].substring(taxi[1].indexOf(" ")+1, taxi[1].indexOf(":")))*60;
                int min2 = Integer.parseInt(taxi[1].substring(taxi[1].indexOf(":")+1, taxi[1].indexOf(":")+3));
                int timeRequestMadeMinutes2 = (min2) + ( seconds2 );

                int rideDuration = timeRequestMadeMinutes2 - timeRequestMadeMinutes1;


                // decide based on time input whether to use this or not to
                if (timeCategory.equals("LATE_NIGHT")) {
                    //12-6
                    if (!(timeRequestMadeMinutes1>0 && timeRequestMadeMinutes1<360)) {
                        continue;
                    }
                } else if (timeCategory.equals("MORNING")) {
                    //6-11
                    if (!(timeRequestMadeMinutes1>361 && timeRequestMadeMinutes1<660)) {
                        continue;
                    }
                } else if (timeCategory.equals("AFTERNOON")) {
                    //11-4
                    if (!(timeRequestMadeMinutes1>661 && timeRequestMadeMinutes1<960)) {
                        continue;
                    }
                } else if (timeCategory.equals("EVENING")) {
                    //4-8
                    if (!(timeRequestMadeMinutes1>961 && timeRequestMadeMinutes1<1200)) {
                        continue;
                    }
                } else if (timeCategory.equals("NIGHT")) {
                    //8-12
                    if (!(timeRequestMadeMinutes1>1201)) {
                        continue;
                    }
                } else {
                    //for ALL times otherwise keep going

                }



                //error check
                if (rideDuration<=0) {
                    continue;
                }


                int pickupLocationNum = Integer.parseInt(taxi[2]);
                int dropoffLocationNum = Integer.parseInt(taxi[3]);
                //int tripDistance = Integer.parseInt(taxi[4]);
                //double totalCost = Integer.parseInt(taxi[5]);
                int dispatch = Integer.parseInt(taxi[6]);

                //create location objects if they dont exist
                //add distance to mapping too!
                //System.out.println("taxi #" +linecount+ " at  "+ pickupLocationNum + " , start time= " + timeRequestMadeMinutes1  + " endtime= " + timeRequestMadeMinutes2 +" duration= " + rideDuration);

                //creates/gets location objects and adds to distance map
                createLocationObjects(pickupLocationNum, dropoffLocationNum,rideDuration);

                //init new ride req
                if (dispatch ==1) {
                    //not dispatched, hailed
                    continue;
                }

//                //TODO remove this for FINAL runs
//                if (linecount>500) {
//                    break;
//                } else {
//                    linecount++;
//                }

                RideRequest rideReq_new = new RideRequest(timeRequestMadeMinutes1,timeRequestMadeMinutes2,rideDuration, getExistingLocation(pickupLocationNum), getExistingLocation(dropoffLocationNum));
                allRideReq.add(rideReq_new);

                activationTime.add(timeRequestMadeMinutes1); //sets the activationTime time for the ride to start
                dropoffTime.add(timeRequestMadeMinutes2); //sets the activationTime time for the ride to start

                pickupLocation.add(pickupLocationNum);
                dropoffLocation.add(dropoffLocationNum);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (allRideReq.size()==0) {
            System.out.println("ERROR: There is no data for times in the selected range. Please add more data or select a different range.");
        } else{
            System.out.print(allRideReq.size() +" taxis initialized.");
        }

    }






    private void createLocationObjects(int a, int b, double time) {
        //dont need to do anything if they both exist
        if (locationExists(a) && locationExists(b)) {

            Location pickupAdd = getExistingLocation(a);
            Location dropoffAdd = getExistingLocation(b);

            //check if their mapping exists , if no add it

            if (distanceMap.get(pickupAdd)!=null) {
                if (!distanceMap.get(pickupAdd).containsKey(dropoffAdd)) {
                    HashMap<Location, Double>  m = distanceMap.get(pickupAdd);
                    m.put(dropoffAdd,time);
                    distanceMap.put(pickupAdd,m);
                }
            } else {
                HashMap<Location, Double>  m = new HashMap<>();
                m.put(dropoffAdd,time);
                distanceMap.put(pickupAdd,m);
            }
        } else if (locationExists(a) && !locationExists(b)) {
            Location pickupAdd = getExistingLocation(a);
            Location dropoffAdd = new Location(b);
            allLocations.put(dropoffAdd, 0.0);
            allLocationsList.add(dropoffAdd);

            HashMap<Location, Double>  m = distanceMap.get(pickupAdd);
            if (m==null) {
                //only is a TO dist rn
                HashMap<Location,Double> addmap = new HashMap<>();
                addmap.put(dropoffAdd,time);
                distanceMap.put(pickupAdd,addmap);
            } else {
                m.put(dropoffAdd,time);
            }
        } else if (!locationExists(a) && locationExists(b)) {
            Location pickupAdd = new Location(a);
            Location dropoffAdd = getExistingLocation(b);
            allLocations.put(pickupAdd, 0.0);
            allLocationsList.add(pickupAdd);
            HashMap<Location,Double> addmap = new HashMap<>();
            addmap.put(dropoffAdd,time);
            distanceMap.put(pickupAdd,addmap);

        } else {
            //both dont exist
            Location pickupAdd;
            Location dropoffAdd;
            if( a==b) {
                 pickupAdd = new Location(a);
                 dropoffAdd = getExistingLocation(b);
                allLocations.put(pickupAdd, 0.0);
                allLocationsList.add(pickupAdd);


            } else  {
                 pickupAdd = new Location(a);
                 dropoffAdd = new Location(b);
                allLocations.put(pickupAdd, 0.0);
                allLocations.put(dropoffAdd, 0.0);
                allLocationsList.add(pickupAdd);
                allLocationsList.add(dropoffAdd);


            }
            HashMap<Location,Double> addmap = new HashMap<>();
            addmap.put(dropoffAdd,time);
            distanceMap.put(pickupAdd,addmap);

        }
    }

    private Location getExistingLocation(int locationID){
        for (Location l : allLocations.keySet()) {
            if (l.getUniqueIdentifier()==locationID) {
                return l;
            }
        }
        return null;
    }

    private boolean locationExists(int locationID){
        for (Location l : allLocations.keySet()) {
            if (l.getUniqueIdentifier()==locationID) {
                return true;
            }
        }
        return false;
    }

    //new GETTERS/SETTERS
    public ArrayList<Integer> getActivationTime() {
        return activationTime;
    }
    public void setActivationTime(ArrayList<Integer> activationTime) {
        this.activationTime = activationTime;
    }
    public ArrayList<Integer> getDropoffTime() {
        return dropoffTime;
    }
    public void setDropoffTime(ArrayList<Integer> dropoffTime) {
        this.dropoffTime = dropoffTime;
    }
    public HashMap<Location, HashMap<Location, Double>> getDistanceMap() {
        return distanceMap;
    }
    public void setDistanceMap(HashMap<Location, HashMap<Location, Double>> distanceMap) {
        this.distanceMap = distanceMap;
    }
    public HashMap<Location,Double> getAllLocations() {
        return allLocations;
    }
    public ArrayList<Location> getAllLocationsList() {
        return allLocationsList;
    }
    public ArrayList<RideRequest> getAllRideReq() {
        return allRideReq;
    }
}

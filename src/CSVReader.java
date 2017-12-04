/**
 * Created by Ashka on 11/30/17.
 */

import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.util.*;

public class CSVReader {
    private ArrayList<Integer> activation = new ArrayList<>();
    private ArrayList<Double> lati = new ArrayList<>();
    private ArrayList<Double> longi = new ArrayList<>();

    private final String uber = "UBER";

    //possible graphs to generate
    private final String avg = "DAILY_AVERAGE";  //TODO add for average 
    private final String am = "AM_PEAK";
    private final String pm = "PM_PEAK";
    private final String midday = "MIDDAY";
    private final String evening = "EVENING";
    private final String morn = "EARLY_MORNING";

    private String[] locationNames;
    private double[][] times;

    CSVReader (String fileType, String indicator)  {
        if (fileType.equals(uber)) {
            System.out.println("Inputting Uber Files...");
            readUberFile("/Users/Ashka/Documents/Workspace/Uber/src/UberData/aug14.csv");
        } else {
            //data reader
            System.out.println("Inputting Location Data...");
            locationGenerator(indicator);
            System.out.print ("Simulation for : " + indicator + "  ||  ");
        }

    }
    private void locationGenerator( String time){
        if (time.equals(am)) {
            readFile("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/am-peak.csv");
        } else if (time.equals(pm)) {
            readFile("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/pm_peak.csv");
        } else if (time.equals(midday)){
            readFile("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/middayTimes.csv");
        } else if (time.equals(evening)) {
            readFile("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/evening_times.csv");
        }else if (time.equals(morn)) {
            readFile("/Users/Ashka/Documents/Workspace/Uber/src/LocationInformation/earlymorning.csv");
        }else{
            System.out.println("WARNING: Invalid Time Period Requested");
        }
    }

    private void readFile(String csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        //TODO this is hardcoded w number of locations I did
        int savedLocations = 0;
        int numLocationsTotal = 10;
        int countX = 0;
        int countY = 0;
        boolean doneWithNames = false;
        boolean skipNext = false;
        boolean firstSkip = true;
        locationNames = new String[numLocationsTotal];
        times = new double[numLocationsTotal][numLocationsTotal];

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] eachLine = line.split(cvsSplitBy);
                for (String each : eachLine){
                    if (skipNext) {
                        skipNext=false;
                        continue;
                    }
                    //saving names of the locations
                    if (savedLocations<numLocationsTotal) {
                        locationNames[savedLocations] = each.trim();
                        savedLocations++;
                        continue;
                    } else {
                        doneWithNames = true;
                    }
                    //save numbers
                    if (countX<numLocationsTotal && doneWithNames){
                        if (firstSkip){
                            firstSkip = false;
                            continue;
                        }
                        times[countY][countX]= Double.parseDouble(each);
                        countX++;
                        if (countX==numLocationsTotal){
                            countY++;
                            countX = 0;
                            skipNext = true;
                        }
                    }
                }
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
    }

    private void readUberFile(String csvFile) {
        int linecount = 0;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                //TODO remove this for FINAL runs
                if (linecount>500) {
                    break;
                } else {
                    linecount++;
                }

                String[] uber = line.split(cvsSplitBy);
                //System.out.println("Uber [time= " + uber[0] + " , lat=" + uber[1] + " , long=" + uber[2]  + "]");
                int seconds = Integer.parseInt(uber[0].substring(uber[0].indexOf(" ")+1,uber[0].indexOf(":")))*60;
                int min = Integer.parseInt(uber[0].substring(uber[0].indexOf(":")+1,uber[0].indexOf(":")+3));
                int timeRequestMadeMinutes = (min) + ( seconds );
                activation.add(timeRequestMadeMinutes);
                lati.add(Double.parseDouble(uber[1]));
                longi.add(Double.parseDouble(uber[2]));
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
    }

    public ArrayList<Double> getLats(){return this.lati;}
    public ArrayList<Double> getLongs(){return this.longi;}
    public ArrayList<Integer> getActivationTimes(){return this.activation;}

    private void printLocations(String[] arr){
        System.out.println("printing locaitons ======");
        for(int i=0; i<arr.length; i++){
            System.out.print(String.format("%20s", arr[i]));
        }
    }
    private void printdata(double[][] arr){
        System.out.println("printing data ======");
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                System.out.print(String.format("%20s", arr[i][j]));
            }
            System.out.println("");
        }
    }
    public double[][] get_times() {
        return times;
    }
    public String[] getlocationNames() {
        return locationNames;
    }


}

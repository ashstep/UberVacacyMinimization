/**
 * Created by Ashka on 11/30/17.
 */

import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.util.*;

public class CSVReader {
    private ArrayList<Integer> activation = new ArrayList<Integer>();
    private ArrayList<Integer> lati = new ArrayList<Integer>();
    private ArrayList<Integer> longi = new ArrayList<Integer>();

    CSVReader(String csvFile){
        readFile(csvFile);
    }

    private void readFile(String csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] uber = line.split(cvsSplitBy);

                System.out.println("Uber [time= " + uber[0] + " , lat=" + uber[1] + " , long=" + uber[2]  + "]");

                int timeRequestMadeMinutes = ( Integer.parseInt(uber[0].substring(uber[0].indexOf(":")+1,uber[0].indexOf(":")+3)) )
                + ( Integer.parseInt(uber[0].substring(uber[0].indexOf(" ")+1,uber[0].indexOf(":")))*60 );

                activation.add(timeRequestMadeMinutes);
                lati.add(Integer.parseInt(uber[1]));
                longi.add(Integer.parseInt(uber[2]));
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

    public ArrayList<Integer> getLats(){return this.lati;}
    public ArrayList<Integer> getLongs(){return this.longi;}
    public ArrayList<Integer> getActivationTimes(){return this.activation;}

}

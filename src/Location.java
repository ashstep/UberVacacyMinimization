import java.util.*;
/**
 * Created by Ashka on 11/15/17.
 * this will be each node on the graph
 */
public class Location {
    private int id;
    private List<Uber> myUbers = new ArrayList<>();
    private double numPassengers;
    private String stringName;
    private static int count = 0;

    Location(String nameFinder){
        this.stringName = nameFinder;
        this.id= count++;
        this.numPassengers = 0;
    }
    public Integer getUniqueIdentifier(){
        return this.id;
    }
    public List<Uber> getmyUbers(){
        return this.myUbers;
    }
    public double getNumPeople(){
        return this.numPassengers;
    }
    public void setNumPeople(double pppl){ this.numPassengers = pppl;}
    public String getName(){return this.stringName;}
}

import java.util.*;
/**
 * Created by Ashka on 11/15/17.
 * this will be each node on the graph
 */
public class Location {
    private int id;
    private List<Uber> myUbers = new ArrayList<>();
    private double numPassengers;

    Location(int locationID){
        this.id = locationID;
        this.numPassengers = 0;
    }

    public Integer getUniqueIdentifier(){
        return this.id;
    }
    public double getNumPeople(){
        return this.numPassengers;
    }
    public void setNumPeople(double pppl){ this.numPassengers = pppl;}
}

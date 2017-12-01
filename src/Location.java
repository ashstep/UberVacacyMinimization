import java.util.*;
/**
 * Created by Ashka on 11/15/17.
 * this will be each node on the graph
 */
public class Location {
    private int id;
    //ubers at this location
    private List<Uber> myUbers = new ArrayList<>();  //TODO need to consistently update this as the uber moves
    private List<Passenger> myPeople = new ArrayList<>();  //set onces (inital location)
    private String stringName;
    private static int count = 0;


    Location(String nameFinder){
        this.stringName = nameFinder;
        this.id= count++;
    }
    public Integer getUniqueIdentifier(){
        return this.id;
    }
    public List<Uber> getmyUbers(){
        return this.myUbers;
    }
    public List<Passenger> getmyPeople(){
        return this.myPeople;
    }
    public String getName(){return this.stringName;}

}

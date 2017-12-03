import java.util.*;
/**
 * Created by Ashka on 11/15/17.
 * the edge of the graph
 */
public class Road {
    private Location from;
    private Location to;
    private double weight;

    public Road(Location from, Location to, double argWeight) {
        this.from = from;
        this.to = to;
        this.weight = argWeight;
    }

    public Location getFrom() {
        return this.from;
    }
    public Location getTo() {
        return this.to;
    }
    public double getWeight() {
        return this.weight;
    }
}

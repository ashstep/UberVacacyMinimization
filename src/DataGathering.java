import java.util.*;
/**
 * Created by Ashka on 11/26/17.
 *
 * information to collect:
 * - time duration
 * - average number of times VACANT
 * - average number of times OCCUPIED
 * -
 */
public class DataGathering {
    private List<Main> allIterations = new ArrayList<>();

    //TODO this constructor
    public DataGathering(){

    }

    public List<Main> getAllIterations() {
        return this.allIterations;
    }
    public void addAllIterations(Main m) {
        this.allIterations.add(m);
        this.analyticsFoOne(m);
    }

    public void allAnalytics() {
        for (Main each: this. allIterations){
            analyticsFoOne(each);
        }
    }

    private void analyticsFoOne(Main m) {
        uberCalc(m.allUbersList);
        passengerCalc(m.allPassengersList);
        //other(m.time);

    }


    public void uberCalc (List<Uber> ubers){
        List<Integer> vac = new ArrayList<>();          // for average NUMBER of vacancies
        List<Integer> occupiedAmt = new ArrayList<>();     // for avg NUMBER of rides
        List<Double> occupiedTimes = new ArrayList<>();     // for avg DURATION of rides
        List<Integer> compensation = new ArrayList<>();     // for avg DURATION of rides

        //gathering data
        for(Uber each: ubers){
            //this is what you would print out for averages
            vac.add(each.getNumVacancies());             //adding number of vacancies
            occupiedAmt.add(each.getTripTimes().size()); //adding number of trips
            occupiedTimes.add(each.getAvgOccupiedTime());
            compensation.add(each.getTotalCompensation());

        }

        //calling submethods
        double v = avgVacancies(vac);
        double o = avgOccupiedNum(occupiedAmt);
        double t = avgOccupiedTime(occupiedTimes);
        double p = avgProfit(compensation);

    }

    public void passengerCalc (List<Passenger> passengers){

    }

    //such as time
    public void other(){

    }


    //=====AVERAGE CALCULATIONS
    //calculate avgNumOfVacancies for this set of ubers
    public double avgVacancies(List<Integer> numofVac){
        double sum = 0;
        for (double e : numofVac){
            sum+=e;
        }
        return sum / ((double) numofVac.size());
    }
    public double avgOccupiedNum(List<Integer> occupiedAmt){
        double sum = 0;
        for (double e : occupiedAmt){
            sum+=e;
        }
        return sum / ((double) occupiedAmt.size());
    }
    public double avgOccupiedTime(List<Double> occupiedTimes){
        double sum = 0;
        for (double e : occupiedTimes){
            sum+=e;
        }
        return sum / ((double) occupiedTimes.size());
    }
    public double avgProfit(List<Integer> profits){
        double sum = 0;
        for (double e : profits){
            sum+=e;
        }
        return sum / ((double) profits.size());
    }

}

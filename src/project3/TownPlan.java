package project3;
import java.util.ArrayList;

/**
 * A class defining a TownPlan which will also store the solution to the fire station problem.
 */
public class TownPlan {
    /**
     * Number of houses
     */
    private Integer n;

    /**
     * Number of fire stations
     */
    private Integer k;

    /**
     * A list containing house positions in ascending order
     */
    private ArrayList<Float> position_houses;

    /**
     * A list containing the optimal fire station positions in ascending order,
     * which should be set by the OptimalPosFireStations method of Program3.java
     */
    private ArrayList<Float> position_fire_stations;

    /**
     * The optimal response time for the TownPlan,
     * which should be set by OptimalResponse method of Program3.java
     */ 
    private Float response;

    public TownPlan(
            Integer n,
            Integer k,
            ArrayList<Float> position_houses) {
        this.n = n;
        this.k = k;
        this.position_houses = position_houses;
        this.position_fire_stations = null;
        this.response = null;
    }

    public TownPlan(
            Integer n,
            Integer k,
            ArrayList<Float> position_houses,
	        ArrayList<Float> position_fire_stations,
            Float response) {
        this.n = n;
        this.k = k;
        this.position_houses = position_houses;
        this.position_fire_stations = position_fire_stations;
        this.response = response;
    }

    public TownPlan(TownPlan data, ArrayList<Float> position_fire_stations, Float response) {
        this(
                data.n,
                data.k,
                data.position_houses,
                position_fire_stations,
                response);
    }

    public TownPlan(TownPlan data) {
        this(
                data.n,
                data.k,
                data.position_houses,
                new ArrayList<Float>(0),
                10000.00f);
    }

    /**
     * Use this method to set "position_fire_stations" from the OptimalPosFireStations method of Program3.java
     */
    public void setPositionFireStations(ArrayList<Float> position_fire_stations) {
        this.position_fire_stations = position_fire_stations;
    }

    /**
     * Use this method to set "response" from the OptimalResponse method of Program3.java
     */
    public void setResponse(Float response){
	    this.response = response;
    }

    public Integer getHouseCount() {
        return n;
    }

    public Integer getStationCount() {
        return k;
    }

    public ArrayList<Float> getPositionHouses() {
        return position_houses;
    }

    public ArrayList<Float> getPositionFireStations() {
        return position_fire_stations;
    }

    public Float getResponse() {
        return response;
    }

    public String getInputSizeString() {
        return String.format("n = %d k = %d\n", n, k);
    }

    public String getResponseString(){
	    if (response == null) {
            return "";
        }

        return String.format("Optimum response time =  %f\n", response);
    }
    public String getSolutionString() {
        if (position_fire_stations == null) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < position_fire_stations.size(); i++) {
            String str = String.format("Station %d Position %f", i, position_fire_stations.get(i));
            s.append(str);
            if (i != position_fire_stations.size() - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    public String toString() {
        return getInputSizeString() + getResponseString() + getSolutionString();
    }
}

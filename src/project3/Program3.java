/*
 * Name: Michael Whitaker
 * EID: maw5299
 */
package project3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program3 extends AbstractProgram3 {

    /**
     * Determines the solution of the optimal response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "response" field set to the optimal response time
     */
    @Override
    public TownPlan OptimalResponse(TownPlan town) {
        /* TODO implement this function */
    	//create the two OPT tables, one storing response, other storing locations
    	ArrayList<ArrayList<Float>> responseOptTable = new ArrayList<ArrayList<Float>>();
    	ArrayList<ArrayList<ArrayList<Float>>> locationOptTable = new ArrayList<ArrayList<ArrayList<Float>>>();
    	
    	
    	//fill in initial values of the table
    	for(int i=0;i<=town.getStationCount();i++) {
    		ArrayList<ArrayList<Float>> locInnerTable = new ArrayList<ArrayList<Float>>();
    		ArrayList<Float> innerTable = new ArrayList<Float>();
    		responseOptTable.add(innerTable);
    		locationOptTable.add(locInnerTable);
    		for(int j=0;j<=town.getHouseCount();j++) {
    			locationOptTable.get(i).add(innerTable);
    			if(i<j) {
    				responseOptTable.get(i).add(-2f);
    			}else if(i==j) {
    				responseOptTable.get(i).add(0f);
    			}else {
    				responseOptTable.get(i).add(-1f);
    			}
    		}
    	}
    	//fill in the first row of the table ie row with only 1 station
    	for(int i=0;i<=town.getHouseCount();i++) {
    		if(responseOptTable.get(1).get(i)==(-2f)) {
    			Float lastHousePosition = town.getPositionHouses().get(i-1);
    			Float firstHousePosition = town.getPositionHouses().get(0);
    			Float responseTime = (lastHousePosition-firstHousePosition)/2;
    			responseOptTable.get(1).set(i, responseTime);
    			Float stationLoc = responseTime + firstHousePosition;
    			locationOptTable.get(1).get(i).add(stationLoc);
    		}
    	}
    	//fill in all other rows of the tables
    	//three cases --
    	//   1. partition first house an rest of houses
    	//   2. partition on left l houses l>1 and right r houses r>1
    	//   3. partition last house and rest of houses
    	for(int i=2;i<=town.getStationCount();i++) {
    		for(int j=0;j<=town.getHouseCount();j++) {
    			Float minResponseTime = -2f;
    			ArrayList<Float> stationLocs = new ArrayList<Float>();
    			if(responseOptTable.get(i).get(j)==(-2f)) {
    				for(int k=1;k<=j-1;k++) {
    					if(k==1) {
    						Float leftHousePos = town.getPositionHouses().get(k);
    						Float rightHousePos = town.getPositionHouses().get(j-1);
    						Float fireHousePos = rightHousePos - leftHousePos;
    						minResponseTime = fireHousePos/2;
    						Float fireHousePos1 = town.getPositionHouses().get(0);
    						fireHousePos = minResponseTime + leftHousePos;
    						for(int p=0;p<i-1;p++) {
    							stationLocs.add(fireHousePos1);
    						}
    						stationLocs.add(fireHousePos);
    					}else if(k==j-1) {
    						if(responseOptTable.get(i-1).get(k)<minResponseTime) {
    							minResponseTime = responseOptTable.get(i-1).get(k);
    							stationLocs = locationOptTable.get(i-1).get(k);
    							stationLocs.add(town.getPositionHouses().get(j-1));
    						}
    					}else {
    						Float leftResponseTime = responseOptTable.get(i-1).get(k);
    						Float fireHousePos = town.getPositionHouses().get(j-1) - town.getPositionHouses().get(k);
    						Float rightResponseTime = fireHousePos/2;
    						if(leftResponseTime<rightResponseTime) {
    							if(rightResponseTime<minResponseTime) {
    								minResponseTime = rightResponseTime;
    								stationLocs = locationOptTable.get(i-1).get(k);
    								Float rightFireLoc = town.getPositionHouses().get(k) + rightResponseTime;
    								stationLocs.add(rightFireLoc);
    							}
    						}else {
    							if(leftResponseTime<minResponseTime) {
    								minResponseTime = leftResponseTime;
    								stationLocs = locationOptTable.get(i-1).get(k);
    								Float rightFireLoc = town.getPositionHouses().get(k) + rightResponseTime;
    								stationLocs.add(rightFireLoc);
    							}
    						}
    					}
    				}
    				responseOptTable.get(i).set(j, minResponseTime);
    				locationOptTable.get(i).set(j, stationLocs);
    			}
    		}
    	}
    	Float minRespTime = -2f;
    	// search table for minimum response time
    	for(int i=1;i<=town.getStationCount();i++) {
    		if(i==1) {
    			minRespTime = responseOptTable.get(i).get(town.getHouseCount());
    		}else {
    			if(responseOptTable.get(i).get(town.getHouseCount())<minRespTime) {
    				minRespTime = responseOptTable.get(i).get(town.getHouseCount());
    			}
    		}
    	}
    	
    	town.setResponse(minRespTime);
    	
    	return town;
    }

    /**
     * Determines the solution of the set of fire station positions that optimize response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "position_fire_stations" field set to the optimal fire station positions
     */
    @Override
    public TownPlan OptimalPosFireStations(TownPlan town) {
        /* TODO implement this function */
    	ArrayList<ArrayList<Float>> responseOptTable = new ArrayList<ArrayList<Float>>();
    	ArrayList<ArrayList<ArrayList<Float>>> locationOptTable = new ArrayList<ArrayList<ArrayList<Float>>>();
    	
    	
    	//fill in initial values of the table
    	for(int i=0;i<=town.getStationCount();i++) {
    		ArrayList<ArrayList<Float>> locInnerTable = new ArrayList<ArrayList<Float>>();
    		ArrayList<Float> innerTable = new ArrayList<Float>();
    		responseOptTable.add(innerTable);
    		locationOptTable.add(locInnerTable);
    		for(int j=0;j<=town.getHouseCount();j++) {
    			ArrayList<Float> thirdTable = new ArrayList<Float>();
    			locationOptTable.get(i).add(thirdTable);
    			if(i<j) {
    				responseOptTable.get(i).add(-2f);
    			}else if(i==j) {
    				responseOptTable.get(i).add(0f);
    			}else {
    				responseOptTable.get(i).add(-1f);
    			}
    		}
    	}
    	//fill in the first row of the table ie row with only 1 station
    	for(int i=0;i<=town.getHouseCount();i++) {
    		if(responseOptTable.get(1).get(i)==(-2f)) {
    			Float lastHousePosition = town.getPositionHouses().get(i-1);
    			Float firstHousePosition = town.getPositionHouses().get(0);
    			Float responseTime = (lastHousePosition-firstHousePosition)/2;
    			responseOptTable.get(1).set(i, responseTime);
    			Float stationLoc = responseTime + firstHousePosition;
    			locationOptTable.get(1).get(i).add(stationLoc);
    		}
    	}
    	//fill in all other rows of the tables
    	//three cases --
    	//   1. partition first house an rest of houses
    	//   2. partition on left l houses l>1 and right r houses r>1
    	//   3. partition last house and rest of houses
    	for(int i=2;i<=town.getStationCount();i++) {
    		for(int j=0;j<=town.getHouseCount();j++) {
    			Float minResponseTime = -2f;
    			if(responseOptTable.get(i).get(j)==(-2f)) {
    				for(int k=1;k<=j-1;k++) {
    					if(k==1) {
    						Float leftHousePos = town.getPositionHouses().get(k);
    						Float rightHousePos = town.getPositionHouses().get(j-1);
    						Float fireHousePos = rightHousePos - leftHousePos;
    						minResponseTime = fireHousePos/2;
    						Float fireHousePos1 = town.getPositionHouses().get(0);
    						fireHousePos = minResponseTime + leftHousePos;
    						ArrayList<Float> stationLocs = new ArrayList<Float>();
    						for(int p=0;p<i-1;p++) {
    							stationLocs.add(fireHousePos1);
    						}
    						stationLocs.add(fireHousePos);
    						locationOptTable.get(i).set(j, stationLocs);
    					}else if(k==j-1) {
    						if(responseOptTable.get(i-1).get(k)<minResponseTime) {
    							minResponseTime = responseOptTable.get(i-1).get(k);
    							ArrayList<Float> tempList = (ArrayList<Float>)(locationOptTable.get(i-1).get(k)).clone();
    							tempList.add(town.getPositionHouses().get(j-1));
    							locationOptTable.get(i).set(j, tempList);
    						}
    					}else {
    						Float leftResponseTime = responseOptTable.get(i-1).get(k);
    						Float fireHousePos = town.getPositionHouses().get(j-1) - town.getPositionHouses().get(k);
    						Float rightResponseTime = fireHousePos/2;
    						if(leftResponseTime<rightResponseTime) {
    							if(rightResponseTime<minResponseTime) {
    								minResponseTime = rightResponseTime;
    								ArrayList<Float> tempListRight = (ArrayList<Float>)(locationOptTable.get(i-1).get(k)).clone();
    								Float rightFireLoc = town.getPositionHouses().get(k) + rightResponseTime;
    								tempListRight.add(rightFireLoc);
    								locationOptTable.get(i).set(j, tempListRight);
    							}
    						}else {
    							if(leftResponseTime<minResponseTime) {
    								minResponseTime = leftResponseTime;
    								ArrayList<Float> tempListLeft = (ArrayList<Float>)(locationOptTable.get(i-1).get(k)).clone();
    								Float rightFireLoc = town.getPositionHouses().get(k) + rightResponseTime;
    								tempListLeft.add(rightFireLoc);
    								locationOptTable.get(i).set(j, tempListLeft);
    							}
    						}
    					}
    				}
    				responseOptTable.get(i).set(j, minResponseTime);
    			}
    		}
    	}
    	// search table for minimum response time
    	Float minRespTime = -2f;
    	ArrayList<Float> finalStationLocs = new ArrayList<Float>();
    	for(int i=1;i<=town.getStationCount();i++) {
    		if(i==1) {
    			minRespTime = responseOptTable.get(i).get(town.getHouseCount());
    			finalStationLocs = locationOptTable.get(i).get(town.getHouseCount());
    		}else {
    			if(responseOptTable.get(i).get(town.getHouseCount())<minRespTime) {
    				minRespTime = responseOptTable.get(i).get(town.getHouseCount());
    				finalStationLocs = locationOptTable.get(i).get(town.getHouseCount());
    			}
    		}
    	}
    	
    	town.setPositionFireStations(finalStationLocs);
    	
    	return town;
    }
}

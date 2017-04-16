package workers;

import java.util.*;

public class MapWorker implements Worker, MapWorkerImp{

	public Map<String, Object> map(Object o1, Object o2){
		
		return null;
	}

	public void notifyMaster(){
		
	}
	
	
	
	public String calculateHash(String s){
		return null;
	}
	
	public void sendToReducers(Map<String, Object> mp){
		
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	public void waitForTasksThread() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 *  
	 * @param slat
	 * @param slon
	 * @param elat
	 * @param elon
	 * @return the distance between coordinates.
	 */
	private double distance(Double slat,Double slon, Double elat,Double elon){
		return Math.pow(Math.abs(elat - slat), 2)+Math.pow(Math.abs(elon-slon), 2);
	}
}
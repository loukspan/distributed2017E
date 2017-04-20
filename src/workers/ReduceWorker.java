package workers;

import java.util.Map;

import model.Directions;
import java.io.*;
import java.net.*;


public class ReduceWorker implements Worker, ReduceWorkerImp{
	private static Map<String,Object> reducedDirections;

	public void waitForMasterAck(){
		//or: new ReduceWorker()
	}


	public Map<String, Object> reduce(String dir, Object ob) {
		// TODO Auto-generated method stub
		return null;
	}


	public void sendResults(Map<String, Object> mp) {
		
		
	}


	public void initialize() {
		
		sendResults(reduce("", new Object()));
		
	}


	public void waitForTasksThread() {
		// TODO Auto-generated method stub
		
	}
	
}

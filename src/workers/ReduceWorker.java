package workers;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import model.Directions;

import java.io.*;
import java.net.*;

import javax.print.attribute.standard.RequestingUserName;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.detDSA;


public class ReduceWorker implements Worker, ReduceWorkerImp{
	private static Map<Integer, Directions> reducedDirections=null;
	public ReduceWorker(Map<Integer, Directions> map){
		reducedDirections=map;
	}
	public void waitForMasterAck(){
		//or: new ReduceWorker()
	}


	public Map<Integer, Directions> getReducedDirections() {
		return reducedDirections;
	}
	public Directions reduce(Map<Integer, Directions> mp) {
		/*Directions directions =*/
		Directions counted;
		
		counted = mp.entrySet().stream().parallel().filter(p->p.getValue().getDirs().contains("34.1385374")).
				map(p->p.getValue()).reduce((sum, p)->sum).get();
		return counted;
	}
	
	public void sendResults(Directions dirs) {
				
	}


	public void initialize() {
		
		/*sendResults(reduce(reducedDirections));*/
		
	}


	public void waitForTasksThread() {
		// TODO Auto-generated method stub
		
	}
	
}

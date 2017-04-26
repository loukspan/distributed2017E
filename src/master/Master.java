package master;

import java.io.*;
import java.net.*;
import java.util.*;

import model.*;

import org.json.*;

import okhttp3.*;
import workers.*;


public class Master implements MasterImp{
	
	private Directions askedDirections;
	private Directions ourDirections;
	private static LinkedList<Directions> cache;
	private Map<Integer, Directions> mappedDirections;
	private static ServerMasterforClient serverMasterforClient;
	public Master(){
		cache = new LinkedList<Directions>();
	}

	public void initialize(){
		waitForNewQueriesThread();
		sendResultsToClient();
		//askedDirections = new Directions(1, 1, 1, 45454);
		//startClientForMapper();
		//MapWorker mapWorker = new MapWorker();
		//mappedDirections=mapWorker.map();
		//startClientforReducer(mappedDirections);
	}
	
	public void waitForNewQueriesThread(){
		openServerForClient();
	}
	
	public Directions searchCache(Directions dir){
		Directions idir=null;
		for(int i=0; i<cache.size(); i++){
			idir=cache.get(i);
			if(dir.equals(idir))
				return idir;
		}
		return idir;
	}
	
	public void distributeToMappers(){
		/**
		 * TODO: Fix myThread to open it again in all methods
		 */

	}
	
	public void waitForMappers(){
		//startClientForMapper(ourDirections);//prosoxi sto object: ourDirections
	}
	
	public void ackToReducers(){
		startClientforReducer(mappedDirections);
	}
	
	public void collecDataFromReducers(){
		
	}
	
	public Directions askGoogleDirectionsAPI(String startlat, String startlon, String endlat, String endlon){
		String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+startlat+","+startlon+"&destination="+endlat+","+endlon+"&key=AIzaSyB3ZUeeQPpFDS1SsD5KwIOiA9xyC8pBQM0";
		return new Directions(sendGet(url));
	}
	
	public boolean updateCache(Directions newDir){
		boolean isThere=true;
		Directions idir;
		for(int i=0; i<cache.size(); i++){
			idir=cache.get(i);
			if(newDir.equals(idir)){
				isThere=false;
				i=cache.size()+2000;
			}
		}
		
		if(isThere)cache.add(newDir);
		
		return isThere;
		
	}
	
	
	public boolean updateDatabase(String dir, Directions newDir){
		return false;
	}
	
	public void sendResultsToClient(){
		ourDirections= new Directions(45, 1, 1, 1);
		closeServerForClient();
	}
	
	// HTTP GET request using OKHTTP
	private String sendGet(String url){
		try {
			return (new Master()).run(url);
		} catch (IOException e) {
			e.printStackTrace();
			return "Request to "+ url+" failed!";
		}
	}

	private	String run(String url) throws IOException {
		
		OkHttpClient client = new OkHttpClient();
		  Request request = new Request.Builder()
		      .url(url)
		      .build();
		  try(Response response = client.newCall(request).execute()){
			  return response.body().string();
		  }  
	}
	
	private void startClientForMapper() {
		Socket requestSocket = null;
        Map<Integer, Directions> mappedDirections;
        try {              
            requestSocket = new Socket("172.16.3.7", 4345);
            ActionsForMappers actionsForMappers = new ActionsForMappers(requestSocket, askedDirections);
            actionsForMappers.run();
            mappedDirections = actionsForMappers.getMappedDirs();
        } catch (Exception e) {
        	e.printStackTrace();
        	System.err.println(e.getMessage());
		} finally {
            try {
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
	}
	
	private void startClientforReducer(Map<Integer, Directions> reducedDirections) {
        Socket requestSocket = null;
        Directions message;
        try {
              
            requestSocket = new Socket("172.16.2.46", 5000);
            ActionsForReducer actionsForReducer = new ActionsForReducer(requestSocket, mappedDirections);
            actionsForReducer.start();
        } catch (Exception e) {
        	e.printStackTrace();
        	System.err.println(e.getMessage());
		} finally {
            try {
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }	
    
	private void openServerForClient() {
		ServerSocket providerSocket = null;
	    Socket connection = null;
        
            try {
				providerSocket = new ServerSocket (4321);
				connection = providerSocket.accept();
				serverMasterforClient = new ServerMasterforClient(connection, askedDirections);
				serverMasterforClient.run();
				//serverMasterforClient.setReducedDirections(new Directions(22,45,745,45));
				//serverMasterforClient.writeOutAndClose();
			} catch (Exception e) {
				e.printStackTrace();
			}              
    }
	
	private void closeServerForClient() {
		serverMasterforClient.writeOutAndClose(ourDirections);
	}
}

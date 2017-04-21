package master;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import model.*;
import org.json.*;
import okhttp3.*;
import workers.MapWorker;


public class Master implements MasterImp{
	
	private Directions ourDirections;
	private static Map<String, Object> cache;
	private MapWorker myWorker;

	public void initialize(){
		cache = new LinkedHashMap<String, Object>();
	}
	
	public void waitForNewQueriesThread(){
		
	}
	
	public Directions searchCache(String dir){
		return (Directions) cache.get(dir);
	}
	
	public void distributeToMappers(){
		/**
		 * TODO: Fix myThread to open it again in all methods
		 */
		myWorker = new MapWorker();
		myWorker.initialize();
	}
	
	public void waitForMappers(){
		myWorker.notifyMaster();
	}
	
	public void ackToReducers(){
		
	}
	
	public void collecDataFromReducers(){
		
	}
	
	public Directions askGoogleDirectionsAPI(String startlat, String startlon, String endlat, String endlon){
		//TODO: Fix url: https://developers.google.com/maps/documentation/directions/start#get-a-key
		String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+startlat+","+startlon+"&destination="+endlat+","+endlon+"&key=AIzaSyB3ZUeeQPpFDS1SsD5KwIOiA9xyC8pBQM0";
		//System.out.println(sendGet(url));
		//System.out.println(deserialize(sendGet(url)));
		return new Directions(sendGet(url));
		
	}
	
	public boolean updateCache(String dir, Directions newDir){
		if (!cache.containsKey(dir)){
			cache.put(dir, newDir);
			return true;
		}
		return false;
		
	}
	
	public boolean updateDatabase(String dir, Directions newDir){
		return false;
		
	}
	
	public void sendResultsToClient(){
		System.out.println(ourDirections.toString());
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
}

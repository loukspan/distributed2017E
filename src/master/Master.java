package master;
import java.io.IOException;
import org.json.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import model.Directions;

public class Master implements MasterImp{
	public void initialize(){
		
	}
	
	public void waitForNewQueriesThread(){
		
	}
	
	public Directions searchCache(String dir){
		return null;
	}
	
	public void distributeToMappers(){
		
	}
	
	public void waitForMappers(){
		
	}
	
	public void ackToReducers(){
		
	}
	
	public void collecDataFromReducers(){
		
	}
	
	public Directions askGoogleDirectionsAPI(String startlat, String startlon, String endlat, String endlon){
		//TODO: Fix url: https://developers.google.com/maps/documentation/directions/start#get-a-key
		String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+startlat+","+startlon+"&destination="+endlat+","+endlon+"&key=AIzaSyB3ZUeeQPpFDS1SsD5KwIOiA9xyC8pBQM0";
		System.out.println(sendGet(url));
		//System.out.println(deserialize(sendGet(url)));
		return null;
		
	}
	
	public boolean updateCache(String dir, Directions newDir){
		return false;
		
	}
	
	public boolean updateDatabase(String dir, Directions newDir){
		return false;
		
	}
	
	public void sendResultsToClient(){
		
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
	//TODO: Fix the way we deserialize the json file we get from google
	private static String deserializeGooglejson(String str){
		JSONObject object = new JSONObject(str);
		/*
		 * Reference to:
		 * http://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
		 */
		
		return "";
	}
}

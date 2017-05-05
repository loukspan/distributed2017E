package master;

import java.io.*;
import java.net.*;
import java.util.*;
import model.*;
import okhttp3.*;

public class Master implements MasterImp{
	
	private Directions askedDirections;
	private Directions ourDirections;
	private static LinkedList<Directions> cache;
	private static Map<Integer, Directions> mappedDirections=null;;
	private static ServerMasterforClient serverMasterforClient;
	public Master(){
		cache = new LinkedList<Directions>();
	}

	public void initialize(){		
		waitForNewQueriesThread();
		askedDirections = serverMasterforClient.getAskedDirections();
		ourDirections = searchCache(askedDirections);
		if(ourDirections==null){
			startClientForMapper();
			//System.out.println(mappedDirections.get(0).getDirs());
			startClientforReducer(mappedDirections);
		}
		if(ourDirections==null){
			ourDirections=askGoogleDirectionsAPI(askedDirections.getStartlat(),askedDirections.getStartlon(),
					askedDirections.getEndlat(),askedDirections.getEndlon());
			
		}
		System.out.println(ourDirections.toString());
		
		
		

		
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
		 * TODO: Fix Thread to open it again in all methods
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
	
	public Directions askGoogleDirectionsAPI(double startlat, double startlon, double endlat, double endlon){
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
		serverMasterforClient.write(ourDirections);
		/*synchronized(serverMasterforClient){
			serverMasterforClient.setReducedDirections(ourDirections);
			try {
				serverMasterforClient.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		//serverMasterforClient.close();
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
			  //System.out.println(response.body().string());
			  return response.body().string();
		  }  
	}
	
	private void startClientForMapper() {
		Socket requestSocket = null;
		ObjectInputStream inputStream = null;
		ObjectOutputStream out = null;
        try {              
            requestSocket = new Socket("172.16.1.68", 4232);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            inputStream = new ObjectInputStream(requestSocket.getInputStream());
            out.writeObject(askedDirections);
            out.flush();
            this.mappedDirections= ((Map<Integer, Directions>) inputStream.readObject());
            //System.out.println(mappedDirections.get(0).toString());
            //ActionsForMappers actionsForMappers = new ActionsForMappers(requestSocket, askedDirections);
            //actionsForMappers.run();
            //mappedDirections = actionsForMappers.getMappedDirs();
            
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
        ObjectOutputStream out=null;
        ObjectInputStream inputStream = null;
        Directions message;
        try {
              
            requestSocket = new Socket("172.16.1.60", 4005);
            out= new ObjectOutputStream(requestSocket.getOutputStream());
            inputStream = new ObjectInputStream(requestSocket.getInputStream());
            out.writeObject(mappedDirections);
            out.flush();
            out.writeObject(askedDirections);
            out.flush();
            Object ourObject= inputStream.readObject();
            if(ourObject.toString().equals("null")){
            	this.ourDirections=null;
            }else{
                this.ourDirections = ((Directions)ourObject);
            }
            //ActionsForReducer actionsForReducer = new ActionsForReducer(requestSocket, mappedDirections);
            //actionsForReducer.start();
            
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
				providerSocket = new ServerSocket (4006);
				connection = providerSocket.accept();
				serverMasterforClient = new ServerMasterforClient(connection, askedDirections);
				serverMasterforClient.run();
				//serverMasterforClient.setReducedDirections(new Directions(22,45,745,45));
				//serverMasterforClient.writeOutAndClose();
			} catch (Exception e) {
				e.printStackTrace();
			}              
    }
}

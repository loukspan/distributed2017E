package workers;

import java.util.Map;
import model.Directions;
import model.ServerReducerForMaster;
import model.ServerWorkerForMaster;

import java.io.*;
import java.net.*;



public class ReduceWorker implements Worker, ReduceWorkerImp{
	private Directions askedDirections, reducedDirections;
	private static Map<Integer, Directions> mappedDirections=null;
	private ServerReducerForMaster serverReducerForMaster;
	public ReduceWorker(Map<Integer, Directions> map, Directions askedDirections){
		mappedDirections=map;
		this.askedDirections=askedDirections;
	}
	public ReduceWorker() {
		// TODO Auto-generated constructor stub
	}
	public void waitForMasterAck(){
		//or: new ReduceWorker()
	}


	public Directions getReducedDirections() {
		return reducedDirections;
	}
	
	private void setReducedDirections(Directions reducedDirections) {
		this.reducedDirections= reducedDirections;
	}
	
	public Directions reduce(Map<Integer, Directions> mp) {
		/*Directions directions =*/
		Directions counted;
		
		counted = (Directions) mp.entrySet().stream().parallel().filter(p->p.getValue().equals(askedDirections)).
				map(p->p.getValue()).reduce((sum, p)->sum).get();
		return counted;
	}
	
	public void sendResults(Directions dirs) {
		serverReducerForMaster.writeOutAndClose(dirs);
	}


	public void initialize() {
		openServerForMaster();
		reducedDirections=reduce(mappedDirections);
		sendResults(reducedDirections);
	}


	public void waitForTasksThread() {
		// TODO Auto-generated method stub
		
	}
	
	private void openServerForMaster() {
		ServerSocket providerSocket = null;
        Socket connection = null;
         
            try {
                providerSocket = new ServerSocket (4232);
                connection = providerSocket.accept();
                serverReducerForMaster = new ServerReducerForMaster(connection);
                serverReducerForMaster.start();
                mappedDirections=serverReducerForMaster.getMappedDirs();
            } catch (UnknownHostException unknownHost) {
                System.err.println("You are trying to connect to an unknown host!");
            }catch (IOException ioException) {
                ioException.printStackTrace();
            }    
	}
}

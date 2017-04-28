package workers;

import java.util.Map;
import model.Directions;
import java.io.*;
import java.net.*;



public class ReduceWorker implements Worker, ReduceWorkerImp{
	private Directions askedDirections;
	private static Map<Integer, Directions> reducedDirections=null;
	public ReduceWorker(Map<Integer, Directions> map, Directions askedDirections){
		reducedDirections=map;
		this.askedDirections=askedDirections;
	}
	public ReduceWorker() {
		// TODO Auto-generated constructor stub
	}
	public void waitForMasterAck(){
		//or: new ReduceWorker()
	}


	public Map<Integer, Directions> getReducedDirections() {
		return reducedDirections;
	}
	
	private void setReducedDirections(Map<Integer, Directions> reducedDirections) {
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
		
	}


	public void initialize() {
		
		/*sendResults(reduce(reducedDirections));*/
		
	}


	public void waitForTasksThread() {
		// TODO Auto-generated method stub
		
	}

 
    public void openServer() {
        ServerSocket providerSocket = null;
        Socket connection = null;
        Map<Integer, Directions> message = null;
        try {
            providerSocket = new ServerSocket (4321);
             
 
            while (true) {
                 
                connection = providerSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                
                do {
                    try {
                        message = ((Map<Integer,Directions>)in.readObject());
                        System.out.println(connection.getInetAddress().getHostAddress()+ " >" + message.get(1).getDirs());
                        out.writeObject(reduce(message));
                        out.flush();
                        break;
                    } catch (ClassNotFoundException classnot) {
                        System.err.println("Data received in unknown format");
                    }catch (Exception e) {
                    	System.out.println(e.getMessage());
					}
                } while (true);
                 
                in.close();
                out.close();
                connection.close();
            }
                 
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                providerSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    
   }
}

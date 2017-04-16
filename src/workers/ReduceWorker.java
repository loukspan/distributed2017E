package workers;

import java.util.Map;

import model.Directions;
import model.Message;
import model.ReducerThread;

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
	private ServerSocket providerSocket;
	private Socket connection = null;
     
    private void openServer() {
        try {
                providerSocket = new ServerSocket(4321, 10);
                 
                while(true){
                    connection = providerSocket.accept();
                     
                    Thread t = new ReducerThread(connection,reducedDirections);
                    t.start();
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

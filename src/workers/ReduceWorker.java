package workers;

import java.util.Map;

import model.Directions;
import model.Message;
import model.ReducerThread;

import java.io.*;
import java.net.*;


public class ReduceWorker implements Worker, ReduceWorkerImp{
	private static Map<String,Object> reducedDirections;
	@Override
	public void waitForMasterAck(){
		//or: new ReduceWorker()
	}

	@Override
	public Map<String, Object> reduce(String dir, Object ob) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendResults(Map<String, Object> mp) {
		
		
	}

	@Override
	public void initialize() {
		
		sendResults(reduce("", new Object()));
		
	}

	@Override
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

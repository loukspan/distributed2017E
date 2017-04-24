package queryres;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import model.Directions;



public class CreateQuery implements CreateQueryImp{
	private Directions askedDirs;
	public CreateQuery(double startlat,double startlon, double endlat, double endlon) {
		askedDirs=new Directions(startlat, startlon, endlat, endlon);
	}
	
	public void getLocation(){
		
	}
	
	public void createQuery(){
		
	}
	public Directions getAskedDirs(){
		return this.askedDirs;
	}
	public void sendQueryToServer(Directions askedDirs){
		this.askedDirs=askedDirs;
		startClientForMasterServer();
	}
	
	private void startClientForMasterServer() {
		Socket requestSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        Directions message;
        try {
             
            requestSocket = new Socket("172.16.3.1", 4321);
             
             
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());
             
            try{
                out.writeObject(getAskedDirs());
                out.flush();
                message = (Directions) in.readObject();
                System.out.println("Server>" + message.getDirs());
                 //TODO: return reduced results from Master
                                  
            }catch (ClassNotFoundException classNot) {
                System.err.println("data received in unknown format");
            }
 
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
	}
	
}

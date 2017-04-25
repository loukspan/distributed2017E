package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class ServerMasterforClient extends Thread{

	ObjectInputStream in;
    ObjectOutputStream out;
    Directions askedDirections,reducedDirections;
    
    public ServerMasterforClient(Socket connection, Directions askedDirections) {
        this.askedDirections= askedDirections;
    	try {
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Directions getReducedDirs(){
      	return this.reducedDirections;
    }
    
    public Directions getAskedDirections() {
		return this.askedDirections;
	}
    
    public void setReducedDirections(Directions reducedDirections){
    	this.reducedDirections=reducedDirections;
    }
     
    public void run() {
 
        try {
             
            try{
            	askedDirections =((Directions)in.readObject());            	
            }catch(ClassNotFoundException classnot){            
                 
                System.err.println("Data received in unknown format!");
            }            
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void writeOutAndClose() {
    	
    	try {
    		out.writeObject(this.getReducedDirs());
    		out.flush();
            in.close();
            out.close();
        } catch (IOException ioException) {
        	ioException.printStackTrace();
        }
	}

}

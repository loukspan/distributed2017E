package model;

import java.io.*;
import java.net.Socket;

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
            	System.out.println(askedDirections.toString());
            
            	
            }catch(ClassNotFoundException classnot){              
                System.err.println("Data received in unknown format!");
            }            
        } catch (IOException e) {
    	   e.printStackTrace();
        }
    }    
    public void write(Directions reduced) {
    	this.setReducedDirections(reduced);
    	try {
			out.writeObject(this.getReducedDirs());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}
	}
    
    public void close() {
    	
    	try {
            in.close();
            out.close();
        } catch (IOException ioException) {
        	ioException.printStackTrace();
        }
	}

}

package model;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

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
    	read();
    }
    
    public synchronized void read() {
    	try {
            try{            	
            	askedDirections =((Directions)in.readObject());      
            	System.out.println(askedDirections.toString());            	
            }catch(ClassNotFoundException classnot){              
                System.err.println("Data received in unknown format!");
            }catch (EOFException e) {
            	System.err.println("EOF Exception");
            }
        } catch (IOException e) {
    	   e.printStackTrace();
        }
    }
    
    public void write(Directions reduced) {
    	this.setReducedDirections(reduced);
    	this.askedDirections = null;
    	try {
			out.writeObject(this.getReducedDirs());
			out.flush();
		}catch (SocketException e) {
			System.err.println("SOCKET Exception");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
    
    public void close() {
    	
    	try {
            in.close();
            out.close();
            this.interrupt();
        } catch (IOException ioException) {
        	ioException.printStackTrace();
        }
	}

}

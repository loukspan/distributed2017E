package model;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ServerReducerForMaster extends Thread{
	ObjectInputStream in;
	ObjectOutputStream out;
	Directions reducedDirectons;
	Map<Integer, Directions> mappedDirections;
	public ServerReducerForMaster(Socket connection) {
		
		   try {
		        out = new ObjectOutputStream(connection.getOutputStream());
		        in = new ObjectInputStream(connection.getInputStream());
		
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	public Map<Integer, Directions> getMappedDirs(){
	  	return this.mappedDirections;
	}
	
	public Directions getAskedDirections() {
		return this.reducedDirectons;
	}
	
	public void setMappedDirections(Map<Integer, Directions> mappedDirections){
		this.mappedDirections=mappedDirections;
	}
	 
	@Override
	public void run() {	
	    
	         
	        try{
	        	//System.out.println(in.readObject().getClass().getName());
	        	reducedDirectons =((Directions)in.readObject());      
	        	System.out.println(reducedDirectons.toString());
	        	
	        }catch(ClassNotFoundException classnot){              
	            System.err.println("Data received in unknown format!");
	        } catch (IOException e) {
	        	
	 		   System.err.println("Cannot read object!\n");
	 		   e.printStackTrace();
			}            
	    
	}	
	
	public void writeOutAndClose(Directions reducedDirections) {
		
		try {
			this.setMappedDirections(mappedDirections);
			out.writeObject(this.getMappedDirs());
			out.flush();
	        in.close();
	        out.close();
	    } catch (IOException ioException) {
	    	ioException.printStackTrace();
	    }
	}
}

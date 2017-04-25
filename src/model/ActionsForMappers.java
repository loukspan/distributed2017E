package model;

import java.io.*;
import java.net.*;
import java.util.Map;

public class ActionsForMappers extends Thread{
	
	ObjectInputStream in;
    ObjectOutputStream out;
    Directions askedDirections;
    Map<Integer, Directions> mappedDirections;
    
    public ActionsForMappers(Socket connection, Directions askedDirections) {
        this.askedDirections= askedDirections;
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
     
    public void run() {
 
        try {
             
            try{
            	out.writeObject(this.askedDirections);
                out.flush();
            	this.mappedDirections =((Map<Integer, Directions>)in.readObject());
            	
            }catch(ClassNotFoundException classnot){            
                 
                System.err.println("Data received in unknown format!");
            }
 
             
             
            } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
  
}

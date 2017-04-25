package model;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ActionsForReducer extends Thread{
	ObjectInputStream in;
    ObjectOutputStream out;
    Directions reducedDirections;
    Map<Integer, Directions> mappedDirections;
    
    public ActionsForReducer(Socket connection, Map<Integer, Directions> mappedDirections) {
        this.mappedDirections= mappedDirections;
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
     
    public void run() {
 
        try {
             
            try{
            	out.writeObject(this.mappedDirections);
                out.flush();
            	this.reducedDirections =((Directions)in.readObject());
            	
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

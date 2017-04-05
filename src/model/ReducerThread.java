package model;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ReducerThread extends Thread {
	 ObjectInputStream in;
	 ObjectOutputStream out;
	 private Map<String, Object> directions;
	public ReducerThread(Socket connection, Map<String,Object> directions) {
		this.directions=directions;
		try {
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	 public void run() {
	        try {
	                int a = in.readInt();
	                int b = in.readInt();
	                if(directions!=null) 
	                	out.writeObject(directions);;
	                out.flush();
	                 
	     
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

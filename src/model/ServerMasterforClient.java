package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

import org.bouncycastle.jcajce.provider.symmetric.Threefish;

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
            	while(true){
            		String className = in.readObject().getClass().getName();
            		if (className.equals("String")) {
						String message = in.readObject().toString();
						if(message.equals("bye")){
							write(message);
							close();
						}
					}else if(className.equals("Directions")){
						askedDirections =((Directions)in.readObject());  
						System.out.println(askedDirections.toString());
						synchronized(this){
							write(getAskedDirections());
							notify();
						}
					}
            	}      	
            	
            }catch(ClassNotFoundException classnot){              
                System.err.println("Data received in unknown format!");
            }            
        } catch (IOException e) {
    	   e.printStackTrace();
        }
    }
    private void write(String message) {
    	try {
			out.writeObject(message);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

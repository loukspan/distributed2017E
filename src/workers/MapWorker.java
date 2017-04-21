package workers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import model.Directions;
import backendclient.AndroidClient;

public class MapWorker implements Worker, MapWorkerImp{

	public Map<Integer, Directions> map(){
		final File currentFilePath = new File(AndroidClient.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath());
		
		String DBFOLDER = currentFilePath.getParentFile().getParentFile()+ File.separator+"dbsnfiles"+File.separator+"directs.txt";
		//System.out.println(DBFOLDER);
		Map<Integer, Directions> map=null;
		try{
			map = new HashMap<Integer, Directions>();
			BufferedReader br = new BufferedReader(new FileReader(DBFOLDER));
			String json ="";
	        String line="";
	        int i=0;
	        while (line != null) {
	            line = br.readLine();
	            json = json + line;
	            if(json.contains('"'+"status"+'"')){
	            	line = br.readLine();
	            	json+= line;
		            map.put(i,new Directions(json));
		            i++;
		            json="";
	            }
	        }	        
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return map;
	}

	public void notifyMaster(){
		
	}
	
	
	
	@Override
	public int calculateHash(String str){
		String plaintext = str;
	    int hash = str.hashCode();
	    MessageDigest m;
	    try {
	        m = MessageDigest.getInstance("MD5");
	        m.reset();
	        m.update(plaintext.getBytes());
	        byte[] digest = m.digest();
	        BigInteger bigInt = new BigInteger(1,digest);
	        String hashtext = bigInt.toString(10);
	        // Now we need to zero pad it if you actually want the full 32 chars.
	        while(hashtext.length() < 32 ){
	          hashtext = "0"+hashtext;
	        }
	        int temp = 0;
	        for(int i =0; i<hashtext.length();i++){
	            char c = hashtext.charAt(i);
	            temp+=(int)c;
	        }
	        return hash+temp;
	    } catch (NoSuchAlgorithmException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return hash;
    }
	
	public void sendToReducers(Map<Integer, Directions> map){
		
	}
	
	/**
	 *  
	 * @param slat
	 * @param slon
	 * @param elat
	 * @param elon
	 * @return the distance between coordinates.
	 */
	private double distance(Double slat,Double slon, Double elat,Double elon){
		return Math.pow(Math.abs(elat - slat), 2)+Math.pow(Math.abs(elon-slon), 2);
	}

	@Override
	public void initialize() {
		sendToReducers(map());
		
	}

	@Override
	public void waitForTasksThread() {
		// TODO Auto-generated method stub
		
	}
}
package workers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import model.Directions;

public interface MapWorkerImp extends Worker{
	
	public Map<Integer, Directions> map();
	public void notifyMaster();
	public int calculateHash(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException;
	public void sendToReducers(Map<Integer, Directions> mp);
	
}

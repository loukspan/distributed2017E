package workers;

import java.util.Map;

public class MapWorker extends Worker implements MapWorkerImp{
	
	@Override
	public Map<String, Object> map(Object o1, Object o2){
		return null;
	}
	@Override
	public void notifyMaster(){
		
	}
	
	@Override
	public String calculateHash(String s){
		return null;
	}
	public void sendToReducers(Map<String, Object> mp){
		
	}
}
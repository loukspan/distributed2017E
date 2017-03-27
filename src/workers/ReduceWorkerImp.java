package workers;

import java.util.Map;

public interface ReduceWorkerImp {
	
	public void waitForMasterAck();
	public Map<String, Object> reduce(String dir, Object ob);
	public void sendResults(Map<String, Object> mp);
}

package workers;

import java.util.Map;

public interface ReduceWorkerImp {
	
	public void waitForMasterAck();
	public Map<String, Object> reduce(String dir, Object ob);//antallagi orismatwn-epistrofis
	public void sendResults(Map<String, Object> mp);
}

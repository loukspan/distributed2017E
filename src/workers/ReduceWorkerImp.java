package workers;

import java.util.Map;

import model.Directions;

public interface ReduceWorkerImp {
	
	public void waitForMasterAck();
	public Directions reduce(Map<Integer,Directions> mp);//antallagi orismatwn-epistrofis
	public void sendResults(Directions directions);
}

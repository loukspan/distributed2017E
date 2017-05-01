package master;

import model.Directions;

public interface MasterImp {
	public void initialize();
	public void waitForNewQueriesThread();
	public Directions searchCache(Directions dir);
	public void distributeToMappers();
	public void waitForMappers();
	public void ackToReducers();
	public void collecDataFromReducers();
	public Directions askGoogleDirectionsAPI(double startlon, double startlat, double endlon, double endlat);
	public boolean updateCache(Directions newDir);
	public boolean updateDatabase(String dir, Directions newDir);
	public void sendResultsToClient();
}

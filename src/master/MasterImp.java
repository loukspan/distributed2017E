package master;

import model.Directions;

public interface MasterImp {
	public void initialize();
	public void waitForNewQueriesThread();
	public Directions searchCache(String dir);
	public void distributeToMappers();
	public void waitForMappers();
	public void askToReducers();
	public void collecDataFromReducers();
	public Directions askGoogleDirectionsAPI(String startlon, String startlat, String endlon, String endlat);
	public boolean updateCache(String dir, Directions newDir);
	public boolean updateDatabase(String dir, Directions newDir);
	public void sendResultsToClient();
}

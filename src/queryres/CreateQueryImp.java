package queryres;

import model.Directions;


public interface CreateQueryImp {
	public void getLocation();
	public void createQuery();
	public void sendQueryToServer(Directions askedDirs);
}

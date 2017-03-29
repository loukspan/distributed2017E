package backendclient;

import master.Master;

public class AndroidClient {
	public static void main(String[] args){
		Master master = new Master();
		master.askGoogleDirectionsAPI("33.812092","-117.918974","34.138117","-118.353378");
	}
}

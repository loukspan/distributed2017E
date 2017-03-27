package backendclient;

import master.Master;

public class AndroidClient {
	public static void main(String[] args){
		Master master = new Master();
		master.askGoogleDirectionsAPI("");
	}
}

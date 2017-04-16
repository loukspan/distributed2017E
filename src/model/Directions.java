package model;


public class Directions {
	private String result, startlon, startlat;
	//private ArrayList[]() routes;
	private String dirs;
	public Directions(String dirs){
		this.dirs=dirs;
	}
	public Directions(String result, String startlon, String startlat){
		this.result=result;
		this.startlat=startlat;
		this.startlon=startlon;
	}
	public String getDirs() {
		return dirs;
	}
  
	public String getResult() {
		return result;
	}
	
	public String getStartlon() {
		return startlon;
	}
	
	public String getStartlat() {
		return startlat;
	}
	
}

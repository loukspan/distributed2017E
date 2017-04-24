package queryres;

import model.Directions;


public class ShowResults implements ShowResultsImp{
	
	private Directions ourResult;
	public void getResults(Directions ourResult){
		this.ourResult=ourResult;
	}
	
	public void showResults(){
		System.out.println(this.ourResult.getDirs());
	}
}

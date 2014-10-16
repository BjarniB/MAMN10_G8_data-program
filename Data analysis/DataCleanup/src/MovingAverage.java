import java.util.ArrayList;


public class MovingAverage {

	ArrayList<Double> x;
	
	public MovingAverage(ArrayList<Double> x){
		this.x = x;
	}
	
	// Average of point and the previous n points
	public void parseAverages(){
		for(int i = 1; i < x.size(); i++){
			double avg = (x.get(i) + x.get(i-1)) / 2;
			x.set(i, avg);
		}
	}
	
	public ArrayList<Double> getMovingAverageX(){
		return x;
	}
}

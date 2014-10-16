import java.util.ArrayList;


public class AverageCurve {
	
	private ArrayList<ArrayList<Double>> x;
	private ArrayList<ArrayList<Double>> t;
	private ArrayList<Double> average;
	private ArrayList<Double> tA;
	private int longestIndex;
	
	public AverageCurve(ArrayList<ArrayList<Double>> x, ArrayList<ArrayList<Double>> t){
		this.x = x;
		this.t = t;
		average = new ArrayList<Double>();
	}
	
	public void average(){
		int longest = getLongest();
		for(int i = 0; i < longest; i++){
			average.add(average(i));
		}
		
		tA = t.get(longestIndex); 
	}
	
	private double average(int index){
		double avg = 0;
		for(int i = 0; i < x.size(); i++){
			if(index < x.get(i).size()){
				avg += x.get(i).get(index);
			}
		}
		return (avg/15);
	}
	
	private int getLongest(){
		int ret = 0;
		for(int i = 0; i < x.size(); i++){
			if(x.get(i).size() > ret){
				ret = x.get(i).size();
				longestIndex = i;
			}
		}
		return ret;
	}
	
	public ArrayList<Double> getXAverage(){
		return average;
	}
	
	public ArrayList<Double> getTAverage(){
		return tA;
	}
}

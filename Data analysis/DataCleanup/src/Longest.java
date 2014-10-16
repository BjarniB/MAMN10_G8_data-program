import java.util.ArrayList;


public class Longest {
	private ArrayList<ArrayList<Double>> x;
	private ArrayList<ArrayList<Double>> times;
	
	public Longest(ArrayList<ArrayList<Double>> x,ArrayList<ArrayList<Double>> times){
		this.x = x;
		this.times = times;
	}
	
	public ArrayList<Double> getLongestTime(){
		return times.get(getLongestIndex());
	}
	
	public void putLongestFirst(){
		int index = getLongestIndex();
		ArrayList<Double> lX = x.get(index);
		ArrayList<Double> lT = times.get(index);
		
		x.remove(index);
		times.remove(index);
		
		x.add(0, lX);
		times.add(0, lT);
	}
	
	private int getLongestIndex(){
		int longest = 0;
		int size = 0;
		for(int i = 0; i < x.size(); i++){
			if(x.get(i).size() > size){
				size = x.get(i).size();
				longest = i;
			}
		}
		
		return longest;
	}
	
	public ArrayList<ArrayList<Double>> getCurves(){
		return x;
	}
}
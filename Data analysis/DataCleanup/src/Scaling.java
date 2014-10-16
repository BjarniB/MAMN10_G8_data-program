import java.util.ArrayList;


public class Scaling {

	private ArrayList<ArrayList<Double>> curves;
	private ArrayList<Double> t;
	private int [][] startStopIndexes;
	
	// listor med tider för varje kurva
	private ArrayList<ArrayList<Double>> times;
	
	public Scaling(ArrayList<ArrayList<Double>> curves, ArrayList<Double> t, int[][] ssi){
		this.curves = curves;
		startStopIndexes = ssi;
		this.t = t;
	}
	
	public void scaleCurves(){
		insertTimes();
		scaleX();
		//scaleT();
	}
	
	private void scaleX(){
		for(ArrayList<Double> c : curves){
			scaleThis(c);
		}
	}
	
	private void scaleThis(ArrayList<Double> c){
		double scal = getLowest(c);
		for(int i = 0; i < c.size(); i++){
			c.set(i, c.get(i)-scal);
		}
	}
	
	private double getLowest(ArrayList<Double> c){
		double lowest = Double.MAX_VALUE;
		for(Double d : c){
			if(d < lowest){
				lowest = d;
			}
		}
		return lowest;
	}
	
	private void insertTimes(){
		double tick = 0.04;
		if(times == null){
			times = new ArrayList<ArrayList<Double>>();
		}
		for(int i = 1; i < startStopIndexes.length-1; i++){
			ArrayList<Double> current = new ArrayList<Double>();
			Double cT = 0.00;
			for(int j = startStopIndexes[i][0]; j <= startStopIndexes[i][1]; j++){
				cT = round(cT);
				current.add(cT);
				cT += tick;
			}
			times.add(current);
		}
	}
	
	private double round(double val){
		val = val*100;
		val = Math.round(val);
		val = val /100;
		return val;
	}
	
	public ArrayList<ArrayList<Double>> getCurves(){
		return curves;
	}
	
	public ArrayList<ArrayList<Double>> getTimes(){
		return times;
	}
}

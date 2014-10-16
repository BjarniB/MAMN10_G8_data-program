import java.util.ArrayList;


public class Curves {

	private ArrayList<Double> x;
	private ArrayList<Integer> indexes;
	
	private double acceptanceBottom;
	
	private int[][] startStopIndexes;
	
	private final int LEFT = -1;
	private final int RIGHT = 1;
	
	ArrayList<ArrayList<Double>> curves;
	
	public Curves(ArrayList<Double> x, ArrayList<Integer> i, double acceptanceBottom){
		this.x = x;
		indexes = i;
		this.acceptanceBottom = acceptanceBottom;
		
		curves = new ArrayList<ArrayList<Double>>();
		
		startStopIndexes = new int[indexes.size()][2];
	}
	
	public ArrayList<ArrayList<Double>> getCurvesList(){
		return curves;
	}
	
	public void parseCurves(){
		// TODO implement to fill matrix with one curve per row
		// also save start and stop indexes into a different matrix
		
		curves.removeAll(curves);
		
		// perhaps ignore first and last index due to unusable data
		for(int i = 1; i < indexes.size()-1; i++){
			ArrayList<Double> current = new ArrayList<Double>();
			
			// get left and right indexes
			int left = getSideBottomIndex(indexes.get(i), LEFT);
			int right = getSideBottomIndex(indexes.get(i), RIGHT);
			
			startStopIndexes[i][0] = left;
			startStopIndexes[i][1] = right;
			
			// TODO loop from left to right and insert into current list
			for(int j = left; j <= right; j++){
				current.add(x.get(j));
			}
			curves.add(current);
		}
	}
	
	public int[][] getStartStop(){
		return startStopIndexes;
	}
	
	private int getSideBottomIndex(int topIndex, int side){
		boolean bottomFound = false;
		int index = topIndex;
		
		while (!bottomFound){
			double diff = diff(x.get(index), x.get(index+side));
			if(diff > 0 && x.get(index) < acceptanceBottom){
				bottomFound = true;
			}else{
				index += side;
			}
		}
		
		return index;
	}
	
	private double diff(double pointA, double pointB){
		return (pointB - pointA);
	}
}

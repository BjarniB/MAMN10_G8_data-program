import java.util.ArrayList;


public class Curves {

	private ArrayList<Double> x;
	private ArrayList<Integer> indexes;
	
	private double acceptanceBottom;
	
	private int[][] startStopIndexes;
	
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int BACKWARD = -1;
	public static final int FORWARD = 1;
	
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
			int left = getSideBottomIndex2(indexes.get(i), LEFT);
			int right = getSideBottomIndex2(indexes.get(i), RIGHT);
			
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
	
	// Unused method, version 2 is better
	private int getSideBottomIndex(int topIndex, int side){
		boolean bottomFound = false;
		int index = topIndex;
		
		while (!bottomFound && index > 0 && index < x.size()-1){
			double diff = diff(x.get(index), x.get(index+side));
			if(diff > 0 && x.get(index) < acceptanceBottom){
				bottomFound = true;
			}else{
				index += side;
			}
		}
		
		return index;
	}
	
	// average diff forwards and backwards from the point, so see if really a cutoff point
	private int getSideBottomIndex2(int topIndex, int side){
		boolean bottomFound = false;
		int index = topIndex;
		
		int[] upDown = {-1, 1};
		if(side == -1){
			upDown[0] = 1;
			upDown[1] = -1;
		}
		
		while (!bottomFound && index > 0 && index < x.size()-1){
			double diff = diff(x.get(index), x.get(index+side));
			if(diff > 0){
				double forwardAvg = avgDiff(index,side,FORWARD);
				double backwardAvg = avgDiff(index,side,BACKWARD);
				if(forwardAvg > 0 && backwardAvg > 0){
					bottomFound = true;
				}else{
					index += side;
				}
			}else{
				index += side;
			}
		}
		
		return index;
	}
	
	private double avgDiff(int index, int side, int fwdBwd){
		int direction = side * fwdBwd;
		double [] points = new double[5];
		double diffSum = 0;
		// index is inside the parameters to avoid index out of bounds
		if(index > 5 && index < x.size()-5){
			points[0] = x.get(index + direction);
			points[1] = x.get(index + (direction*2));
			points[2] = x.get(index + (direction*3));
			points[3] = x.get(index + (direction*4));
			points[4] = x.get(index + (direction*5));
		}
		
		int i;
		for(i = 0; i < points.length-1; i++){
			diffSum += diff(points[i],points[i+1]);
		}
		
		return (diffSum / (double)i);
	}
	
	private double diff(double pointA, double pointB){
		return (pointB - pointA);
	}
}

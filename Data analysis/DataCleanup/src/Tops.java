import java.util.ArrayList;


public class Tops {
	
	private double acceptancePercent = 0.3;
	
	private double acceptanceTop = 0;
	private double acceptanceBottom = 0;
	
	private ArrayList<Double> t;
	private ArrayList<Double> x;
	
	private ArrayList<Integer> inds;
	
	public Tops(ArrayList<Double> t, ArrayList<Double> x){
		this.t = t;
		this.x = x;
		
		inds = new ArrayList<Integer>();
		
		setAcceptances();
	}
	
	public ArrayList<Integer> getTopsIndexes(){
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		if(t.size() != x.size()){
			System.out.println("t and x lengths dont match");
			return indexes;
		}
		
		if(acceptanceTop == 0){
			System.out.println("Acceptance top set to ZERO");
			return indexes;
		}
	
		boolean upSlope = false;
		double prev = 0;
		int prevTopIndex = 0;
		for(int i = 0; i < x.size(); i++){
			// TODO iterate through x and find all the max values and save into indexes
			double diff = diff(prev,x.get(i));
			if(diff > 0){
				upSlope = true;
			}else{
				if(upSlope){
					double tDiff = t.get(i) - t.get(prevTopIndex);
					if(prev > acceptanceTop && tDiff >= 1){
						prevTopIndex = i-1;
						indexes.add(i-1);
					}
				}
				upSlope = false;
			}
			prev = x.get(i);
		}
		
		return indexes;
	}
	
	private double diff(double pointA, double pointB){
		return (pointB - pointA);
	}
	
	private void setAcceptances(){
		double smallest = findSmallest();
		double largest = findLargest();
		double diff = (largest - smallest) * acceptancePercent;
		
		acceptanceTop = largest - diff;
		acceptanceBottom = smallest + diff;
	}
	
	private double findSmallest(){
		double smallest = Double.MAX_VALUE;
		for(int i = 0; i < x.size(); i++){
			double curr = x.get(i);
			if(curr < smallest){
				smallest = curr;
			}
		}
		return smallest;
	}
	
	private double findLargest(){
		double largest = 0;
		for(int i = 0; i < x.size(); i++){
			double curr = x.get(i);
			if(curr > largest){
				largest = curr;
			}
		}
		return largest;
	}

	public double getAcceptanceTop(){
		return acceptanceTop;
	}
	
	public double getAcceptanceBottom(){
		return acceptanceBottom;
	}
	
	public void setAcceptancePercent(double p){
		acceptancePercent = p;
		setAcceptances();
	}
	
	// Getting the 17 tops without acceptance
	public ArrayList<Integer> get17Tops(){
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		if(t.size() != x.size()){
			System.out.println("t and x lengths dont match");
			return indexes;
		}
		
		if(acceptanceTop == 0){
			System.out.println("Acceptance top set to ZERO");
			return indexes;
		}
	
		boolean upSlope = false;
		double prev = 0;
		int prevTopIndex = 0;
		for(int i = 0; i < x.size(); i++){
			// TODO iterate through x and find all the max values and save into indexes
			double diff = diff(prev,x.get(i));
			if(diff > 0){
				upSlope = true;
			}else{
				if(upSlope){
					double tDiff = t.get(i) - t.get(prevTopIndex);
					if(tDiff >= 0.5){
						if(indexes.size() == 17){
							indexes = replaceSmallest(indexes, i-1);
						}else{
							prevTopIndex = i-1;
							indexes.add(i-1);
						}
					}
				}
				upSlope = false;
			}
			prev = x.get(i);
		}
		inds = indexes;
		newAcceptances();
		return indexes;
	}
	
	private ArrayList<Integer> replaceSmallest(ArrayList<Integer> indexes, int biggerIndex){
		int smallestIndex = getSmallestIndex(indexes);
		if(x.get(smallestIndex) < x.get(biggerIndex)){
			indexes.remove(indexes.indexOf(smallestIndex));
			indexes.add(biggerIndex);
		}
		return indexes;
	}
	
	private int getSmallestIndex(ArrayList<Integer> indexes){
		double currX = Double.MAX_VALUE;
		int currIndex = 0;
		for(int i = 0; i < indexes.size(); i ++){
			if(x.get(indexes.get(i)) < currX){
				currX = x.get(indexes.get(i));
				currIndex = indexes.get(i);
			}
		}
		
		return currIndex;
	}
	
	private void newAcceptances(){
		int leftBound = getSideIndex(Curves.LEFT);
		int rightBound = getSideIndex(Curves.RIGHT);
		
		double smallest = newSmallest(leftBound,rightBound);
		double largest = newLargest(leftBound,rightBound);
		double diff = (largest - smallest) * acceptancePercent;
		
		acceptanceTop = largest - diff;
		acceptanceBottom = smallest + diff;
	}
	
	private double newSmallest(int leftBound, int rightBound){
		double smallest = Double.MAX_VALUE;
		for(int i = leftBound; i < rightBound; i++){
			double curr = x.get(i);
			if(curr < smallest){
				smallest = curr;
			}
		}
		return smallest;
	}
	
	private double newLargest(int leftBound, int rightBound){
		double largest = 0;
		for(int i = leftBound; i < rightBound; i++){
			double curr = x.get(i);
			if(curr > largest){
				largest = curr;
			}
		}
		return largest;
	}
	
	// Side: -1 for left, 1 for right
	private int getSideIndex(int side){
		int index;
		if(side < 0){
			index = Integer.MAX_VALUE;
		}else{
			index = Integer.MIN_VALUE;
		}
		for(int i = 0; i < inds.size(); i++){
			if(side < 0){
				if(index > inds.get(i))
					index = inds.get(i);
			}else{
				if(index < inds.get(i))
					index = inds.get(i);
			}
		}
		return index;
	}
}

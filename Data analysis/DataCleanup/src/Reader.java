import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reader class for reading raw data files.
 * No cleanup is done in this class only simple reading of raw file file
 * @author Bjarni
 */
public class Reader {
	
	private String firstLine;
	private String secondLine;
	private ArrayList<Double> x;
	private ArrayList<Double> t;

	
	public Reader(){
		x = new ArrayList<Double>();
		t = new ArrayList<Double>();
	}
	
	public void readFile(File f){
		if(x == null || t == null){
			return;
		}

		x.removeAll(x);
		t.removeAll(t);
		
		Scanner scan;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		firstLine = scan.nextLine();
		secondLine = scan.nextLine();
		
		// read through all lines of file and parse values to doubles
		// parsing to floats results in loss of decimal points
		while(scan.hasNext()){
			String line = scan.nextLine();
			String[] split = line.split("\t");
			
			double tc = Double.parseDouble(split[0]);
			t.add(tc);
			
			double xc = Double.parseDouble(split[1]);
			x.add(xc);
		}
		
		System.out.println("File read");
	}
	
	//TODO add method for handling files with Esponentials in data file, "1,978173E2" = 197.8173
	
	public ArrayList<Double> getX(){
		return x;
	}
	
	public ArrayList<Double> getT(){
		return t;
	}
	
	public String getFirstLine(){
		return firstLine;
	}
	
	public String getSecondLine(){
		return secondLine;
	}
}

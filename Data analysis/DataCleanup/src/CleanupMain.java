import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CleanupMain {
	
	private final static String defaultOutputDir = "/output/";

	private static ArrayList<String> actions;
	private static ArrayList<String> subs;
	private static ArrayList<String> points;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
		
		String action = new String();
		String subaction = new String();
		String point = new String();
		boolean totalAvg = false;
		boolean individualAvg = false;
		String output = defaultOutputDir;
		String sourcefile = new String();
		String targetfile = new String();
		boolean debug = false;
		try {
			System.out.println(new File(".").getCanonicalPath());

		} catch (IOException e) {
		    System.err.println("Caught IOException: " + e.getMessage());
		}

		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-ta")){
				if(args[i+1].contains("1"))
					totalAvg = true;
				i++;
			}else if(args[i].equals("-ia")){
				if(args[i+1].contains("1"))
					individualAvg = true;
				i++;
			}else if(args[i].equals("-o")){
				output = args[i+1];
				i++;
			}else if(actions.contains(args[i])){
				action = args[i];
			}else if(subs.contains(args[i])){
				subaction = args[i];
			}else if(points.contains(args[i])){
				point = args[i];
			}else if(args[i].equals("-s")){
				sourcefile = args[i+1];
				
				i++;
			}else if(args[i].equals("-t")){
				targetfile = args[i+1];
				i++;
			}else if(args[i].equals("-debug")){
				debug = true;
			}

		}
		if (targetfile.isEmpty()) {
			targetfile = sourcefile + ".out.csv";
		}
		if(debug){
			System.out.println("got " + sourcefile);
			System.out.println("writing to " + targetfile);
		}
		// String dir = output + ....
		//String dir = "C:\\Users\\Bjarni\\Desktop\\Interaktion\\filmer\\analys\\smoothing\\";
		//String[] files = getFilesFromDir(dir);
		
		//ArrayList<String> relevant = getRelevantFiles(dir, dir, dir, files);

		//Byta filename til arg
		File f = new File(sourcefile);
				//"C:\\Users\\Bjarni\\Desktop\\Interaktion\\filmer\\analys\\smoothing\\Bjarni_shove2_hand_raw.txt");
		Reader r = new Reader();
		try {
			r.readFile(f);
			
		}catch (java.lang.ArrayIndexOutOfBoundsException e){
			System.out.println("***problem: " + sourcefile);
		}
		
		Writer w = new Writer();

		MovingAverage ma = new MovingAverage(r.getX());
		ma.parseAverages();
		
		Tops t = new Tops(r.getT(), ma.getMovingAverageX());

		// t.setAcceptancePercent(0.2);

		ArrayList<Integer> indexes = t.get17Tops();
		indexes.trimToSize();
		
		// TODO test every step from this point on with otherIndexes that used the get17Tops method
		Curves c = new Curves(ma.getMovingAverageX(),indexes, t.getAcceptanceBottom());
		
		try {
			c.parseCurves();
		} catch(java.lang.IndexOutOfBoundsException e){
			System.out.println("***problem: " + sourcefile);
		}
		
		ArrayList<ArrayList<Double>> curves = c.getCurvesList();
		int[][] startStop = c.getStartStop();
		
		
		Scaling scal = new Scaling(curves, r.getT(), startStop);
		scal.scaleCurves();

		
		curves = scal.getCurves();
		ArrayList<ArrayList<Double>> times = scal.getTimes();

		
		//AverageCurve avg= new AverageCurve(curves, times);
		//avg.average();
		
		Longest l = new Longest(curves, times);
		l.putLongestFirst();
		ArrayList<Double> lT = l.getLongestTime();
		

		/*
		w.writeToFile(
				r.getFirstLine(),
				r.getSecondLine(),
				times,
				curves,
				targetfile,
				//"C:\\Users\\Bjarni\\Desktop\\Interaktion\\filmer\\analys\\smoothing\\Bjarni_shove2_hand_hump.txt"
				);
		// w.saveHumpsToFile("C:\\Users\\Bjarni\\Desktop\\Interaktion\\filmer\\Bjarni robot\\Bjarni_peka_hand_hump.txt");
		
		w.writeAverage(
				r.getFirstLine(),
				r.getSecondLine(),
				avg.getTAverage(),
				avg.getXAverage(),
				"C:\\Users\\Bjarni\\Desktop\\Interaktion\\filmer\\analys\\smoothing\\Bjarni_shove2_hand_avg.txt");
		*/
		w.writeHumpFile(lT, l.getCurves(), 
			targetfile
			//"C:\\Users\\Bjarni\\Desktop\\Interaktion\\filmer\\analys\\smoothing\\humps.txt"
			);

	}
	
	private static void init(){
		actions = new ArrayList<String>();
		actions.add("peka");
		actions.add("push");
		actions.add("pull");
		actions.add("shove");
		actions.add("sweep");
		
		subs = new ArrayList<String>();
		subs.add("12");
		subs.add("13");
		subs.add("23");
		subs.add("31");
		subs.add("32");
		subs.add("21");
		subs.add("1");
		subs.add("2");
		subs.add("3");
		
		points = new ArrayList<String>();
		points.add("hand");
		points.add("armbage");
		points.add("axel");
	}
	
	//Get all files in the specified directory
	private static String[] getFilesFromDir(String dir){
		File f = new File(dir);
		return f.list();
	}
	
	private ArrayList<String> getRelevantFiles(String action, String sub, String point, String[] files){
		ArrayList<String> relevant = new ArrayList<String>();
		if(sub.equals("")){
			sub = "_";
		}
		for(int i = 0; i < files.length; i++){
			if(files[i].contains(action) && files[i].contains(sub) && files[i].contains(point)){
				relevant.add(files[i]);
			}
		}
		return relevant;
	}
	
	// TODO hjälp metod om ett input saknas

	// TODO metoder för att göra olika saker beroende på input (skriver humps,
	// kör scaling och average)
}

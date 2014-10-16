import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Writer {

	public Writer() {

	}

	public void saveHumpsToFile(String fileName) {
		BufferedWriter writer;
		File nF = new File(fileName);

		try {
			if (!nF.exists()) {

				nF.createNewFile();

			}

			writer = new BufferedWriter(new FileWriter(nF));

			// TODO loop through the lists of handled data and write to file
			writer.write("lala");
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToFile(String firstLine, String secondLine,
			ArrayList<ArrayList<Double>> t, ArrayList<ArrayList<Double>> x,
			String filename) {
		BufferedWriter writer;
		File nF = new File(filename);

		try {
			if (!nF.exists()) {

				nF.createNewFile();

			}

			writer = new BufferedWriter(new FileWriter(nF));

			// TODO loop through the lists of handled data and write to file
			// writer.write(firstLine + "\n");
			// writer.flush();
			writer.write(secondLine + "\n");
			writer.flush();

			for (int i = 0; i < x.size(); i++) {
				ArrayList<Double> currList = x.get(i);
				ArrayList<Double> currTime = t.get(i);
				for (int j = 0; j < currList.size(); j++) {
					writer.write(currTime.get(j) + "\t" + currList.get(j)
							+ "\n");
					writer.flush();
				}

			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeAverage(String firstLine, String secondLine,
			ArrayList<Double> t, ArrayList<Double> x, String filename) {
		BufferedWriter writer;
		File nF = new File(filename);

		try {
			if (!nF.exists()) {

				nF.createNewFile();

			}

			writer = new BufferedWriter(new FileWriter(nF));

			// TODO loop through the lists of handled data and write to file
			// writer.write(firstLine + "\n");
			// writer.flush();
			writer.write(secondLine + "\n");
			writer.flush();

			for (int i = 0; i < x.size(); i++) {
				Double currX = x.get(i);
				Double currTime = t.get(i);

				writer.write(currTime + "\t" + currX + "\n");
				writer.flush();

			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeHumpFile(ArrayList<Double> t, ArrayList<ArrayList<Double>> x, String filename){
		BufferedWriter writer;
		File nF = new File(filename);

		try {
			if (!nF.exists()) {

				nF.createNewFile();

			}

			writer = new BufferedWriter(new FileWriter(nF));

			// Write first row of times
			for(int i = 0; i < t.size(); i++){
				writer.write(t.get(i).toString() + "\t");
			}
			
			writer.write("\n");
			writer.flush();
			
			//Write each curve on its own row
			for (int i = 0; i < x.size(); i++) {
				ArrayList<Double> currX = x.get(i);
				
				for(int j = 0; j < t.size(); j++){
					if(j >= currX.size()){
						writer.write("0\t");
					}else{
						writer.write(currX.get(j).toString() + "\t");
					}
				}
				writer.write("\n");
				writer.flush();
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

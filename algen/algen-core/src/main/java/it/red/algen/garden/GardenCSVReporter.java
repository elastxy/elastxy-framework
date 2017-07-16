package it.red.algen.garden;

import it.red.algen.stats.Stats;
import it.red.algen.tracking.CSVReporter;

import java.util.ArrayList;
import java.util.List;

public class GardenCSVReporter extends CSVReporter {

	public GardenCSVReporter(String path){
		super(path);
	}
	
	protected List<String[]> createCustomPart(Stats stats) {
		List<String[]> result = new ArrayList<String[]>();
		result.add(new String[] {"Placement", "Happyness (1=happy, 0=dead)"}); 
		String[] bestMatchCSV = stats._lastGeneration.getBestMatch().toString().split(";");
		int happy = 0;
		int unhappy = 0;
		int dead = 0;
		for(String b : bestMatchCSV){
			String happyness = "";
			if(b.indexOf("0.0")!=-1){
				happyness = "1"; happy++;
			}
			else if(b.indexOf("1.0")!=-1){
				happyness = "0";dead++;
			}
			else {
				unhappy++;
			}
			result.add(new String[] {b, happyness});
		}
		result.add(0, new String[] {"Tree happiness", happy+" :) happy,  "+unhappy+" :( unhappy, "+dead+" :| dead  "});
		return result;
	}
}

package it.red.algen.tracking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import it.red.algen.stats.ExperimentStats;


public class CSVReporter implements Reporter {
	private String _outputDir = null;
	
	public CSVReporter(String path){
		_outputDir = path;
	}
	
	public void setOutputDir(String path){
		_outputDir = path;
	}
	
	public void createReports(ExperimentStats stats) {
		try {
			File outputDir = new File(_outputDir);
			outputDir.mkdirs();
			CSVWriter writer = new CSVWriter(new FileWriter(new File(outputDir, "results_gen"+stats._generations+"_pop"+stats._lastGeneration.size()+"_"+Calendar.getInstance().getTimeInMillis()+".csv")), ';');
			List<String[]> csv = new ArrayList<String[]>();
			csv.add(new String[] {"FITNESS", String.format("%1.3f", stats._lastGeneration.bestMatch.getFitness().getValue())});
			csv.add(new String[] {"Last population size", String.valueOf(stats._lastGeneration.size())});
			csv.add(new String[] {"Number of generations", String.valueOf(stats._generations)});
			csv.add(new String[] {"Total time (sec)", String.valueOf(stats._time) });
			
			List<String[]> customPart = createCustomPart(stats);
			csv.addAll(customPart);
			
			writer.writeAll(csv);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("Cannot create report. Ex: "+e, e);
		}
	}

	protected List<String[]> createCustomPart(ExperimentStats stats){
		return new ArrayList<String[]>(0);
	}
	
}

/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.elastxy.core.tracking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.elastxy.core.engine.core.AlgorithmException;
import org.elastxy.core.stats.ExperimentStats;

import au.com.bytecode.opencsv.CSVWriter;


public class CSVReporter implements Reporter, Serializable {
	private String outputDir = null;
	
	public CSVReporter(String path){
		outputDir = path;
	}
	
	public void setOutputDir(String path){
		outputDir = path;
	}
	
	// TODO2-2: ResultsRenderer: reuse?
	public void createReports(ExperimentStats stats) {
		try {
			File outputDir = new File(this.outputDir);
			outputDir.mkdirs();
			CSVWriter writer = new CSVWriter(new FileWriter(new File(outputDir, "results_gen"+stats.generations+"_pop"+stats.lastGeneration.size()+"_"+Calendar.getInstance().getTimeInMillis()+".csv")), ';');
			List<String[]> csv = new ArrayList<String[]>();
			csv.add(new String[] {"FITNESS", String.format("%1.20f", stats.bestMatch.getFitness().getValue())});
			csv.add(new String[] {"Last population size", String.valueOf(stats.lastGeneration.size())});
			csv.add(new String[] {"Number of generations", String.valueOf(stats.generations)});
			csv.add(new String[] {"Total execution time (ms)", String.valueOf(stats.executionTimeMs) });
			
			List<String[]> customPart = createCustomPart(stats);
			csv.addAll(customPart);
			
			writer.writeAll(csv);
			writer.close();
		} catch (IOException e) {
			throw new AlgorithmException("Cannot create report. Ex: "+e, e);
		}
	}

	protected List<String[]> createCustomPart(ExperimentStats stats){
		return new ArrayList<String[]>(0);
	}
	
}

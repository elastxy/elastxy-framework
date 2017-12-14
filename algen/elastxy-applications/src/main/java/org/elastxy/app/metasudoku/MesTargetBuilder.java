package org.elastxy.app.metasudoku;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.conf.ReadConfigSupport;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.PerformanceTarget;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.engine.factory.TargetBuilder;

public class MesTargetBuilder implements TargetBuilder<int[][], Integer> {
	private Logger logger = Logger.getLogger(MesTargetBuilder.class);

	private AlgorithmContext context;
	
	@Override
	public void setup(AlgorithmContext context){
		this.context = context;
	}
	
	@Override
	public Target<int[][], Integer> define(WorkingDataset dataset) {
    	// Defines goal representation
    	PerformanceTarget target = new PerformanceTarget();
    	target.setGoal(createGoal());

    	// Determines goal rough measure by deriving from extreme solutions
    	// 27 is the number of rows, columns, squares with numbers 1 to 9
    	target.setReferenceMeasure(MesConstants.TOTAL_COMPLETED);
		return target;
	}

    
    /**
     * Simple Sudoku matrix
     * 
     * @return
     */
    private int[][] createGoal(){
		String classpathResource = "/"+context.application.appName+"/target.json";
		try {
			return (int[][])ReadConfigSupport.readJSON(classpathResource, int[][].class);
		} catch (IOException e) {
			String msg = "Error while reading JSON from classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
    }


}

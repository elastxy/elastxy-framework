package org.elastxy.app.metasudoku;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.PerformanceTarget;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.engine.factory.TargetBuilder;
import org.elastxy.core.support.JSONSupport;

/**
 * 		
 * 	    "target" : { 
			"matrix" : "[[0,0,7,0,0,9,1,0,0],[0,0,1,0,0,5,0,9,0],[0,0,0,8,0,0,5,0,7],[0,0,0,0,0,6,0,1,5],[0,0,3,0,0,0,9,0,0],[1,5,0,7,0,0,0,0,0],[3,0,6,0,0,8,0,0,0],[0,1,0,6,0,0,2,0,0],[0,0,5,4,0,0,7,0,0]]"
		}
			
	 	"target" : { 
			"matrix" : "[[1,0,3,0,8,5,6,2,0],[0,5,2,1,6,7,8,3,9],[9,8,6,0,3,4,7,0,1],[7,2,0,8,4,3,9,6,5],[3,9,5,6,7,0,0,4,8],[8,6,0,5,9,0,2,0,3],[6,4,0,0,2,9,0,1,7],[2,1,7,4,5,8,3,9,6],[5,0,9,7,1,0,4,8,0]]"
		}

 * @author red
 *
 */
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
    	Object matrix = context.applicationSpecifics.target.get("matrix");
    	int[][] intMatrix = matrix==null||!(matrix instanceof List) ? readGoal() : toIntArray((List)matrix); 
    	target.setGoal(intMatrix);

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
    private int[][] readGoal(){
		String classpathResource = JSONSupport.checkClasspathResource(context.application.appName, "target.json");
		try {
			return (int[][])JSONSupport.readJSON(classpathResource, int[][].class);
		} catch (IOException e) {
			String msg = "Error while reading JSON from classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
    }
    
    
    private int[][] toIntArray(List matrix){
    	int totMatrix = matrix.size();
    	int[][] array = new int[totMatrix][];
    	for (int i = 0; i < totMatrix; i++) {
    	    List<Integer> row = (List<Integer>)matrix.get(i);
    	    int totRows = row.size();
    	    array[i] = new int[totRows];
    	    for(int j = 0; j < totRows; j++){
    	    	array[i][j] = row.get(j);
    	    }
    	}
    	return array;
    }


}

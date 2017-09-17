/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.metasudoku;

import java.io.IOException;

import org.apache.log4j.Logger;

import it.red.algen.conf.ConfigurationException;
import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.dataaccess.AbstractEnvFactory;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.PredefinedGenoma;

/**
 * TODOA: remove duplications with other factories
 *
 * @author grossi
 */
public class MesEnvFactory extends AbstractEnvFactory<int[][], Integer, PredefinedGenoma> {
	private Logger logger = Logger.getLogger(MesEnvFactory.class);
	
	@Override
	protected Target<int[][], Integer> defineTarget(Genoma genoma) {
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
		String classpathResource = "/"+context.application.name+"/target.json";
		try {
			return (int[][])ReadConfigSupport.readJSON(classpathResource, int[][].class);
		} catch (IOException e) {
			String msg = "Error while reading JSON from classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
    }


}

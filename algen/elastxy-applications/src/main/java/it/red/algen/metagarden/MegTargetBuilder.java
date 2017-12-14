package it.red.algen.metagarden;

import org.apache.log4j.Logger;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.engine.factory.TargetBuilder;
import it.red.algen.metagarden.data.MegWorkingDataset;
import it.red.algen.metasudoku.MesTargetBuilder;


public class MegTargetBuilder implements TargetBuilder<String, Double> {
	private Logger logger = Logger.getLogger(MesTargetBuilder.class);

	private AlgorithmContext context;
	
	@Override
	public void setup(AlgorithmContext context){
		this.context = context;
	}
	
	@Override
	public PerformanceTarget<String, Double> define(WorkingDataset dataset) {
		// TODOM-2: evaluate a specific target builder in distributed environment to set overall goals?
		if(!(dataset instanceof MegWorkingDataset)){
			return null;
		}
		PerformanceTarget<String,Double> target = new PerformanceTarget<String,Double>();
    	target.setGoal(context.applicationSpecifics.getTargetString(MegConstants.TARGET_WELLNESS));
    	// Determines goal rough measure: minimum possible unhappiness (illness), 0.0
    	target.setReferenceMeasure(((MegWorkingDataset)dataset).getPlacesNumber() * 2.0);  // 2 is the maximum value happiness can reach
		return target;
	}
	
}

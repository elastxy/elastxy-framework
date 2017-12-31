package org.elastxy.distributed.context;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.util.LongAccumulator;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.distributed.dataprovider.BroadcastWorkingDataset;


/**
 * Input parameters for the single colony closure execution.
 * @author red
 *
 */
public class SingleColonyClosureContext implements Serializable {
	private static final long serialVersionUID = 4152663749296949868L;

	public long currentEraNumber;
	public AlgorithmContext algorithmContext;
	public Target target;
	public LongAccumulator coloniesGoalAccumulator;
	public Broadcast<List<Allele>> mutatedGenesBC;
	public Broadcast<List<Solution>> previousBestMatchesBC;
	public Map<String, BroadcastWorkingDataset> broadcastDatasets;


	public void check(){
		if(algorithmContext==null) throw new IllegalArgumentException("Single Colony Closure: missing context."); 
		if(algorithmContext.application.appName==null || algorithmContext.application.appName.trim().length()==0) 
			throw new IllegalArgumentException("Single Colony Closure: missing appName in context."); 
		if(target==null) throw new IllegalArgumentException("Single Colony Closure: missing target."); 
		if(coloniesGoalAccumulator==null) throw new IllegalArgumentException("Single Colony Closure: missing coloniesGoalAccumulator."); 
	}
}

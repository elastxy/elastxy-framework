package org.elastxy.app.d.algofrigerator;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elastxy.app.algofrigerator.data.MefWorkingDataset;
import org.elastxy.app.algofrigerator.data.Recipe;
import org.elastxy.app.algofrigerator.data.RecipeType;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.distributed.dataprovider.BroadcastWorkingDataset;
import org.elastxy.distributed.dataprovider.BroadcastedDatasetProvider;


/**
 * Retrieves data locally by accessing to
 * broadcasted variable instead of CSV files.
 * @author red
 *
 */
public class MefSingleColonyDatasetProvider implements BroadcastedDatasetProvider {
	private static Logger logger = Logger.getLogger(MefSingleColonyDatasetProvider.class);

	private AlgorithmContext context;
	private Map<String, BroadcastWorkingDataset> broadcastDatasets;

	
	public void setup(AlgorithmContext context){
		this.context = context;
	}
	
	private MefWorkingDataset workingDataset;
	
	@Override
	public WorkingDataset getWorkingDataset(){
		return workingDataset;
	}
	
	
	@Override
	public void collect() {
		workingDataset = new MefWorkingDataset();
		Object feasibleRecipes = broadcastDatasets.get(MefWorkingDataset.FEASIBLE_RECIPES).bc.getValue();
		
		if(feasibleRecipes==null || !(feasibleRecipes instanceof Map<?,?>)){
			String message = String.format("Broadcasted variable for feasible recipes null or wrong typed: %s", feasibleRecipes==null?null:feasibleRecipes.getClass());
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		workingDataset.feasibleByType = (Map<RecipeType,List<Recipe>>)feasibleRecipes;
		workingDataset.indicize();
	}
	

	@Override
	public void shrink(Target<?, ?> target){
	}


	@Override
	public void setBroadcastDatasets(Map<String, BroadcastWorkingDataset> broadcastDatasets) {
		this.broadcastDatasets = broadcastDatasets;
	}
	
	
}

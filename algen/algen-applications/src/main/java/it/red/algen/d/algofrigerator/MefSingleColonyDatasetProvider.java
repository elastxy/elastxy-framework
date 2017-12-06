package it.red.algen.d.algofrigerator;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.red.algen.algofrigerator.data.MefWorkingDataset;
import it.red.algen.algofrigerator.data.Recipe;
import it.red.algen.algofrigerator.data.RecipeType;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.distributed.dataprovider.BroadcastWorkingDataset;
import it.red.algen.distributed.dataprovider.BroadcastedDatasetProvider;
import it.red.algen.domain.experiment.Target;
import it.red.algen.metagarden.data.MegWorkingDataset;
import it.red.algen.metagarden.data.Place;
import it.red.algen.metagarden.data.Tree;


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
		Object allRecipes = broadcastDatasets.get(MefWorkingDataset.ALL_RECIPES).bc.getValue();
		Object feasibleRecipes = broadcastDatasets.get(MefWorkingDataset.FEASIBLE_RECIPES).bc.getValue();
		
		if(allRecipes==null || !(allRecipes instanceof Map<?, ?>)){
			String message = String.format("Broadcasted variable for all recipes null or wrong typed: %s", allRecipes==null?null:allRecipes.getClass());
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		if(feasibleRecipes==null || !(feasibleRecipes instanceof Map<?,?>)){
			String message = String.format("Broadcasted variable for feasible recipes null or wrong typed: %s", feasibleRecipes==null?null:feasibleRecipes.getClass());
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		workingDataset.recipeById = (Map<Long,Recipe>)allRecipes;
		workingDataset.feasibleByType = (Map<RecipeType,List<Recipe>>)feasibleRecipes;
	}
	

	@Override
	public void shrink(Target<?, ?> target){
	}


	@Override
	public void setBroadcastDatasets(Map<String, BroadcastWorkingDataset> broadcastDatasets) {
		this.broadcastDatasets = broadcastDatasets;
	}
	
	
}

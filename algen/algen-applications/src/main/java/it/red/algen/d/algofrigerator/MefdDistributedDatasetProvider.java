package it.red.algen.d.algofrigerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.red.algen.algofrigerator.data.MefWorkingDataset;
import it.red.algen.algofrigerator.data.Recipe;
import it.red.algen.algofrigerator.data.RecipeType;
import it.red.algen.distributed.dataprovider.BroadcastWorkingDataset;
import it.red.algen.distributed.dataprovider.ProcessingOnlyDistributedDatasetProvider;
import it.red.algen.metagarden.data.GardenDatabaseCSV;
import it.red.algen.metagarden.data.Place;

/**
 * TODOA-4: bloccare le interfacce in ottica SDK!
 * @author red
 *
 */
public class MefdDistributedDatasetProvider extends ProcessingOnlyDistributedDatasetProvider {
	private static Logger logger = Logger.getLogger(MefdDistributedDatasetProvider.class);

	private Map<String, BroadcastWorkingDataset> broadcastDataset;

	@Override
	public Map<String, BroadcastWorkingDataset> getBroadcastDatasets(){
		return broadcastDataset;
	}
	
	
	
	/**
	 * Broadcast feasible recipes file to all nodes.
	 * 
	 * Delegates Recipes list creation to old MefDatasetProvider.
	 * TODOA-2: evaluate injection of localDatasetProvider component into distributed.
	 * 
	 */
	@Override
	public void broadcast(){
		if(logger.isInfoEnabled()) logger.info("Broadcasting local recipes to colonies.");
		
		// Check if local data are present
		if(context.application.datasetProvider==null || context.application.datasetProvider.getWorkingDataset()==null){
			String message = "Expecting a local dataset provider with data to be spread out. Please provide one.";
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		MefWorkingDataset localDataset = (MefWorkingDataset)context.application.datasetProvider.getWorkingDataset();
		
		broadcastDataset = new HashMap<String, BroadcastWorkingDataset>();
		BroadcastWorkingDataset<Map<RecipeType,List<Recipe>>> feasibleRecipes = new BroadcastWorkingDataset<Map<RecipeType,List<Recipe>>>();
		feasibleRecipes.bc = context.distributedContext.broadcast(localDataset.feasibleByType);
		broadcastDataset.put(MefWorkingDataset.FEASIBLE_RECIPES, feasibleRecipes);
	}
	

}

package org.elastxy.app.d.metagarden;

import java.util.Map;

import org.apache.log4j.Logger;
import org.elastxy.app.metagarden.data.GardenDatabaseCSV;
import org.elastxy.app.metagarden.data.MegWorkingDataset;
import org.elastxy.app.metagarden.data.Place;
import org.elastxy.app.metagarden.data.Tree;
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
public class MegSingleColonyDatasetProvider implements BroadcastedDatasetProvider {
	private static Logger logger = Logger.getLogger(MegSingleColonyDatasetProvider.class);

	private AlgorithmContext context;
	private Map<String, BroadcastWorkingDataset> broadcastDatasets;

	
	public void setup(AlgorithmContext context){
		this.context = context;
	}
	
	private MegWorkingDataset workingDataset;
	
	@Override
	public WorkingDataset getWorkingDataset(){
		return workingDataset;
	}
	
	
	@Override
	public void collect() {
		workingDataset = new MegWorkingDataset();
		Object places = broadcastDatasets.get(GardenDatabaseCSV.PLACES_FILENAME).bc.getValue();
		Object trees = broadcastDatasets.get(GardenDatabaseCSV.TREES_FILENAME).bc.getValue();
		
		if(places==null || !(places instanceof Place[])){
			String message = String.format("Broadcasted variable for places null or wrong typed: %s", places==null?null:places.getClass());
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		if(trees==null || !(trees instanceof Tree[])){
			String message = String.format("Broadcasted variable for trees null or wrong typed: %s", trees==null?null:trees.getClass());
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		workingDataset.places = (Place[])places;
		workingDataset.trees = 	(Tree[])trees;
	}
	

	@Override
	public void shrink(Target<?, ?> target){
	}


	@Override
	public void setBroadcastDatasets(Map<String, BroadcastWorkingDataset> broadcastDatasets) {
		this.broadcastDatasets = broadcastDatasets;
	}
	
	
}

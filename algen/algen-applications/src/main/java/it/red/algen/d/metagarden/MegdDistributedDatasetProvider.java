package it.red.algen.d.metagarden;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import it.red.algen.distributed.dataprovider.BroadcastWorkingDataset;
import it.red.algen.distributed.dataprovider.ProcessingOnlyDistributedDatasetProvider;
import it.red.algen.metagarden.data.GardenDatabaseCSV;
import it.red.algen.metagarden.data.Place;
import it.red.algen.metagarden.data.Tree;

/**
 * TODOD: bloccare le interfacce in ottica SDK!
 * @author red
 *
 */
public class MegdDistributedDatasetProvider extends ProcessingOnlyDistributedDatasetProvider {
	private static Logger logger = Logger.getLogger(MegdDistributedDatasetProvider.class);

	private Map<String, BroadcastWorkingDataset> broadcastDataset;

	@Override
	public Map<String, BroadcastWorkingDataset> getBroadcastDatasets(){
		return broadcastDataset;
	}
	
	/**
	 * Broadcast garden trees and positions file to all nodes.
	 */
	@Override
	public void broadcast(){
		if(logger.isInfoEnabled()) logger.info("Broadcasting trees and places lists to colonies.");
		
		GardenDatabaseCSV db = new GardenDatabaseCSV(context.application.name);
		Place[] places = db.getAllPlaces();
		Tree[] trees = db.getAllTrees();
		
		broadcastDataset = new HashMap<String, BroadcastWorkingDataset>();
		
		BroadcastWorkingDataset<Place[]> distrPlaces = new BroadcastWorkingDataset<Place[]>();
		distrPlaces.bc = context.distributedContext.broadcast(places);
		broadcastDataset.put(GardenDatabaseCSV.PLACES_FILENAME, distrPlaces);

		BroadcastWorkingDataset<Tree[]> distrTrees = new BroadcastWorkingDataset<Tree[]>();
		distrTrees.bc = context.distributedContext.broadcast(trees);
		broadcastDataset.put(GardenDatabaseCSV.TREES_FILENAME, distrTrees);
	}
	

}

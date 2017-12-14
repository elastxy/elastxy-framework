package org.elastxy.app.metagarden.data;

import org.apache.log4j.Logger;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.DatasetProvider;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Target;

public class MegDatasetProvider implements DatasetProvider {
	private static Logger logger = Logger.getLogger(MegDatasetProvider.class);

	private AlgorithmContext context;

	private GardenDatabase db;
	
	public void setup(AlgorithmContext context){
		this.context = context;
	}
	
	private MegWorkingDataset workingDataset;
	
	@Override
	public WorkingDataset getWorkingDataset(){
		return workingDataset;
	}
	
	
	/**
	 * Dataset initially collects the list of recipes database, classified by recipe type
	 */
	@Override
	public void collect() {
		db = new GardenDatabaseCSV(context.application.appFolder);
		workingDataset = new MegWorkingDataset();
		workingDataset.places = db.getAllPlaces();
		workingDataset.trees = 	db.getAllTrees();
	}
	

	@Override
	public void shrink(Target<?, ?> target){
	}
	
	
}

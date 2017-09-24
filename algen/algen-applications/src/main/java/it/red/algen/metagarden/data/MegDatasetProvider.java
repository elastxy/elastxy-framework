package it.red.algen.metagarden.data;

import org.apache.log4j.Logger;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.WorkingDataset;
import it.red.algen.dataaccess.DatasetProvider;
import it.red.algen.domain.experiment.Target;

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
		db = new GardenDatabaseCSV(context.application.name);
		workingDataset = new MegWorkingDataset();
		workingDataset.places = db.getAllPlaces();
		workingDataset.trees = 	db.getAllTrees();
	}
	

	@Override
	public void shrink(Target<?, ?> target){
	}
	
	
}

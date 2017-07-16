/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden;

import it.red.algen.Env;
import it.red.algen.EnvFactory;
import it.red.algen.Population;
import it.red.algen.Target;
import it.red.algen.garden.domain.GardenDatabase;
import it.red.algen.garden.domain.GardenDatabaseCSV;
import it.red.algen.garden.domain.Place;
import it.red.algen.garden.domain.Tree;

/**
 *
 * @author grossi
 */
public class GardenEnvFactory implements EnvFactory {
	private GardenDatabase database = new GardenDatabaseCSV(GardenConf.DATABASE_DIR);
	
	public GardenEnvFactory(){
	}
	
    public Env create(int maxIterations, int maxLifetime, Integer maxIdenticalFitnesses){
    	
        // Crea la popolazione iniziale
    	GardenPopulationFactory gardenPopulationFactory = new GardenPopulationFactory();
    	Place[] places = database.getAllPlaces();
    	Tree[] trees = database.getAllTrees();
    	gardenPopulationFactory.init(places, trees);
        Population startGen = gardenPopulationFactory.createNew(GardenConf.INITIAL_POPULATION);
        
        // Definisce l'ambiente di riproduzione
        Target target = new GardenTarget(places, trees);
        return new Env(startGen, target, maxIterations, maxLifetime, maxIdenticalFitnesses);
    }

	
	public GardenDatabase getDatabase() {
		return database;
	}

	public void setDatabase(GardenDatabase database) {
		this.database = database;
	}

}

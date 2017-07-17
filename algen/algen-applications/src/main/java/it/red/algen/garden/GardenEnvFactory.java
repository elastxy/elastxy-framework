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

import org.springframework.stereotype.Component;

import it.red.algen.Env;
import it.red.algen.EnvFactory;
import it.red.algen.Population;
import it.red.algen.Target;
import it.red.algen.conf.AlgorithmContext;
import it.red.algen.garden.domain.GardenDatabase;
import it.red.algen.garden.domain.GardenDatabaseCSV;
import it.red.algen.garden.domain.Place;
import it.red.algen.garden.domain.Tree;

/**
 *
 * @author grossi
 */
@Component
public class GardenEnvFactory implements EnvFactory {
	private GardenDatabase database = new GardenDatabaseCSV(GardenConf.DATABASE_DIR);
	
    public Env create(AlgorithmContext context, Target target){
    	
        // Crea la popolazione iniziale
    	GardenPopulationFactory gardenPopulationFactory = new GardenPopulationFactory();
    	Place[] places = database.getAllPlaces();
    	Tree[] trees = database.getAllTrees();
    	gardenPopulationFactory.init(places, trees);
        Population startGen = gardenPopulationFactory.createNew(context.parameters, GardenConf.INITIAL_POPULATION);
        
        // Definisce l'ambiente di riproduzione
        // TODOM: target from outside!
        Target mytarget = new GardenTarget(places, trees);
        Env env = new Env();
        env.init(context, startGen, mytarget);
        return env;
    }

	
	public GardenDatabase getDatabase() {
		return database;
	}

	public void setDatabase(GardenDatabase database) {
		this.database = database;
	}

}

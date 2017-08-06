/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.domain.Env;
import it.red.algen.domain.Population;
import it.red.algen.engine.interfaces.EnvFactory;
import it.red.algen.garden.data.GardenDatabase;
import it.red.algen.garden.data.GardenDatabaseCSV;
import it.red.algen.garden.domain.GardenRawFitness;
import it.red.algen.garden.domain.GardenTarget;
import it.red.algen.garden.domain.Place;
import it.red.algen.garden.domain.Tree;

/**
 *
 * @author grossi
 */
@Component
public class GardenEnvFactory implements EnvFactory {
	private GardenDatabase database = new GardenDatabaseCSV(GardenApplication.DATABASE_DIR);
	
	@Autowired
	private GardenPopulationFactory gardenPopulationFactory;
	
	
    public Env create(){
    	
        // Crea la popolazione iniziale
    	Place[] places = database.getAllPlaces();
    	Tree[] trees = database.getAllTrees();
    	gardenPopulationFactory.init(places, trees);
        Population startGen = gardenPopulationFactory.createNew();
        
        // Definisce l'ambiente di riproduzione
        // TODOM: target from outside!
        GardenTarget mytarget = new GardenTarget();
        mytarget.setRawFitness(new GardenRawFitness(places.length * 1)); // 1=distanza massima dall'ottimo
        Env env = new Env(mytarget, startGen);
        return env;
    }

	
	public GardenDatabase getDatabase() {
		return database;
	}

	public void setDatabase(GardenDatabase database) {
		this.database = database;
	}

}

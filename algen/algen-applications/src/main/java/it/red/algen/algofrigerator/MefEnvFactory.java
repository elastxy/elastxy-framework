/*
 * ExprExperimentFactory.java
 *
 * Created on 5 agosto 2007, 15.19
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.algofrigerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.conf.ConfigurationException;
import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.AbstractEnvFactory;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.metadata.StandardMetadataGenoma;

/**
 * TODOA: bootstrappare
 * @author grossi
 */
@Component
public class MefEnvFactory extends AbstractEnvFactory<PerformanceTarget, BigDecimal, StandardMetadataGenoma> {
	private static Logger logger = Logger.getLogger(MefEnvFactory.class);
	
	@Autowired private ContextSupplier contextSupplier;
	
	@Autowired private MefGenomaProvider genomaProvider;
	

	@Override
	protected GenomaProvider getGenomaProvider() {
		return genomaProvider;
	}

	// TODOM: take outside Target definition code, as a new Component
	@Override
	protected Target<PerformanceTarget, BigDecimal> defineTarget(Genoma genoma) {
		AlgorithmContext context = contextSupplier.getContext();
        
		// User parameters
		Integer desiredMeals = context.applicationSpecifics.getTargetInteger(MefApplication.TARGET_DESIRED_MEALS, MefApplication.DEFAULT_DESIRED_MEALS);
		Integer savouryProportion = context.applicationSpecifics.getTargetInteger(MefApplication.TARGET_SAVOURY_PROPORTION, MefApplication.DEFAULT_SAVOURY_PROPORTION);
		Integer sweetProportion = context.applicationSpecifics.getTargetInteger(MefApplication.TARGET_SWEET_PROPORTION, MefApplication.DEFAULT_SWEET_PROPORTION);
		String 	userFridgeFoods = context.applicationSpecifics.getParamString(MefApplication.PARAM_REFRIGERATOR_FOODS);
		String 	userPantryFoods = context.applicationSpecifics.getParamString(MefApplication.PARAM_PANTRY_FOODS);
		
		// Defines goal representation
    	PerformanceTarget target = new PerformanceTarget();
    	target.setTargetFitness(contextSupplier.getContext().stopConditions.targetFitness);
    	target.setTargetThreshold(contextSupplier.getContext().stopConditions.targetThreshold); // TODOA: commons to all envfactory
    	target.setWeights(savouryProportion.doubleValue() / 100.0, sweetProportion.doubleValue() / 100.0);
    	target.setGoal(createGoal(desiredMeals, target.getWeights(), userFridgeFoods, userPantryFoods)); // TODOA: foods will be input parameters!
    	
    	
//        target.setReferenceMeasure(totalRecipes); // TODOA: multiobiettivo: distinte per savoury e sweet
		return target;
	}


	@Override
	protected SolutionsFactory<StandardMetadataGenoma> getSolutionsFactory() {
		return contextSupplier.getContext().application.solutionsFactory;
	}
	

    /**
     * A list of foods from refrigerator (TODOM: by code, not by name)
     * 
     * @return
     */
    private MefGoal createGoal(Integer desiredMeals, Double[] weights, String userProvidedFoods, String userProvidedPantry){
    	MefGoal result = new MefGoal();
    	
    	if(userProvidedFoods==null||userProvidedFoods.length()==0){
    		readFoodsFromFile(result);
    	}
    	else {
    		readFoodsFromString(result, userProvidedFoods);
    	}

    	if(userProvidedPantry==null||userProvidedPantry.length()==0){
    		readPantryFromFile(result);
    	}
    	else {
    		readPantryFromString(result, userProvidedPantry);
    	}
		
		result.desiredMeals = desiredMeals;
		
		result.savouryMeals = (int)Math.round(desiredMeals * weights[0]);
		result.sweetMeals 	= desiredMeals - result.savouryMeals;
		
		return result;
    }

    
    // TODOA: remove duplicates
	private void readFoodsFromFile(MefGoal result) {
		String classpathResource = "/"+MefApplication.APP_NAME+"/target.json";
		try {
			result.refrigeratorFoods = new ArrayList<String>();
			String[] foods = (String[])ReadConfigSupport.readJSON(classpathResource, String[].class);
			result.refrigeratorFoods.addAll(Arrays.asList(foods));
		} catch (IOException e) {
			String msg = "Error while reading JSON from classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
	}
	

	private void readPantryFromFile(MefGoal result) {
		// TODOA: Load all default pantry foods from file
		String classpathResource = "/"+MefApplication.APP_NAME+"/default_pantry.json";
		try {
			result.pantry = Arrays.asList((String[])ReadConfigSupport.readJSON(classpathResource, String[].class));
		} catch (IOException e) {
			String msg = "Error while reading JSON from classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
	}


	private void readFoodsFromString(MefGoal result, String userProvidedFoods) {
		try {
			result.refrigeratorFoods = new ArrayList<String>();
			String[] foods = (String[])ReadConfigSupport.readJSONString(userProvidedFoods, String[].class);
			result.refrigeratorFoods.addAll(Arrays.asList(foods));
		} catch (IOException e) {
			String msg = "Error while reading JSON from input String ["+userProvidedFoods+"]. Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
	}

	private void readPantryFromString(MefGoal result, String userProvidedPantry) {
		try {
			result.pantry = new ArrayList<String>();
			String[] foods = (String[])ReadConfigSupport.readJSONString(userProvidedPantry, String[].class);
			result.pantry.addAll(Arrays.asList(foods));
		} catch (IOException e) {
			String msg = "Error while reading JSON from input String ["+userProvidedPantry+"]. Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
	}

}

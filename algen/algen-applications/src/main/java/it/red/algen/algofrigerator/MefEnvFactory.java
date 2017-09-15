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
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.conf.ConfigurationException;
import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.AbstractEnvFactory;
import it.red.algen.dataaccess.GenomaProvider;
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
	

	// TODOM: take outside Target definition code, as a new Component
	@Override
	protected Target<PerformanceTarget, BigDecimal> defineTarget(Genoma genoma) {
		AlgorithmContext context = contextSupplier.getContext();
        
		// User parameters
		Integer desiredMeals = context.applicationSpecifics.getTargetInteger(MefApplication.TARGET_DESIRED_MEALS, MefApplication.DEFAULT_DESIRED_MEALS);
		Integer savouryProportion = context.applicationSpecifics.getTargetInteger(MefApplication.TARGET_SAVOURY_PROPORTION, MefApplication.DEFAULT_SAVOURY_PROPORTION);
		Integer sweetProportion = context.applicationSpecifics.getTargetInteger(MefApplication.TARGET_SWEET_PROPORTION, MefApplication.DEFAULT_SWEET_PROPORTION);
		Boolean fridgeMandatory = context.applicationSpecifics.getTargetBoolean(MefApplication.TARGET_FRIDGE_MANDATORY, MefApplication.DEFAULT_FRIDGE_MANDATORY);
		List<String> userFridgeFoods = context.applicationSpecifics.getParamList(MefApplication.PARAM_REFRIGERATOR_FOODS);
		List<String> userPantryFoods = context.applicationSpecifics.getParamList(MefApplication.PARAM_PANTRY_FOODS);
		
		// Defines goal representation
    	PerformanceTarget target = new PerformanceTarget();
    	target.setTargetFitness(contextSupplier.getContext().stopConditions.targetFitness);
    	target.setTargetThreshold(contextSupplier.getContext().stopConditions.targetThreshold); // TODOA: commons to all envfactory
    	target.setWeights(savouryProportion.doubleValue() / 100.0, sweetProportion.doubleValue() / 100.0);
    	target.setGoal(createGoal(desiredMeals, target.getWeights(), fridgeMandatory, userFridgeFoods, userPantryFoods)); // TODOA: foods will be input parameters!
    	
    	
//        target.setReferenceMeasure(totalRecipes); // TODOA: multiobiettivo: distinte per savoury e sweet
		return target;
	}


    /**
     * A list of foods from refrigerator (TODOM: by code, not by name)
     * 
     * @return
     */
    private MefGoal createGoal(Integer desiredMeals, Double[] weights, boolean fridgeMandatory, List<String> userProvidedFoods, List<String> userProvidedPantry){
    	MefGoal result = new MefGoal();
    	
    	if(userProvidedFoods==null||userProvidedFoods.size()==0){
    		readFoodsFromFile(result);
    	}
    	else {
    		result.refrigeratorFoods = userProvidedFoods;
//    		readFoodsFromString(result, userProvidedFoods);
    	}

    	if(userProvidedPantry==null||userProvidedPantry.size()==0){
    		readPantryFromFile(result);
    	}
    	else {
    		result.pantry = userProvidedPantry;
//    		readPantryFromString(result, userProvidedPantry);
    	}
		
		result.desiredMeals = desiredMeals;
		
		result.savouryMeals = (int)Math.round(desiredMeals * weights[0]);
		result.sweetMeals 	= desiredMeals - result.savouryMeals;
		
		result.fridgeMandatory = fridgeMandatory;
		return result;
    }

    
    // TODOA: remove duplicates
	private void readFoodsFromFile(MefGoal result) {
		String db = this.contextSupplier.getContext().applicationSpecifics.getParamString(MefApplication.PARAM_DATABASE, MefApplication.DEFAULT_DATABASE);
		String classpathResource = "/"+MefApplication.APP_NAME+"/"+db+"/target.json";
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
		String db = this.contextSupplier.getContext().applicationSpecifics.getParamString(MefApplication.PARAM_DATABASE, MefApplication.DEFAULT_DATABASE);
		String classpathResource = "/"+MefApplication.APP_NAME+"/"+db+"/pantry.json";
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

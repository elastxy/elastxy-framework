package org.elastxy.app.algofrigerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.conf.ReadConfigSupport;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.MultiplePerformanceTarget;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.experiment.TargetType;
import org.elastxy.core.engine.factory.TargetBuilder;

public class MefTargetBuilder implements TargetBuilder<MultiplePerformanceTarget, BigDecimal>{
	private static Logger logger = Logger.getLogger(MefTargetBuilder.class);

	private AlgorithmContext context;

	@Override
	public void setup(AlgorithmContext context) {
		this.context = context;
	}

	
	
	@Override
	public Target<MultiplePerformanceTarget, BigDecimal> define(WorkingDataset dataset) {

		// User parameters
		Integer desiredMeals = context.applicationSpecifics.getTargetInteger(MefConstants.TARGET_DESIRED_MEALS, MefConstants.DEFAULT_DESIRED_MEALS);
		Integer savouryProportion = context.applicationSpecifics.getTargetInteger(MefConstants.TARGET_SAVOURY_PROPORTION, MefConstants.DEFAULT_SAVOURY_PROPORTION);
		Integer sweetProportion = context.applicationSpecifics.getTargetInteger(MefConstants.TARGET_SWEET_PROPORTION, MefConstants.DEFAULT_SWEET_PROPORTION);
		Boolean fridgeMandatory = context.applicationSpecifics.getTargetBoolean(MefConstants.TARGET_FRIDGE_MANDATORY, MefConstants.DEFAULT_FRIDGE_MANDATORY);
		List<String> userFridgeFoods = context.applicationSpecifics.getParamList(MefConstants.PARAM_REFRIGERATOR_FOODS);
		List<String> userPantryFoods = context.applicationSpecifics.getParamList(MefConstants.PARAM_PANTRY_FOODS);
		
		// Defines goal representation
		MultiplePerformanceTarget target = new MultiplePerformanceTarget();
		target.setTargetType(TargetType.AGGREGATE);
    	target.setWeights(savouryProportion.doubleValue() / 100.0, sweetProportion.doubleValue() / 100.0);
    	target.setGoal(createGoal(desiredMeals, target.getWeights(), fridgeMandatory, userFridgeFoods, userPantryFoods));
    	
		return target;
	}


    /**
     * A list of foods from refrigerator (TODOM-4: mef: by code, not by name)
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
    	}

    	if(userProvidedPantry==null||userProvidedPantry.size()==0){
    		readPantryFromFile(result);
    	}
    	else {
    		result.pantry = userProvidedPantry;
    	}
		
		result.desiredMeals = desiredMeals;
		
		result.savouryMeals = (int)Math.round(desiredMeals * weights[0]);
		result.sweetMeals 	= desiredMeals - result.savouryMeals;
		
		result.fridgeMandatory = fridgeMandatory;
		return result;
    }

    
	private void readFoodsFromFile(MefGoal result) {
		String db = this.context.applicationSpecifics.getParamString(MefConstants.PARAM_DATABASE, MefConstants.DEFAULT_DATABASE);
		String classpathResource = "/"+this.context.application.appFolder+"/"+db+"/target.json";
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
		String db = context.applicationSpecifics.getParamString(MefConstants.PARAM_DATABASE, MefConstants.DEFAULT_DATABASE);
		String classpathResource = "/"+this.context.application.appFolder+"/"+db+"/pantry.json";
		try {
			result.pantry = Arrays.asList((String[])ReadConfigSupport.readJSON(classpathResource, String[].class));
		} catch (IOException e) {
			String msg = "Error while reading JSON from classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
	}


//	private void readFoodsFromString(MefGoal result, String userProvidedFoods) {
//		try {
//			result.refrigeratorFoods = new ArrayList<String>();
//			String[] foods = (String[])ReadConfigSupport.readJSONString(userProvidedFoods, String[].class);
//			result.refrigeratorFoods.addAll(Arrays.asList(foods));
//		} catch (IOException e) {
//			String msg = "Error while reading JSON from input String ["+userProvidedFoods+"]. Ex: "+e;
//			logger.error(msg, e);
//			throw new ConfigurationException(msg, e);
//		}
//	}
//
//	private void readPantryFromString(MefGoal result, String userProvidedPantry) {
//		try {
//			result.pantry = new ArrayList<String>();
//			String[] foods = (String[])ReadConfigSupport.readJSONString(userProvidedPantry, String[].class);
//			result.pantry.addAll(Arrays.asList(foods));
//		} catch (IOException e) {
//			String msg = "Error while reading JSON from input String ["+userProvidedPantry+"]. Ex: "+e;
//			logger.error(msg, e);
//			throw new ConfigurationException(msg, e);
//		}
//	}


}

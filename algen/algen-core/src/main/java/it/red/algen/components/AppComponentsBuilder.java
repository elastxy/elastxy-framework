package it.red.algen.components;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import it.red.algen.conf.ConfigurationException;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.PopulationFactory;
import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.engine.fitness.FitnessCalculator;
import it.red.algen.engine.fitness.Incubator;
import it.red.algen.engine.operators.Mutator;
import it.red.algen.engine.operators.Recombinator;
import it.red.algen.engine.operators.Selector;
import it.red.algen.metadata.AlleleGenerator;
import it.red.algen.tracking.SolutionRenderer;

/**
 * Builds up all the ApplicationComponents, based on 
 * AlgenApplication metadata definition.
 * 
 * TODOM: evaluate Command for this duties
 * 
 * @author red
 *
 */
@Component
public class AppComponentsBuilder {
	private static Logger logger = Logger.getLogger(AppComponentsBuilder.class);
	
	/**
	 * Creates the ApplicationComponents from their definition
	 */
	public AppComponents construct(AlgenApplication applicationMetadata){
		logger.info("Constructing components for application: "+applicationMetadata.name);
		
		AppComponents result = new AppComponents();

		
		result.name = 				applicationMetadata.name;

		result.envFactory = 		(EnvFactory)constructComponent(applicationMetadata.envFactory);
		
		// TODOM: not by reference: indirection with name
		result.genomaProvider = 	(GenomaProvider)constructComponent(applicationMetadata.genomaProvider);
		result.alleleGenerator = 	(AlleleGenerator)constructComponent(applicationMetadata.alleleGenerator);

		result.populationFactory = 	(PopulationFactory)constructComponent(applicationMetadata.populationFactory);
		result.solutionsFactory = 	(SolutionsFactory)constructComponent(applicationMetadata.solutionsFactory);
		
		result.fitnessCalculator = 	(FitnessCalculator)constructComponent(applicationMetadata.fitnessCalculator);
		result.incubator = 			(Incubator)constructComponent(applicationMetadata.incubator);
		
		result.selector = 			(Selector)constructComponent(applicationMetadata.selector);
		result.mutator = 			(Mutator)constructComponent(applicationMetadata.mutator);
		result.recombinator = 		(Recombinator)constructComponent(applicationMetadata.recombinator);
		
		result.solutionRenderer = 	(SolutionRenderer)constructComponent(applicationMetadata.solutionRenderer);
		
		return result;
	}
	
	private Object constructComponent(ComponentMetadata metadata){
		Object result = null;
		if(metadata.type==null || ComponentMetadata.TYPE_JAVA.equals(metadata.type)){
			Class<?> clazz;
			try {
				clazz = Class.forName(metadata.content);
				result = clazz.newInstance();
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
				String msg = "Error while constructing component of metadata "+metadata+". Ex: "+e;
				logger.error(msg, e);
				throw new ConfigurationException(msg, e);
			}
		}
		else {
			String msg = "Type not recognized '"+metadata.type+"' while constructing component of metadata "+metadata;
			logger.error(msg);
			throw new ConfigurationException(msg);
		}
		return result;
	}
	
	
	/**
	 * Wires ApplicationComponents up so that they could collaborate
	 */
	public AppComponents wire(AppComponents appComponents){
		appComponents.populationFactory.setSolutionsFactory(appComponents.solutionsFactory);
		appComponents.fitnessCalculator.setup(appComponents.incubator);
		return appComponents;
	}
	
	
	/**
	 * Initialize the ApplicationComponents for InitializationAware components
	 * TODOM: to be used?
	 */
	public AppComponents init(AppComponents appComponents){
		return appComponents;
	}
	
}

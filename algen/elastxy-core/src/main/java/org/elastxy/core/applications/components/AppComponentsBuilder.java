package org.elastxy.core.applications.components;

import org.apache.log4j.Logger;
import org.elastxy.core.conf.ConfigurationException;
import org.elastxy.core.dataprovider.DatasetProvider;
import org.elastxy.core.dataprovider.GenomaProvider;
import org.elastxy.core.engine.factory.EnvFactory;
import org.elastxy.core.engine.factory.PopulationFactory;
import org.elastxy.core.engine.factory.SolutionsFactory;
import org.elastxy.core.engine.factory.TargetBuilder;
import org.elastxy.core.engine.fitness.FitnessCalculator;
import org.elastxy.core.engine.fitness.Incubator;
import org.elastxy.core.engine.metadata.AlleleGenerator;
import org.elastxy.core.engine.operators.Mutator;
import org.elastxy.core.engine.operators.Recombinator;
import org.elastxy.core.engine.operators.Selector;
import org.elastxy.core.tracking.ResultsRenderer;
import org.elastxy.core.tracking.SolutionRenderer;
import org.springframework.stereotype.Component;

/**
 * Builds up all the ApplicationComponents, based on 
 * ApplicationMetadata metadata definition.
 * 
 * TODO3-2: evaluate Command for duties like builders
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
	public AppComponents construct(ApplicationMetadata applicationMetadata){
		if(logger.isDebugEnabled()) logger.debug("Constructing components for application: "+applicationMetadata.appName);
		
		AppComponents result = new AppComponents();

		result.appName = 				applicationMetadata.appName;
		result.appFolder = 				applicationMetadata.appFolder!=null ? "appdata/"+applicationMetadata.appFolder : "appdata/"+applicationMetadata.appName;

		// TODO2-2: provide default when applicable, to reduce verbosity of application.json config
		result.targetBuilder = 		(TargetBuilder)constructComponent(applicationMetadata.targetBuilder);
		result.envFactory = 		(EnvFactory)constructComponent(applicationMetadata.envFactory);
		
		// TODO3-2: link not by reference: indirection with name?
		result.datasetProvider = (DatasetProvider)constructComponent(applicationMetadata.datasetProvider);
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
		result.friendlySolutionRenderer = (SolutionRenderer)constructComponent(applicationMetadata.solutionRenderer);
		result.resultsRenderer = 	(ResultsRenderer)constructComponent(applicationMetadata.resultsRenderer);

		// Distributed application
		// TODO2-4: evaluate one only property (e.g. envFactory) but assigned based on runtime context: LOCAL|DISTRIBUTED
		result.multiColonyEnvFactory =(EnvFactory)constructComponent(applicationMetadata.multiColonyEnvFactory);
		result.distributedDatasetProvider =(DatasetProvider)constructComponent(applicationMetadata.distributedDatasetProvider);
		result.singleColonyDatasetProvider = (DatasetProvider)constructComponent(applicationMetadata.singleColonyDatasetProvider);
		result.distributedGenomaProvider =(GenomaProvider)constructComponent(applicationMetadata.distributedGenomaProvider);

		return result;
	}
	
	private Object constructComponent(ComponentMetadata metadata){
		Object result = null;
		if(metadata==null){
			result = null;
		}
		else if(metadata.type==null || ComponentMetadata.TYPE_JAVA.equals(metadata.type)){
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
		appComponents.envFactory.setTargetBuilder(appComponents.targetBuilder);
		if(appComponents.multiColonyEnvFactory!=null) appComponents.multiColonyEnvFactory.setTargetBuilder(appComponents.targetBuilder);
		appComponents.populationFactory.setSolutionsFactory(appComponents.solutionsFactory);
		appComponents.fitnessCalculator.setIncubator(appComponents.incubator);
		appComponents.resultsRenderer.setTechieSolutionRenderer(appComponents.solutionRenderer);
		appComponents.resultsRenderer.setFriendlySolutionRenderer(appComponents.friendlySolutionRenderer);
		return appComponents;
	}
	
	
	/**
	 * TODO3-2: Initialize the ApplicationComponents for InitializationAware components: to be used?
	 */
	public AppComponents init(AppComponents appComponents){
		return appComponents;
	}
	
}

/*
 * Experiment.java
 *
 * Created on 5 agosto 2007, 14.31
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.distributed.engine.core;

import java.util.List;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.engine.core.Experiment;
import org.elastxy.core.engine.core.SingleColonyEvolver;
import org.elastxy.core.engine.factory.EnvFactory;
import org.elastxy.core.engine.operators.RecombinatorLogics;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.EnvObserver;
import org.elastxy.distributed.engine.factory.SingleColonyClosureEnvFactory;




/**
 *	Local (single colony) experiment within a distributed algorithm.
 *
 *  In distributed MultiColony context, the SingleColonyExperiment
 *  runs a big number of generations from an initial population, into an Era 
 *  within a closure.
 *  
 *  Such an Experiment can be optionally based on Genoma provided 
 *  by a distributed broadcast set for mutation and a rdd iterator 
 *  containing alleles of initial population, beyond best matches
 *  of current era.
 *
 *	If new population or mutation alleles are not provided,
 *  the Experiment is a normal local one, except for best matches
 *  which are reintroduced from a previous era into actual population.
 *
 * @author grossi
 */
public class SingleColonyClosureExperiment implements Experiment {
	private AlgorithmContext context;
	private Target target;
	private List<Allele> newPopulationAlleles;
	private List<Allele> mutationAlleles;
	private List<Solution> previousBestMatches;
	
	private ExperimentStats stats;
	
	
	public SingleColonyClosureExperiment(
			AlgorithmContext context, 
			Target target, 
			List<Allele> newPopulationAlleles,
			List<Allele> mutationAlleles,
			List<Solution> previousBestMatches){
		this.context = context;
		this.target = target;
		this.newPopulationAlleles = newPopulationAlleles;
		this.mutationAlleles = mutationAlleles;
		this.previousBestMatches = previousBestMatches;
	}
	

    public ExperimentStats getStats(){
        return stats;
    }
    
    public void run(){
    	
        EnvObserver observer = new EnvObserver(context); // TODOM-8: events like Kafka?
        
        boolean processingOnlyExperiment = newPopulationAlleles==null||newPopulationAlleles.isEmpty();
        
        // Creates initial environment
        EnvFactory envFactory = processingOnlyExperiment ?
        		context.application.envFactory :
        		new SingleColonyClosureEnvFactory(
        		target, 
        		newPopulationAlleles, 
        		mutationAlleles, 
        		previousBestMatches);
        envFactory.setup(context);
        Env environment = envFactory.create();
        
        // Randomly add previous best matches to initial population
        if(processingOnlyExperiment) insertPreviousBestMatches(environment);

        // Setups engine
        SingleColonyEvolver evolver = new SingleColonyEvolver(
        		context, 
        		environment);
        evolver.subscribe(observer);
        
        // Starts evolution
        evolver.evolve();
        
        // Retrieves stats
        stats = evolver.getStats();
    }


    /**
     * Replaces first N solutions with a copy of every previous best matches.
     * 
     * @param environment
     */
	private void insertPreviousBestMatches(Env environment) {
		if(previousBestMatches!=null && previousBestMatches.size() > 1){
			if(context.algorithmParameters.elitism.recombineElite){
				List<Solution> reinsertedBestMatches = RecombinatorLogics.recombineList(
					context.application.recombinator, 
					previousBestMatches, 
					environment.genoma.getLimitedAllelesStrategy());
				for(int s=0; s < reinsertedBestMatches.size(); s++) 
					environment.lastGen.solutions.set(s, reinsertedBestMatches.get(s).copy());
			}
			else {
				for(int s=0; s < previousBestMatches.size(); s++) 
					environment.lastGen.solutions.set(s, previousBestMatches.get(s).copy());
			}
		}
	}
	
    
    public String toString(){
    	return String.format("Experiment stats: %s", stats);
    }
}

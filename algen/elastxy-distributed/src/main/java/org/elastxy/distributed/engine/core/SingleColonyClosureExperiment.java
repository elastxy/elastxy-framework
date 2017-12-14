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
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.EnvObserver;
import org.elastxy.distributed.engine.factory.SingleColonyClosureEnvFactory;




/**
 *	Local (single colony) experiment within a distributed algorithm.
 *
 *  In distributed MultiColony context, the SingleColonyExperiment
 *  runs a big number of generations from an initial population, into an Era 
 *  within a closure and based on Genoma provided by a distributed broadcast 
 *  set for mutation and a rdd iterator for initial population.
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
        
        // Creates initial environment
        EnvFactory envFactory = new SingleColonyClosureEnvFactory(
        		target, 
        		newPopulationAlleles, 
        		mutationAlleles, 
        		previousBestMatches);
        envFactory.setup(context);
        Env environment = envFactory.create();
    	
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
    
    public String toString(){
    	return String.format("Experiment stats: %s", stats);
    }
}

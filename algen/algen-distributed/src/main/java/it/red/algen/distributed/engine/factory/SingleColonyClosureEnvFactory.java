package it.red.algen.distributed.engine.factory;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.AlleleValuesProvider;
import it.red.algen.dataprovider.GenomaProvider;
import it.red.algen.dataprovider.InMemoryAlleleValuesProvider;
import it.red.algen.distributed.dataprovider.BroadcastWorkingDataset;
import it.red.algen.distributed.dataprovider.DistributedAlleleValuesProvider;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.engine.factory.EnvFactory;
import it.red.algen.engine.factory.TargetBuilder;
import it.red.algen.engine.metadata.MetadataGenomaBuilder;
import it.red.algen.engine.operators.RecombinatorLogics;


/**
 * Starts from data from Driver coordinator, and creates an ad-hoc
 * local context with:
 * - references to broadcast working sets, if needed
 * - GenomaProvider not distributed, but AllelesValuesProvider loaded
 *   with broadcast variable from Driver
 * - initial Population created from Alleles List from Driver
 * 
 * @author red
 *
 */
public class SingleColonyClosureEnvFactory implements EnvFactory {
	private static Logger logger = Logger.getLogger(SingleColonyClosureEnvFactory.class);
	
	private AlgorithmContext context;
	private Target target;
	private List<Allele> mutationAlleles;
	private List<Allele> newPopulationAlleles;
	private List<Solution> previousBestMatches;


	public SingleColonyClosureEnvFactory(
			Target target, 
			List<Allele> newPopulationAlleles,
			List<Allele> mutationAllele,
			List<Solution> previousBestMatches){
		this.target = target;
		this.newPopulationAlleles = newPopulationAlleles;
		this.mutationAlleles = mutationAllele;
		this.previousBestMatches = previousBestMatches;
	}
	

	@Override
	public void setup(AlgorithmContext context) {
		this.context = context;
	}

	@Override
	public void setTargetBuilder(TargetBuilder targetBuilder) {
		throw new UnsupportedOperationException("A SingleColonClosure already has the common Multicolony target.");
	}
	
	
	
    public Env create(){

    	// Updates Genoma with local Alleles from partition for population creation
        AlleleValuesProvider allelesProviderForPopulation = new InMemoryAlleleValuesProvider();
        // TODOA-8: multi alleles provider: get name of alleles provider from metadata?
        allelesProviderForPopulation.insertAlleles(DistributedAlleleValuesProvider.NAME, newPopulationAlleles);
        
        GenomaProvider genomaProvider = context.application.genomaProvider;
        genomaProvider.collect();
        Genoma genoma = genomaProvider.shrink(target); // TODOA-2: target from context?? now either from context and passed serialized
        MetadataGenomaBuilder.setupAlleleValuesProvider(genoma, allelesProviderForPopulation);

    	// Create initial population
    	Population startGen = createInitialPopulation(genoma, previousBestMatches);

    	// Updates Genoma with broadcasted mutation Alleles for mutation
        AlleleValuesProvider allelesProviderForMutation = new InMemoryAlleleValuesProvider();
        // TODOA-8: multi alleles provider: how to manage multi alleles provider?
        allelesProviderForMutation.insertAlleles(DistributedAlleleValuesProvider.NAME, mutationAlleles);
    	genoma.setAlleleValuesProvider(allelesProviderForMutation);
        
        // Create environment
        Env env = new Env(target, startGen, genoma, null); // TODOA-2: reintroduce working set? (e.g. MeF)
        
        return env;
    }
    

	private Population createInitialPopulation(Genoma genoma, List<Solution> previousBestMatches) {
		long solutions = 		context.algorithmParameters.initialSelectionNumber;
		boolean random = 		context.algorithmParameters.initialSelectionRandom;
		
		// Recombines previous best to preserve their genetic material,
		// while avoiding that every population will reproduce undefinitely the same bests!
		if(logger.isTraceEnabled()) logger.trace("Best matches before recombination: "+previousBestMatches);
		if(previousBestMatches!=null && previousBestMatches.size()>1){
			previousBestMatches = RecombinatorLogics.recombineList(context.application.recombinator, previousBestMatches, genoma.getLimitedAllelesStrategy());
		}
		if(logger.isTraceEnabled()) logger.trace("Best matches after recombination: "+previousBestMatches);
		
        Population startGen = 	context.application.populationFactory.createNew(genoma, solutions, random, previousBestMatches);
		return startGen;
	}
	

}

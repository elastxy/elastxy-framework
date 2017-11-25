package it.red.algen.distributed.engine.factory;

import java.util.List;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.AlleleValuesProvider;
import it.red.algen.dataprovider.GenomaProvider;
import it.red.algen.dataprovider.InMemoryAlleleValuesProvider;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.Population;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.engine.factory.EnvFactory;
import it.red.algen.engine.metadata.MetadataGenomaBuilder;


/**
 * Starts from data from Driver coordinator, and creates an ad-hoc
 * local context with:
 * - no references to working set (TODOD: Mef needs dataset to grow recipes!)
 * - GenomaProvider not distributed, but AllelesValuesProvider loaded
 *   with broadcast variable from Driver
 * - initial Population created from Alleles List from Driver
 * 
 * @author red
 *
 */
public class SingleColonyClosureEnvFactory implements EnvFactory {
	private AlgorithmContext context;
	private Target target;
	private List<Allele> mutationAlleles;
	private List<Allele> newPopulationAlleles;
	
	
	public SingleColonyClosureEnvFactory(
			Target target, 
			List<Allele> newPopulationAlleles,
			List<Allele> mutationAllele){
		this.target = target;
		this.newPopulationAlleles = newPopulationAlleles;
		this.mutationAlleles = mutationAllele;
	}
	

	@Override
	public void setup(AlgorithmContext context) {
		this.context = context;
	}
	
    public Env create(){
    	
    	// Updates Genoma with local Alleles from partition for population creation
        AlleleValuesProvider allelesProviderForPopulation = new InMemoryAlleleValuesProvider();
        // TODOD: name of alleles provider from metadata?
        allelesProviderForPopulation.insertAlleles("rddProvider", newPopulationAlleles);
        
        GenomaProvider genomaProvider = context.application.genomaProvider;
        genomaProvider.collect();
        Genoma genoma = genomaProvider.getGenoma();
        MetadataGenomaBuilder.setupAlleleValuesProvider(genoma, allelesProviderForPopulation);

    	// Create initial population
    	Population startGen = createInitialPopulation(genoma);

    	// Updates Genoma with broadcasted mutation Alleles for mutation
        AlleleValuesProvider allelesProviderForMutation = new InMemoryAlleleValuesProvider();
        // TODOA: where to put constants? how to manage multi alleles provider?
        allelesProviderForMutation.insertAlleles("rddProvider", mutationAlleles);
    	genoma.setAlleleValuesProvider(allelesProviderForMutation);
        
        // Create environment
        Env env = new Env(target, startGen, genoma, null); // TODOD: reintroduce working set (e.g. MeF)
        
        return env;
    }
    

	private Population createInitialPopulation(Genoma genoma) {
		long solutions = 		context.algorithmParameters.initialSelectionNumber;
		boolean random = 		context.algorithmParameters.initialSelectionRandom;
        Population startGen = 	context.application.populationFactory.createNew(genoma, solutions, random);
		return startGen;
	}


}

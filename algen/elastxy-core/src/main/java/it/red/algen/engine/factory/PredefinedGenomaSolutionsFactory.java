package it.red.algen.engine.factory;

import java.util.List;
import java.util.stream.IntStream;

import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.engine.genetics.PredefinedGenoma;
import it.red.algen.engine.genetics.PredefinedGenotypeFactory;

public class PredefinedGenomaSolutionsFactory  implements SolutionsFactory<PredefinedGenoma>{

	@Override
    public Solution createRandom(PredefinedGenoma genoma) {
    	return createSolution(genoma, genoma.getRandomAlleles());
    }
    
	@Override
    public Solution createBaseModel(PredefinedGenoma genoma) {
    	return createSolution(genoma, genoma.getOrderedAlleles());
    }

	private Solution createSolution(PredefinedGenoma genoma, List<Allele> alleles) {
		GenericSolution solution = new GenericSolution();
    	solution.genotype = PredefinedGenotypeFactory.createGenotype(genoma);

    	// Assign to every Gene an Allele
    	IntStream.range(0, ((Chromosome)solution.genotype).genes.size()).
    		forEach(i -> ((Chromosome)solution.genotype).genes.get(i).allele = alleles.get(i));
    	
    	return solution;
	}

//	@Override
//	public Solution createPredefined(PredefinedGenoma genoma, List<Object> alleleValues) {
//		GenericSolution solution = new GenericSolution();
//    	solution.genotype = PredefinedGenotypeFactory.createGenotype(genoma);
//
//    	IntStream.range(0, ((Chromosome)solution.genotype).genes.size()).
//    		forEach(i -> ((Chromosome)solution.genotype).genes.get(i).allele = new Allele(alleleValues.get(i)));
//    	
//    	return solution;
//	}

	
}

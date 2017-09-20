package it.red.algen.dataaccess;

import java.util.List;
import java.util.stream.IntStream;

import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.PredefinedGeneFactory;
import it.red.algen.domain.genetics.PredefinedGenoma;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.domain.genetics.genotype.Allele;

public class PredefinedGenomaSolutionsFactory  implements SolutionsFactory<PredefinedGenoma>{

	@Override
    public Solution createRandom(PredefinedGenoma genoma) {
    	return createSolution(genoma, genoma.getRandomAlleles());
    }
    
	@Override
    public Solution createBaseModel(PredefinedGenoma genoma) {
    	return createSolution(genoma, genoma.getAllAlleles());
    }

	private Solution createSolution(PredefinedGenoma genoma, List<Allele> alleles) {
		GenericSolution solution = new GenericSolution();
    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = PredefinedGeneFactory.createSequence(genoma);
    	solution.genotype = genotype;

    	IntStream.range(0, genotype.genes.size()).forEach(i -> genotype.genes.get(i).allele = alleles.get(i));
    	
    	return solution;
	}

	@Override
	public Solution createPredefined(PredefinedGenoma genoma, List<Object> alleleValues) {
		GenericSolution solution = new GenericSolution();
    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = PredefinedGeneFactory.createSequence(genoma);
    	solution.genotype = genotype;

    	IntStream.range(0, genotype.genes.size()).forEach(i -> genotype.genes.get(i).allele = new Allele(alleleValues.get(i)));
    	
    	return solution;
	}

	
}

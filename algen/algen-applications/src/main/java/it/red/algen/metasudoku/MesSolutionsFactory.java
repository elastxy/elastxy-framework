package it.red.algen.metasudoku;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.PredefinedGeneFactory;
import it.red.algen.domain.genetics.PredefinedGenoma;
import it.red.algen.domain.genetics.SequenceGenotype;

@Component
public class MesSolutionsFactory implements SolutionsFactory<PredefinedGenoma> {


    public Solution createRandom(PredefinedGenoma genoma) {
    	GenericSolution solution = new GenericSolution();
    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = PredefinedGeneFactory.createSequence(genoma);
    	solution.genotype = genotype;

    	List<Allele> alleles = genoma.getRandomAlleles();
    	IntStream.range(0, genotype.genes.size()).forEach(i -> genotype.genes.get(i).allele = alleles.get(i));
    	
    	return solution;
    }
    
    @Override
    public Solution createBaseModel(PredefinedGenoma genoma) {
    	GenericSolution solution = new GenericSolution();
    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = PredefinedGeneFactory.createSequence(genoma);
    	solution.genotype = genotype;

    	List<Allele> alleles = genoma.getAllAlleles();
    	IntStream.range(0, genotype.genes.size()).forEach(i -> genotype.genes.get(i).allele = alleles.get(i));
    	
    	return solution;
    }

	@Override
	public Solution createPredefined(PredefinedGenoma genoma, List<Object> alleleValues) {
		return createBaseModel(genoma);
	}


}

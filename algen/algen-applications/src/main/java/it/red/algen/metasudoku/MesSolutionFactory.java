package it.red.algen.metasudoku;

import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.PredefinedGeneFactory;
import it.red.algen.domain.genetics.PredefinedGenoma;
import it.red.algen.domain.genetics.SequenceGenotype;

@Component
public class MesSolutionFactory implements SolutionsFactory {

	@Autowired private MesGenomaProvider genomaProvider;

    public Solution createRandom() {
    	GenericSolution solution = new GenericSolution();
    	
    	PredefinedGenoma genoma = (PredefinedGenoma)genomaProvider.getGenoma();

    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = PredefinedGeneFactory.createSequence(genoma);
    	solution.genotype = genotype;

    	List<Allele> alleles = genoma.getRandomAlleles();
    	IntStream.range(0, genotype.genes.size()).forEach(i -> genotype.genes.get(i).allele = alleles.get(i));
    	
    	return solution;
    }
    
    @Override
    public Solution createBaseModel() {
    	GenericSolution solution = new GenericSolution();
    	
    	PredefinedGenoma genoma = (PredefinedGenoma)genomaProvider.getGenoma();

    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = PredefinedGeneFactory.createSequence(genoma);
    	solution.genotype = genotype;

    	List<Allele> alleles = genoma.getAllAlleles();
    	IntStream.range(0, genotype.genes.size()).forEach(i -> genotype.genes.get(i).allele = alleles.get(i));
    	
    	return solution;
    }

	@Override
	public Solution createPredefined(List<Object> alleleValues) {
		return createBaseModel();
	}


}

package it.red.algen.metagarden;

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
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.metadata.MetadataBasedGenoma;

@Component
public class MegSolutionsFactory implements SolutionsFactory {

    @Autowired
    @Resource(name="megGenomaProvider")
    private GenomaProvider genomaProvider;

    // TODOM: genotype builders based directly inside in genoma
    
    
    
    /**
     * Create a random solution
     * 
     * IMPORTANT: infinite Trees!
     */
    public Solution createRandom() {
    	MetadataBasedGenoma genoma = (MetadataBasedGenoma)genomaProvider.collect();
    	GenericSolution solution = new GenericSolution();

    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = genoma.createSequence();
    	solution.genotype = genotype;
    	
    	List<Allele> alleles = genoma.createRandomAlleles();
    	IntStream.range(0, genotype.genes.size()).forEach(i -> genotype.genes.get(i).allele = alleles.get(i));
    	
    	return solution;
    }
    
    
    /**
     * Garden with every plant on the same initial position
     * 
     * IMPORTANT: infinite Trees!
     */
    @Override
    public Solution createBaseModel() {
    	MetadataBasedGenoma genoma = (MetadataBasedGenoma)genomaProvider.collect();
    	GenericSolution solution = new GenericSolution();
    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = genoma.createSequence();
    	solution.genotype = genotype;

    	List<Allele> alleles = genoma.createRandomAlleles(); // TODOM: create a list of alleles indexed indentically to the gene pos... useful??
    	IntStream.range(0, genotype.genes.size()).forEach(i -> genotype.genes.get(i).allele = alleles.get(i));
    	
    	return solution;
    }

    
	@Override
	public Solution createPredefined(List<Object> alleleValues) {
		throw new UnsupportedOperationException("NYI");
	}

}

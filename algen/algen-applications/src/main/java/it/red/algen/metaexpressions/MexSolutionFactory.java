package it.red.algen.metaexpressions;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.metadata.MetadataBasedGenoma;

@Component
public class MexSolutionFactory implements SolutionsFactory {

    @Autowired
    @Resource(name="mexGenomaProvider")
    private GenomaProvider genomaProvider;

    // TODOM: genotype builders based directly inside in genoma
    public Solution createRandom() {
    	MetadataBasedGenoma genoma = (MetadataBasedGenoma)genomaProvider.collect();
    	GenericSolution solution = new GenericSolution();

    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = genoma.createSequenceByPositions(Arrays.asList("0", "1", "2"));
    	solution.genotype = genotype;
    	
    	genotype.genes.get(0).allele = genoma.createRandomAlleleByCode("operand");
    	genotype.genes.get(1).allele = genoma.createRandomAlleleByCode("operator");
    	genotype.genes.get(2).allele = genoma.createRandomAlleleByCode("operand");

    	return solution;
    }
    
    @Override
    public Solution createBaseModel() {
    	return createByValues(0L, '+', 0L);
    }

	@Override
	public Solution createPredefined(List<Object> alleleValues) {
		return createByValues(alleleValues.get(0), alleleValues.get(1), alleleValues.get(2));
	}


    
	private GenericSolution createByValues(Object operand1, Object operator, Object operand2) {
    	MetadataBasedGenoma genoma = (MetadataBasedGenoma)genomaProvider.collect();
		GenericSolution solution = new GenericSolution();

    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = genoma.createSequenceByPositions(Arrays.asList("0", "1", "2"));
    	solution.genotype = genotype;
    	
    	genotype.genes.get(0).allele = genoma.createPredefinedAlleleByValue("operand", operand1);
    	genotype.genes.get(1).allele = genoma.createPredefinedAlleleByValue("operator", operator);
    	genotype.genes.get(2).allele = genoma.createPredefinedAlleleByValue("operator", operand2);
		return solution;
	}
    
}

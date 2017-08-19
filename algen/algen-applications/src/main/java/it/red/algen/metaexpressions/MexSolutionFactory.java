package it.red.algen.metaexpressions;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.metadata.MetadataGeneFactory;
import it.red.algen.metadata.StandardMetadataGenoma;

@Component
public class MexSolutionFactory implements SolutionsFactory<StandardMetadataGenoma> {


    // TODOM: genotype builders based directly inside in genoma
    public Solution createRandom(StandardMetadataGenoma genoma) {
    	GenericSolution solution = new GenericSolution();

    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = MetadataGeneFactory.createSequenceByPositions(genoma, Arrays.asList("0", "1", "2"));
    	solution.genotype = genotype;
    	
    	genotype.genes.get(0).allele = genoma.createRandomAlleleByCode("operand");
    	genotype.genes.get(1).allele = genoma.createRandomAlleleByCode("operator");
    	genotype.genes.get(2).allele = genoma.createRandomAlleleByCode("operand");

    	return solution;
    }
    
    @Override
    public Solution createBaseModel(StandardMetadataGenoma genoma) {
    	return createByValues(genoma, 0L, '+', 0L);
    }

	@Override
	public Solution createPredefined(StandardMetadataGenoma genoma, List<Object> alleleValues) {
		return createByValues(genoma, alleleValues.get(0), alleleValues.get(1), alleleValues.get(2));
	}


    
	private GenericSolution createByValues(StandardMetadataGenoma genoma, Object operand1, Object operator, Object operand2) {
		GenericSolution solution = new GenericSolution();

    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = MetadataGeneFactory.createSequenceByPositions(genoma, Arrays.asList("0", "1", "2"));
    	solution.genotype = genotype;
    	
    	genotype.genes.get(0).allele = genoma.createAlleleByValue("operand", operand1);
    	genotype.genes.get(1).allele = genoma.createAlleleByValue("operator", operator);
    	genotype.genes.get(2).allele = genoma.createAlleleByValue("operator", operand2);
		return solution;
	}
    
}

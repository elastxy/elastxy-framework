package it.red.algen.algofrigerator;

import java.util.List;
import java.util.SortedMap;

import org.springframework.stereotype.Component;

import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.ChromosomeGenotype;
import it.red.algen.metadata.MetadataGeneFactory;
import it.red.algen.metadata.StandardMetadataGenoma;

@Component
public class MefSolutionsFactory implements SolutionsFactory<StandardMetadataGenoma> {


    // TODOM: genotype builders based directly inside in genoma
    public Solution createRandom(StandardMetadataGenoma genoma) {
    	GenericSolution solution = new GenericSolution();

    	ChromosomeGenotype genotype = new ChromosomeGenotype();
    	genotype.chromosomes = MetadataGeneFactory.createStrand(genoma);
    	
    	SortedMap<String,Allele> alleles = genoma.getRandomAllelesAsMap();
    	genotype.assignAlleles(alleles);
    	solution.genotype = genotype;

    	return solution;
    }
    
    @Override
    public Solution createBaseModel(StandardMetadataGenoma genoma) {
    	throw new UnsupportedOperationException("NYI");
//    	return createByValues(genoma, 0L, '+', 0L);
    }

	@Override
	public Solution createPredefined(StandardMetadataGenoma genoma, List<Object> alleleValues) {
    	throw new UnsupportedOperationException("NYI");
//		return createByValues(genoma, alleleValues.get(0), alleleValues.get(1), alleleValues.get(2));
	}


}

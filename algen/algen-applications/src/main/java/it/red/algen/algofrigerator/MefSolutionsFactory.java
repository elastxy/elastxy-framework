package it.red.algen.algofrigerator;

import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.engine.metadata.MetadataGenotypeFactory;
import it.red.algen.engine.metadata.StandardMetadataGenoma;

public class MefSolutionsFactory implements SolutionsFactory<StandardMetadataGenoma> {


    // TODOM: genotype builders based directly inside in genoma
    public Solution createRandom(StandardMetadataGenoma genoma) {
    	GenericSolution solution = new GenericSolution();
    	solution.genotype = MetadataGenotypeFactory.createStrand(genoma);
    	return solution;
    }
    
    @Override
    public Solution createBaseModel(StandardMetadataGenoma genoma) {
    	throw new UnsupportedOperationException("NYI");
    }

//	@Override
//	public Solution createPredefined(StandardMetadataGenoma genoma, List<Object> alleleValues) {
//    	throw new UnsupportedOperationException("NYI");
//	}


}

package org.elastxy.app.algofrigerator;

import org.elastxy.core.domain.experiment.GenericSolution;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.engine.factory.SolutionsFactory;
import org.elastxy.core.engine.metadata.MetadataGenotypeFactory;
import org.elastxy.core.engine.metadata.StandardMetadataGenoma;

public class MefSolutionsFactory implements SolutionsFactory<StandardMetadataGenoma> {


    // TODO2-4: decouple from genotype builders: used them directly inside in genoma
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

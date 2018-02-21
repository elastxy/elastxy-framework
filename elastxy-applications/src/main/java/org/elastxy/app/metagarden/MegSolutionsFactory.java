package org.elastxy.app.metagarden;

import org.elastxy.core.domain.experiment.GenericSolution;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.engine.metadata.MetadataGenotypeFactory;
import org.elastxy.core.engine.metadata.MetadataSolutionsFactory;
import org.elastxy.core.engine.metadata.StandardMetadataGenoma;

public class MegSolutionsFactory extends MetadataSolutionsFactory {

	
    /**
     * Garden with every plant on the same initial position
     * 
     * IMPORTANT: finite Trees of size >= than Places!
     */
    @Override
    public Solution createBaseModel(StandardMetadataGenoma genoma) {
    	GenericSolution solution = new GenericSolution();
    	solution.genotype = MetadataGenotypeFactory.createChromosome(genoma);
    	return solution;
    }

//    
//	@Override
//	public Solution createPredefined(StandardMetadataGenoma genoma, List<Object> alleleValues) {
//		throw new UnsupportedOperationException("NYI");
//	}

}

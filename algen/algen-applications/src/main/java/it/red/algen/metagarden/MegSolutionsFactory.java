package it.red.algen.metagarden;

import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.engine.metadata.MetadataGenotypeFactory;
import it.red.algen.engine.metadata.MetadataSolutionsFactory;
import it.red.algen.engine.metadata.StandardMetadataGenoma;

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

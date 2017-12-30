package org.elastxy.core.engine.metadata;

import java.util.List;

import org.elastxy.core.domain.experiment.GenericSolution;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.engine.factory.SolutionsFactory;

public class MetadataSolutionsFactory implements SolutionsFactory<StandardMetadataGenoma> {


	// TODO2-4: decouple from genotype builders: used them directly inside in genoma
    @Override
    public Solution createRandom(StandardMetadataGenoma genoma) {
    	return createSolution(genoma, genoma.getRandomAlleles());
    }
    
    @Override
    public Solution createBaseModel(StandardMetadataGenoma genoma) {
    	return createSolution(genoma, genoma.getOrderedAlleles());
    }

	private Solution createSolution(StandardMetadataGenoma genoma, List<Allele> alleles) {
		GenericSolution solution = new GenericSolution();
    	solution.genotype = MetadataGenotypeFactory.createChromosome(genoma, alleles);
    	return solution;
	}

//	@Override
//	public Solution createPredefined(StandardMetadataGenoma genoma, List<Object> alleleValues) {
//		return createByValues(genoma, alleleValues);
//	}
//
//	private GenericSolution createByValues(StandardMetadataGenoma genoma, List<Object> values) {
//		GenericSolution solution = new GenericSolution();
//    	Chromosome genotype = new Chromosome();
//    	genotype.genes = MetadataGeneticMaterialFactory.createSequence(genoma);
//    	solution.genotype = genotype;
//    	for(int i=0; i < genotype.genes.size(); i++){
//    		Gene gene = genotype.genes.get(i);
//        	gene.allele = genoma.createAlleleByValue(gene.metadataCode, values.get(i));
//    	}
//		return solution;
//	}
	    
}

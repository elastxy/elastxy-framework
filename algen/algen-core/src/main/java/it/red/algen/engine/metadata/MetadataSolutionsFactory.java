package it.red.algen.engine.metadata;

import java.util.List;

import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.genotype.Allele;

public class MetadataSolutionsFactory implements SolutionsFactory<StandardMetadataGenoma> {


    // TODOM: genotype builders based directly inside in genoma
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

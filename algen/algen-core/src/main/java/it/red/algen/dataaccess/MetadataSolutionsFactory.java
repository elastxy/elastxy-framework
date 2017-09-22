package it.red.algen.dataaccess;

import java.util.List;
import java.util.stream.IntStream;

import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.domain.genetics.genotype.Gene;
import it.red.algen.metadata.MetadataGeneticMaterialFactory;
import it.red.algen.metadata.StandardMetadataGenoma;

public class MetadataSolutionsFactory implements SolutionsFactory<StandardMetadataGenoma> {


    // TODOM: genotype builders based directly inside in genoma
    @Override
    public Solution createRandom(StandardMetadataGenoma genoma) {
    	return createSolution(genoma, genoma.getRandomAlleles());
    }
    
    @Override
    public Solution createBaseModel(StandardMetadataGenoma genoma) {
    	return createSolution(genoma, genoma.getFirstAlleles());
    }

	private Solution createSolution(StandardMetadataGenoma genoma, List<Allele> alleles) {
		GenericSolution solution = new GenericSolution();

    	Chromosome genotype = new Chromosome();
    	genotype.genes = MetadataGeneticMaterialFactory.createSequence(genoma);
    	solution.genotype = genotype;
    	
    	IntStream.range(0, genotype.genes.size()).forEach(i -> genotype.genes.get(i).allele = alleles.get(i));
    	
    	return solution;
	}

	@Override
	public Solution createPredefined(StandardMetadataGenoma genoma, List<Object> alleleValues) {
		return createByValues(genoma, alleleValues);
	}

	private GenericSolution createByValues(StandardMetadataGenoma genoma, List<Object> values) {
		GenericSolution solution = new GenericSolution();
    	Chromosome genotype = new Chromosome();
    	genotype.genes = MetadataGeneticMaterialFactory.createSequence(genoma);
    	solution.genotype = genotype;
    	for(int i=0; i < genotype.genes.size(); i++){
    		Gene gene = genotype.genes.get(i);
        	gene.allele = genoma.createAlleleByValue(gene.metadataCode, values.get(i));
    	}
		return solution;
	}
	    
}

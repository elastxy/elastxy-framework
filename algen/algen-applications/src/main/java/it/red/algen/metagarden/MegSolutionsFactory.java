package it.red.algen.metagarden;

import java.util.List;

import it.red.algen.applications.ApplicationException;
import it.red.algen.dataaccess.MetadataSolutionsFactory;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.metadata.MetadataGeneticMaterialFactory;
import it.red.algen.metadata.StandardMetadataGenoma;

public class MegSolutionsFactory extends MetadataSolutionsFactory {

	
    /**
     * Garden with every plant on the same initial position
     * 
     * IMPORTANT: finite Trees of size >= than Places!
     */
    @Override
    public Solution createBaseModel(StandardMetadataGenoma genoma) {
    	GenericSolution solution = new GenericSolution();
    	
    	Chromosome genotype = new Chromosome();
    	genotype.genes = MetadataGeneticMaterialFactory.createSequence(genoma);
    	solution.genotype = genotype;

    	// TODOM: new method of genoma for creating a list of alleles indexed identically to the gene pos... useful??
    	List<Allele> alleles = genoma.createRandomAllelesByCode(genotype.genes.get(0).metadataCode); // TODOM: get(0)?
    	
    	if(alleles.size() < genotype.genes.size()){
    		throw new ApplicationException("Number of possible different alleles less than number of genes creating a base predefined Solution. Check if you need the createRandom instead or try adding alleles");
    	} 
    	
    	for(int i=0; i < alleles.size(); i++){
    		genotype.genes.get(i).allele = alleles.get(i);
    	}
    	
    	return solution;
    }

    
	@Override
	public Solution createPredefined(StandardMetadataGenoma genoma, List<Object> alleleValues) {
		throw new UnsupportedOperationException("NYI");
	}

}

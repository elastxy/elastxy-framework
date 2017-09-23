package it.red.algen.algofrigerator;

import java.util.SortedMap;

import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.domain.genetics.genotype.Strand;
import it.red.algen.metadata.MetadataGeneticMaterialFactory;
import it.red.algen.metadata.StandardMetadataGenoma;

public class MefSolutionsFactory implements SolutionsFactory<StandardMetadataGenoma> {


    // TODOM: genotype builders based directly inside in genoma
    public Solution createRandom(StandardMetadataGenoma genoma) {
    	GenericSolution solution = new GenericSolution();

    	Strand genotype = MetadataGeneticMaterialFactory.createStrand(genoma);
    	
    	SortedMap<String,Allele> alleles = genoma.getRandomAllelesAsMap();
    	genotype.assignAlleles(alleles);
    	solution.genotype = genotype;

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

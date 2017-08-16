package it.red.algen.metasudoku;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.dataaccess.SolutionsFactory;
import it.red.algen.domain.experiment.GenericSolution;
import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.metadata.MetadataBasedGenoma;

@Component
public class MesSolutionFactory implements SolutionsFactory {

    @Autowired
    @Resource(name="mesGenomaProvider")
    private GenomaProvider genomaProvider;

    public Solution createRandom() {
    	MetadataBasedGenoma genoma = (MetadataBasedGenoma)genomaProvider.getGenoma();
    	GenericSolution solution = new GenericSolution();

    	SequenceGenotype genotype = new SequenceGenotype();
    	genotype.genes = genoma.createSequenceByPositions(IntStream.range(0, 81).boxed().map(i -> String.valueOf(i)).collect(Collectors.toList()));
    	solution.genotype = genotype;

    	List<Allele> alleles = genoma.createRandomAlleles();
    	IntStream.range(0, genotype.genes.size()).forEach(i -> genotype.genes.get(i).allele = alleles.get(i));

    	return solution;
    }
    
    @Override
    public Solution createBaseModel() {
    	return createEmpty();
    }

	@Override
	public Solution createPredefined(List<Object> alleleValues) {
		return createEmpty();
	}


    
	private Solution createEmpty() {
    	MetadataBasedGenoma genoma = (MetadataBasedGenoma)genomaProvider.getGenoma();
		GenericSolution result = (GenericSolution)createRandom();
		SequenceGenotype genotype = (SequenceGenotype)result.genotype;
		for(int i=0; i < genotype.genes.size(); i++){
			genotype.genes.get(i).allele = genoma.createPredefinedAlleleByValue("cell", 0);
		}
		return result;
	}
    
}

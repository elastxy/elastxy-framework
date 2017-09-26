package it.red.algen.domain.genetics;

import java.util.List;
import java.util.Map;

import it.red.algen.domain.genetics.genotype.Allele;

public class PredefinedGenomaBuilder {

	/**
	 * Initializes Genoma with a single list of all possible alleles.
	 * 
	 * This list is applicable to all Genes: more efficient when 
	 * the same list of possible alleles is shared between Genes.
	 * 
	 * @param alleles
	 */
	public static PredefinedGenoma build(int numberOfPositions, List<Allele> alleles, boolean limitedAllelesStrategy){
		PredefinedGenoma result = new PredefinedGenoma();
		result.setLimitedAllelesStrategy(limitedAllelesStrategy);
		result.sharedAlleles = true;
		result.alleles.put(PredefinedGenoma.NO_POSITION, alleles);
		ChromosomeGenotypeStructure structure = new ChromosomeGenotypeStructure();
		((ChromosomeGenotypeStructure)structure).build(numberOfPositions);
		result.setGenotypeStructure(structure);
		return result;
	}

	
	/**
	 * Initializes Genoma with a map of genes:
	 * [position;list of all possible alleles]
	 * 
	 * @param alleles
	 */
	public static PredefinedGenoma build(Map<String, List<Allele>> alleles, boolean limitedAllelesStrategy){
		PredefinedGenoma result = new PredefinedGenoma();
		result.setLimitedAllelesStrategy(limitedAllelesStrategy);
		result.sharedAlleles = false;
		result.alleles = alleles;
		ChromosomeGenotypeStructure structure = new ChromosomeGenotypeStructure();
		((ChromosomeGenotypeStructure)structure).build(alleles.size());
		result.setGenotypeStructure(structure);
		return result;
	}
	
}

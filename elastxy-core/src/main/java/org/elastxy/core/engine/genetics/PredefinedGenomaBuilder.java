package org.elastxy.core.engine.genetics;

import java.util.List;

import org.elastxy.core.dataprovider.AlleleValuesProvider;
import org.elastxy.core.dataprovider.InMemoryAlleleValuesProvider;
import org.elastxy.core.domain.genetics.ChromosomeGenotypeStructure;
import org.elastxy.core.domain.genetics.genotype.Allele;

public class PredefinedGenomaBuilder {

	/**
	 * Initializes Genoma with Allele Provider, previously filled with Alleles.
	 * 
	 * @param alleles
	 */
	public static PredefinedGenoma build(int numberOfPositions, AlleleValuesProvider allelesProvider, boolean limitedAllelesStrategy){
		PredefinedGenoma result = new PredefinedGenoma();
		result.setLimitedAllelesStrategy(limitedAllelesStrategy);
		result.setAlleleValuesProvider(allelesProvider);
		ChromosomeGenotypeStructure structure = new ChromosomeGenotypeStructure();
		((ChromosomeGenotypeStructure)structure).build(numberOfPositions);
		result.setGenotypeStructure(structure);
		return result;
	}
	

	/**
	 * Initializes Genoma with a single list of all possible alleles.
	 * 
	 * This list is applicable to all Genes: more efficient when 
	 * the same list of possible alleles is shared between Genes.
	 * 
	 * @param numberOfPositions
	 * @param allelesProvider
	 * @param limitedAllelesStrategy
	 * @return
	 */
	public static PredefinedGenoma build(int numberOfPositions, List<Allele> alleles, boolean limitedAllelesStrategy){
		PredefinedGenoma result = new PredefinedGenoma();
		result.setLimitedAllelesStrategy(limitedAllelesStrategy);
		AlleleValuesProvider provider = new InMemoryAlleleValuesProvider();
		provider.insertAlleles(alleles);
		result.setAlleleValuesProvider(provider);
		ChromosomeGenotypeStructure structure = new ChromosomeGenotypeStructure();
		((ChromosomeGenotypeStructure)structure).build(numberOfPositions);
		result.setGenotypeStructure(structure);
		return result;
	}
}

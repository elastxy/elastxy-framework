package it.red.algen.engine.genetics;

import java.util.List;

import it.red.algen.dataaccess.AlleleValuesProvider;
import it.red.algen.dataaccess.InMemoryAlleleValuesProvider;
import it.red.algen.domain.genetics.ChromosomeGenotypeStructure;
import it.red.algen.domain.genetics.PredefinedGenoma;
import it.red.algen.domain.genetics.genotype.Allele;

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

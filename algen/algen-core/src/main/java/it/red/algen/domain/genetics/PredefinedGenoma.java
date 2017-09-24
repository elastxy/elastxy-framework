package it.red.algen.domain.genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.engine.AlgorithmException;
import it.red.algen.engine.genetics.AbstractGenoma;
import it.red.algen.utils.Randomizer;

/**
 * A Genoma with a predefined list of Alleles for every Gene position.
 * 
 * Single Chromosome Genoma.
 * 
 * @author red
 *
 */
public class PredefinedGenoma extends AbstractGenoma implements Genoma {

	/**
	 * To be used when position is not applicable 
	 * (same list of alleles shared betweeb genes)
	 */
	private final String NO_POSITION = "-1"; 
	
	private boolean sharedAlleles = false;
	

	/**
	 * Map of predefined alleles by position.
	 */
	private Map<String, List<Allele>> alleles = new HashMap<String, List<Allele>>();


	
	/**
	 * Initializes Genoma with a single list of all possible alleles.
	 * 
	 * This list is applicable to all Genes: more efficient when 
	 * the same list of possible alleles is shared between Genes.
	 * 
	 * @param alleles
	 */
	public void initialize(int numberOfPositions, List<Allele> alleles){
		sharedAlleles = true;
		this.alleles.put(NO_POSITION, alleles);
		genotypeStructure = new ChromosomeGenotypeStructure();
		((ChromosomeGenotypeStructure)genotypeStructure).build(numberOfPositions);
	}

	
	/**
	 * Initializes Genoma with a map of genes:
	 * [position;list of all possible alleles]
	 * 
	 * @param alleles
	 */
	public void initialize(Map<String, List<Allele>> alleles){
		sharedAlleles = false;
		this.alleles = alleles;
		genotypeStructure = new ChromosomeGenotypeStructure();
		((ChromosomeGenotypeStructure)genotypeStructure).build(this.alleles.size());
	}

	
	
	private void forbidNotSharedAlleles(){
		if(!sharedAlleles){
			throw new AlgorithmException("Same list of alleles are not shared between positions: a position must be specified.");
		}
	}


	
	
	/**
	 * Get all alleles always in the same order, picking 
	 * the first value everytime.
	 * @return
	 */
	@Override
	public List<Allele> getOrderedAlleles() {
		forbidNotSharedAlleles();
		return alleles.get(NO_POSITION);
		
//		forbidNotSharedAlleles();
//		List<Allele> result = new ArrayList<Allele>();
//		int positions = ((ChromosomeGenotypeStructure)genotypeStructure).getNumberOfGenes(0);
//		Allele fixedAllele = alleles.get(NO_POSITION).get(0);
//		for(int g=0; g < positions; g++){
//			result.add(fixedAllele);
//		}
//		return result;
	}

	
	/**
	 * Returns a random allele for every position, with no duplicates.
	 * TODOA: strategy for admitting duplicates or not?
	 * 
	 * Implemented only for shared values.
	 * @return
	 */
	@Override
	public List<Allele> getRandomAlleles() {
		forbidNotSharedAlleles();
		int positions = ((ChromosomeGenotypeStructure)genotypeStructure).getNumberOfGenes(0);
		
		// TODOA: genotype dimension it's NOT the list of Allele (we may have 10 genes
		// with a list of 7 possible alleles: 10 are the alleles to return, not 7!)
		List<Allele> result = new ArrayList<Allele>(alleles.get(NO_POSITION));
		if(positions!=result.size()){
			throw new AlgorithmException("Shared possible Alleles number differs from genotype positions. Fix NYI.");
		}
		Collections.shuffle(result);
		return result;
//		List<Object> alreadyUsedAlleles = new ArrayList<Object>();
//		for(String pos : alleles.keySet()){
//			Allele newAllele = ;
//			alreadyUsedAlleles.add(newAllele.value);
//			result.add(newAllele);
//		}
//		return alleles.keySet().stream().map(p -> getRandomAllele(p)).collect(Collectors.toList());
	}
	

	/**
	 * Returns a random allele for every position, with no duplicates.
	 * TODOA: strategy for admitting duplicates or not?
	 * 
	 * Implemented only for shared values.
	 * 
	 * @return
	 */
	@Override
	public SortedMap<String, Allele> getRandomAllelesAsMap() {
		forbidNotSharedAlleles();
		SortedMap<String, Allele> result = new TreeMap<String, Allele>();
		List<Allele> geneAlleles = new ArrayList<Allele>(alleles.get(NO_POSITION));
		for(int pos=0; pos < geneAlleles.size(); pos++){
			result.put(String.valueOf(pos), geneAlleles.get(pos));
		}
		return result;
	}
	

	/**
	 * Returns a random allele for given position.
	 */
	@Override
	public Allele getRandomAllele(String position){
		if(sharedAlleles){
			position = NO_POSITION;
		}
		List<Allele> positionsAlleles = alleles.get(position);
		return positionsAlleles.get(Randomizer.nextInt(positionsAlleles.size()));
	}


	/**
	 * Returns random alleles for given positions.
	 * @return
	 */
	@Override
	public List<Allele> getRandomAlleles(List<String> position){
		forbidLimitedAllelesStrategy();
		return position.stream().map(s -> getRandomAllele(s)).collect(Collectors.toList());
	}
	

	public String toString(){
		return String.format("PredefinedGenoma: %d alleles, limitedAllelesStrategy %b", alleles.size(), limitedAllelesStrategy);
	}


}

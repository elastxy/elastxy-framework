package it.red.algen.domain.genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.red.algen.dataaccess.WorkingDataset;
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
	
	public WorkingDataset workingDataset;
	

	/**
	 * Map of predefined alleles by position.
	 */
	private Map<String, List<Allele>> alleles = new HashMap<String, List<Allele>>();

	/**
	 * Structure of genotype
	 */
	private ChromosomeGenotypeStructure genotypeStructure;

	
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
		genotypeStructure.build(numberOfPositions);
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
		genotypeStructure.build(this.alleles.size());
	}


	@Override
	public GenotypeStructure getGenotypeStructure() {
		return genotypeStructure;
	}


	
	
	private void forbidNotSharedAlleles(){
		if(!sharedAlleles){
			throw new AlgorithmException("Same list of alleles are not shared between positions: a position must be specified.");
		}
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
	

	/**
	 * Returns a random allele for every position, with no duplicates.
	 * @return
	 */
	@Override
	public List<Allele> getRandomAlleles() {
		forbidNotSharedAlleles();
		List<Allele> result = new ArrayList<Allele>(alleles.get(NO_POSITION));
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
	 * Get all available alleles
	 * @return
	 */
	public List<Allele> getAllAlleles() {
		forbidNotSharedAlleles();
		return alleles.get(NO_POSITION);
	}
	

	public String toString(){
		return String.format("PredefinedGenoma: %d alleles, limitedAllelesStrategy %b", alleles.size(), limitedAllelesStrategy);
	}


}

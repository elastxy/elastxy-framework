package it.red.algen.domain.genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.red.algen.dataaccess.WorkingDataset;
import it.red.algen.utils.Randomizer;

/**
 * A Genoma with a predefined list of Alleles for every Gene position.
 * 
 * @author red
 *
 */
public class PredefinedGenoma implements Genoma {
	
	public WorkingDataset workingDataset;
	
	/**
	 * If FALSE
	 * 
	 * Any number of Alleles can be created of the same type
	 * 
	 * If TRUE
	 * 
	 * Limits the number of total Alleles to those predefined at the beginning.
	 * When generating a set of Alleles for a number of genes, takes care of excluding 
	 * those already selected
	 */
	// TODOM: manage by strategy
	public boolean limitedAllelesStrategy = false;

	/**
	 * Map of predefined alleles by position.
	 */
	private Map<String, List<Allele>> alleles = new HashMap<String, List<Allele>>();

	
	public void initialize(Map<String, List<Allele>> alleles){
		this.alleles = alleles;
	}

	
	/**
	 * TODOA: separate Genoma role woth working dataset
	 */
	@Override
	public WorkingDataset getWorkingDataset() {
		return workingDataset;
	}

	@Override
	public void setWorkingDataset(WorkingDataset workingDataset) {
		this.workingDataset = workingDataset;
	}
	
	
	@Override
	public int getPositionsSize(){
		return alleles.size();
	}


	@Override
	public List<String> getPositions() {
		List<String> positions = IntStream.range(0, alleles.size()).boxed().map(i -> i.toString()).collect(Collectors.toList());
		return positions;
	}

	@Override
	public boolean isLimitedAllelesStrategy() {
		return limitedAllelesStrategy;
	}


	@Override
	public void setLimitedAllelesStrategy(boolean limitedAllelesStrategy) {
		this.limitedAllelesStrategy = limitedAllelesStrategy;
	}
	

	/**
	 * Some methods are not allowed when limited alleles strategy is on
	 */
	private void forbidLimitedAllelesStrategy(){
		if(limitedAllelesStrategy){
			throw new IllegalStateException("Cannot generate Allele in limited context: you must use aggregate methods.");
		}
	}
	
	
	@Override
	public Allele getRandomAllele(String position){
		List<Allele> positionsAlleles = alleles.get(position);
		return positionsAlleles.get(Randomizer.nextInt(positionsAlleles.size()));
	}

	
	@Override
	public List<Allele> getRandomAlleles(List<String> position){
		forbidLimitedAllelesStrategy();
		return position.stream().map(s -> getRandomAllele(s)).collect(Collectors.toList());
	}
	

	/**
	 * Returns an random allele for every position.
	 * @return
	 */
	@Override
	public List<Allele> getRandomAlleles() {
		List<Allele> result = new ArrayList<Allele>(alleles.get("0")); // TODOA: shared list of all alleles
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
		return alleles.get("0"); // TODOA: get(0) is BAD: add access to list of common alleles
	}
	

	public String toString(){
		return String.format("PredefinedGenoma: %d alleles, limitedAllelesStrategy %b", alleles.size(), limitedAllelesStrategy);
	}


//	@Override
//	public int getNumberOfStrands() {
//		return 0;
//	}


	@Override
	public int getNumberOfChromosomes() {
		return 1;
	}


	@Override
	public int getNumberOfGenes(int chromosome) {
		return alleles.size();
	}



}

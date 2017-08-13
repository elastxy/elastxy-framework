package it.red.algen.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import it.red.algen.domain.genetics.Allele;
import it.red.algen.engine.AlleleGenerator;

public class PredefinedGenoma implements Genoma {
	private static Random RANDOM = new Random();

	/**
	 * Map of predefined alleles by position.
	 */
	public Map<String, List<Allele>> alleles = new HashMap<String, List<Allele>>();

	// TODOA: implmenent the strategy
	public boolean limitedAllelesStrategy = false;
	
	public boolean isLimitedAllelesStrategy() {
		return limitedAllelesStrategy;
	}


	public void setLimitedAllelesStrategy(boolean limitedAllelesStrategy) {
		this.limitedAllelesStrategy = limitedAllelesStrategy;
	}
	
	@Override
	public void setupAlleleGenerator(AlleleGenerator generator){
	}


	/**
	 * Retrieves a random Allele suitable for the given position
	 * @param position
	 * @return
	 */
	@Override
	public Allele createRandomAllele(String position){
		List<Allele> positionsAlleles = alleles.get(position);
		return positionsAlleles.get(RANDOM.nextInt(positionsAlleles.size()));
	}

	
	@Override
	public List<Allele> createRandomAlleles(List<String> position){
		return position.stream().map(s -> createRandomAllele(s)).collect(Collectors.toList());
	}

	/**
	 * Retrieves the predefined list of set of Alleles suitable for the given positions
	 * @param positions
	 * @return
	 */
	public List<Set<Allele>> getAlleles(List<String> positions){
		throw new UnsupportedOperationException("NYI");
//		return alleles.entrySet().stream().
//				filter(map -> positions.contains(map.getKey())
//				.flatMap(map -> map.getValue())
//				.collect(Collectors.toList());
	}

	
	/**
	 * Retrieves the set of Alleles suitable for the given position
	 * @param position
	 * @return
	 */
	public Set<Allele> getAlleles(String position){
		throw new UnsupportedOperationException("NYI");
//		List<Allele> positionsAlleles = alleles.get(position);
//		return positionsAlleles.get(RANDOM.nextInt(positionsAlleles.size()));
	}


	@Override
	public List<Allele> createRandomAlleles() {
		throw new UnsupportedOperationException("NYI");
	}
	
	

}

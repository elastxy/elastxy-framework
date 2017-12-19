package org.elastxy.core.engine.genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.elastxy.core.dataprovider.AlleleValuesProvider;
import org.elastxy.core.domain.genetics.AbstractGenoma;
import org.elastxy.core.domain.genetics.ChromosomeGenotypeStructure;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.engine.core.AlgorithmException;
import org.elastxy.core.engine.core.Randomizer;

/**
 * A Genoma with a predefined list of Alleles for every Gene position.
 * 
 * Single Chromosome Genoma.
 * 
 * The list of common alleles is maintained in a separate list for efficiency.
 * 
 * @author red
 *
 */
public class PredefinedGenoma extends AbstractGenoma implements Genoma {


	/**For a PredefinedGenoma if a valuesProvider is set, 
	 * then all alleles must be pulled from that Provider.
	 * 
	 * TODOM-1: sharedAlleles linked to provider number is ok?
	 */
	@Override
	public void setAlleleValuesProvider(AlleleValuesProvider provider){
		super.setAlleleValuesProvider(provider);
		sharedAlleles = provider.countProviders()==1;
	}
	
	
	/**
	 * Get alleles always in the same order, picking: 
	 * - the first value from every Provider
	 * - the first value in shared list
	 * @return
	 */
	@Override
	public List<Allele> getOrderedAlleles() {
		allowOnlySharedAlleles();
		
		List<Allele> result = new ArrayList<Allele>();
		int positions = ((ChromosomeGenotypeStructure)genotypeStructure).getNumberOfGenes(0);
		
		List<Allele> predefinedAlleles = alleleValuesProvider.getAlleles();
		
		for(int pos=0; pos < positions; pos++){
			if(this.limitedAllelesStrategy){
				result.add(predefinedAlleles.get(pos));
			}
			else {
				result.add(predefinedAlleles.get(0));
			}
		}
		
		return result;
	}

	
	/**
	 * Returns a random allele for every position, with no duplicates.
	 * 
	 * Implemented only for shared values.
	 * 
	 * TODOM-4: strategy for admitting duplicates or not?
	 * @return
	 */
	@Override
	public List<Allele> getRandomAlleles() {
		allowOnlySharedAlleles();
		int positions = ((ChromosomeGenotypeStructure)genotypeStructure).getNumberOfGenes(0);
		
		List<Allele> result = new ArrayList<Allele>(alleleValuesProvider.getAlleles());
		if(positions > result.size()){
			for(int pos=result.size(); pos < positions; pos++){
				result.add(getRandomAllele(String.valueOf(pos)));
			}
		}
		else if(positions < result.size()){
			throw new AlgorithmException("Shared possible Alleles number more than from genotype positions. Fix TODOM.");
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
	 * Returns a random allele for every position.
	 * Implemented only for shared values.
	 * 
	 * TODOM-4: strategy for admitting duplicates or not?
	 * 
	 * @return
	 */
	@Override
	public SortedMap<String, Allele> getRandomAllelesAsMap() {
		allowOnlySharedAlleles();
		
		SortedMap<String, Allele> result = new TreeMap<String, Allele>();
		List<Allele> geneAlleles = new ArrayList<Allele>(alleleValuesProvider.getAlleles());
		for(int pos=0; pos < genotypeStructure.getPositionsSize(); pos++){
			String posString = String.valueOf(pos);
			result.put(posString, getRandomAllele(posString));
		}
		return result;
	}
	

	/**
	 * Returns a random allele for given position.
	 */
	@Override
	public Allele getRandomAllele(String position){
		List<Allele> positionsAlleles = null;
		if(sharedAlleles){
			positionsAlleles = alleleValuesProvider.getAlleles();
		}
		else {
			positionsAlleles = alleleValuesProvider.getAlleles(position);
		}
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
		return String.format("PredefinedGenoma: %b sharedAlleles, limitedAllelesStrategy %b", sharedAlleles, limitedAllelesStrategy);
	}


}
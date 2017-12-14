package it.red.algen.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.red.algen.domain.genetics.genotype.Allele;

/**
 * A simple Allele provider for hosting list of Alleles.
 * 
 * It can be loaded in a GenomaProvider.
 * 
 * @author red
 *
 */
public class InMemoryAlleleValuesProvider implements AlleleValuesProvider {
	
	
	
	/**
	 * Used when a single list is enough, for efficiency.
	 */
	private List<Allele> singleListAlleles = new ArrayList<Allele>();

	
	/**
	 * Used when list of alleles is specific to a provider.
	 */
	private Map<String,List<Allele>> allelesByProvider = new HashMap<String,List<Allele>>();
	

	/**
	 * Return 1 if a single list of alleles is used,
	 * otherwise the number of providers mapped.
	 */
	@Override
	public int countProviders() {
		int result = allelesByProvider.size();
		return result==0 ? 1 : result;
	}
	
	@Override
	public List<Allele> getAlleles(String provider) {
		return allelesByProvider.get(provider);
	}

	
	@Override
	public void insertAlleles(String provider, List<Allele> alleles) {
		allelesByProvider.put(provider, alleles);
	}

	
	@Override
	public List<Allele> getAlleles() {
		return singleListAlleles;
	}

	
	@Override
	public void insertAlleles(List<Allele> alleles) {
		singleListAlleles = alleles;
	}

}

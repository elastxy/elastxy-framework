package it.red.algen.d.metaexpressions;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;

import it.red.algen.dataprovider.AlleleValuesProvider;
import it.red.algen.domain.genetics.genotype.Allele;

/**
 * Maintains RDD of Alleles cached.
 * 
 * @author red
 *
 */
public class RddNumbersAlleleValuesProvider implements AlleleValuesProvider {
	private JavaRDD<Allele> alleles;
	
	public RddNumbersAlleleValuesProvider(MexdWorkingDataset dataset){
		this.alleles = (JavaRDD)dataset.numbers.map(RddNumbersAlleleValuesProvider::toAllele).cache();
	}
	
	@Override
	public int countProviders() {
		return 1;
	}

	// TODOD: method getAlleles(number)
	@Override
	public List<Allele> getAlleles() {
		return alleles.collect();
	}
	
	@Override
	public List<Allele> getAlleles(String provider) {
		return getAlleles();
	}
	
	
	// TODOM: move to a "writable" alleleValuesProvider
	@Override
	public void insertAlleles(List<Allele> alleles) {
		if(true) throw new UnsupportedOperationException("Not supported: alleles are retrieved by RDD");
	}
	@Override
	public void insertAlleles(String provider, List<Allele> alleles) {
		if(true) throw new UnsupportedOperationException("Not supported: alleles are retrieved by RDD");
	}


	private static final Allele<Long> toAllele(Long value){
		Allele<Long> result = new Allele<Long>();
		result.value = value;
		return result;
	}
}

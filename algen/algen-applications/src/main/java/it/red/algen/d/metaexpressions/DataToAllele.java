package it.red.algen.d.metaexpressions;

import it.red.algen.domain.genetics.genotype.Allele;

public class DataToAllele {

	public static final Allele<Long> toAllele(Long value){
		Allele<Long> result = new Allele<Long>();
		result.value = value;
		return result;
	}
	
}

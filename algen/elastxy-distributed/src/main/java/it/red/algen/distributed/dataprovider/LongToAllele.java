package it.red.algen.distributed.dataprovider;

import it.red.algen.domain.genetics.genotype.Allele;

public class LongToAllele {

	public static final Allele<Long> toAllele(Long value){
		Allele<Long> result = new Allele<Long>();
		result.value = value;
		return result;
	}
	
}

package org.elastxy.distributed.dataprovider;

import org.elastxy.core.domain.genetics.genotype.Allele;

public class LongToAllele {

	public static final Allele<Long> toAllele(Long value){
		Allele<Long> result = new Allele<Long>();
		result.value = value;
		return result;
	}
	
}

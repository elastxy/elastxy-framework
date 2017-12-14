package org.elastxy.distributed.dataprovider;

import org.elastxy.core.domain.genetics.genotype.Allele;

public class IntegerToAllele {

	public static final Allele<Integer> toAllele(Integer value){
		Allele<Integer> result = new Allele<Integer>();
		result.value = value;
		return result;
	}
	
}

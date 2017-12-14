package org.elastxy.app.d.metaexpressions;

import org.elastxy.core.domain.genetics.genotype.Allele;

public class DataToAllele {

	public static final Allele<Long> toAllele(Long value){
		Allele<Long> result = new Allele<Long>();
		result.value = value;
		return result;
	}
	
}

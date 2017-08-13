package it.red.algen.metaexpressions;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import it.red.algen.domain.genetics.Allele;
import it.red.algen.engine.AlleleGenerator;
import it.red.algen.metadata.GeneMetadata;


@Component
public class MexAlleleGenerator implements AlleleGenerator {

	@Override
	public Allele<?> generate(GeneMetadata metadata) {
		Allele result = null;
		if(metadata.code.equals("operator")){
			result = new Allele<Character>();
			result.value = (Character)metadata.values.get(ThreadLocalRandom.current().nextInt(metadata.values.size()));
		}
		else if(metadata.code.equals("operand")){
			result = new Allele<Long>();
			result.value = ThreadLocalRandom.current().nextLong((Long)metadata.max - (Long)metadata.min + 1) + (Long)metadata.min; // From -max to +max
		}

		// Check consistency
		else {
			throw new IllegalArgumentException("Metadata not allowed: "+metadata.code);
		}
		return result;
	}
	
	

	@Override
	public Allele<?> generate(GeneMetadata metadata, Object value) {
		Allele result = null;
		if(metadata.code.equals("operator")){
			result = new Allele<Character>();
			result.value = value;
		}
		else if(metadata.code.equals("operand")){
			result = new Allele<Long>();
			result.value = value;
		}
		else {
			throw new IllegalArgumentException("Metadata not allowed: "+metadata.code);
		}
		
//		// Check consistency
//		if(!metadata.values.contains(value)){
//			throw new IllegalArgumentException("Cannot generate a new Allele: value not allowed ["+value+"]");
//		}
		return result;
	}



	@Override
	public Allele generateExclusive(GeneMetadata metadata, List<Object> exclusions) {
		throw new UnsupportedOperationException("NYI");
	}
	
	
}

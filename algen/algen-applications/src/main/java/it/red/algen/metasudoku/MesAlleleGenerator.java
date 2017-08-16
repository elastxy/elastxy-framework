package it.red.algen.metasudoku;

import java.util.List;

import org.springframework.stereotype.Component;

import it.red.algen.context.Randomizer;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.engine.AlleleGenerator;
import it.red.algen.metadata.GeneMetadata;


/**
 * Every sudoku Allele is values 0-9, considering 0 the empty cell value
 * @author red
 *
 */
@Component
public class MesAlleleGenerator implements AlleleGenerator {

	
	/**
	 * A new Allele is one of [1..9] numbers.
	 * 
	 * 0 is the empty value, cannot be generated as a new allele.
	 */
	@Override
	public Allele<?> generate(GeneMetadata metadata) {
		Allele result = null;
		if(metadata.code.equals("cell")){
			result = new Allele<Integer>();
			// NOTE: '1+': exclude '0'; '-1': include 9 of 10 positions
			result.value = (Integer)metadata.values.get(1+Randomizer.nextInt(metadata.values.size()-1));
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
		if(metadata.code.equals("cell")){
			result = new Allele<Integer>();
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

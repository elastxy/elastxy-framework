package it.red.algen.metasudoku;

import java.util.List;
import java.util.stream.Collectors;

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
	 * The empty value is not present in final solutions, 
	 * so it cannot be generated as a new allele.
	 */
	@Override
	public Allele<?> generate(GeneMetadata metadata) {
		Allele result = null;
		if(metadata.code.equals(MesGenomaProvider.ALLELE_CELL)){
			result = new Allele<Integer>();
			result.value = (Integer)metadata.values.get(Randomizer.nextInt(metadata.values.size()));
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
		if(metadata.code.equals(MesGenomaProvider.ALLELE_CELL)){
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
		Allele<Integer> result = new Allele<Integer>();
		List<Integer> subtracted = (List<Integer>)metadata.values.stream().filter(t -> !exclusions.contains(t)).collect(Collectors.toList());
		if(subtracted.isEmpty()){
			throw new IllegalStateException("Remaining values for generating alleles cannot be empty! Maybe allele possibile values are not enough for this gene?");
		}
		result.value = (Integer)subtracted.get(Randomizer.nextInt(subtracted.size()));
		return result;
	}
	
	
}

package it.red.algen.algofrigerator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import it.red.algen.algofrigerator.data.Recipe;
import it.red.algen.context.Randomizer;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.engine.AlleleGenerator;
import it.red.algen.metadata.GeneMetadata;


/**
 * TODOA: duplicated with MegAlleleGenerator!
 * TODOA: check if allele generator could access to genoma (feasible recipes) in case of large set of data
 * @author red
 *
 */
@Component
public class MefAlleleGenerator implements AlleleGenerator {

	
	@Override
	public Allele<?> generate(GeneMetadata metadata) {
		Allele<Long> result = new Allele<Long>();
		result.value = (Long)metadata.values.get(Randomizer.nextInt(metadata.values.size()));
		return result;
	}
	
	

	@Override
	public Allele<?> generate(GeneMetadata metadata, Object value) {
		Allele<Long> result = new Allele<Long>();
		result.value = (Long)value;
		return result;
	}



	@Override
	public Allele<?> generateExclusive(GeneMetadata metadata, List<Object> exclusions) {
		Allele<Long> result = new Allele<Long>();
		List<Long> subtracted = (List<Long>)metadata.values.stream().filter(t -> !exclusions.contains(t)).collect(Collectors.toList());
		if(subtracted.isEmpty()){
			throw new IllegalStateException("Remaining values for generating alleles cannot be empty! Maybe allele possibile values are not enough for this gene?");
		}
		result.value = (Long)subtracted.get(Randomizer.nextInt(subtracted.size()));
		return result;
	}
	
	
}

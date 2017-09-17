package it.red.algen.engine;

import java.util.List;
import java.util.stream.Collectors;

import it.red.algen.context.Randomizer;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.metadata.GeneMetadata;

public class MetadataAlleleGenerator implements AlleleGenerator {

	@Override
	public <T> Allele<T> generate(GeneMetadata metadata) {
		Allele<T> result = new Allele<T>();
		result.value = (T)metadata.randomPick();
		return result;
	}
	
	
	@Override
	public <T> Allele<T> generate(GeneMetadata metadata, T value) {
		Allele<T> result = new Allele<T>();
		result.value = (T)value;
		return result;
	}


	@Override
	public <T> Allele<T> generateExclusive(GeneMetadata metadata, List<T> exclusions) {
		Allele<T> result = new Allele<T>();
		List<T> subtracted = (List<T>)metadata.values.stream().filter(t -> !exclusions.contains(t)).collect(Collectors.toList());
		if(subtracted.isEmpty()){
			throw new IllegalStateException("Remaining values for generating alleles cannot be empty! Maybe allele possibile values are not enough for this gene?");
		}
		result.value = (T)subtracted.get(Randomizer.nextInt(subtracted.size()));
		return result;
	}

}

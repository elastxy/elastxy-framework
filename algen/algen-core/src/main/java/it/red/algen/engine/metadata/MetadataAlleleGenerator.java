package it.red.algen.engine.metadata;

import java.util.List;
import java.util.stream.Collectors;

import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.engine.AlgorithmException;
import it.red.algen.utils.Randomizer;

public class MetadataAlleleGenerator implements AlleleGenerator {

	/**
	 * Generates a new instance provided with only the given value.
	 */
	@Override
	public <T> Allele<T> generateFromValue(T value) {
		Allele<T> result = new Allele<T>();
		result.value = (T)value;
		return result;
	}


	/**
	 * Generare a new instance provided with a random value from metadata.
	 */
	@Override
	public <T> Allele<T> generateRandom(GeneMetadata metadata) {
		Allele<T> result = new Allele<T>();
		result.value = (T)GeneMetadataPicker.randomPick(metadata);
		return result;
	}
	

	/**
	 * Generates a new instance with a random value from metadata remaining 
	 * after exclusions.
	 */
	@Override
	public <T> Allele<T> generateExclusive(GeneMetadata metadata, List<T> exclusions) {
		Allele<T> result = new Allele<T>();
		List<T> subtracted = (List<T>)metadata.values.stream().filter(t -> !exclusions.contains(t)).collect(Collectors.toList());
		if(subtracted.isEmpty()){
			throw new AlgorithmException("Remaining values for generating alleles cannot be empty! Maybe allele possibile values are not enough for this gene?");
		}
		result.value = (T)subtracted.get(Randomizer.nextInt(subtracted.size()));
		return result;
	}

	
	/**
	 * Generates an instance with always the same value (first from metadata).
	 * Useful when requesting always the same Allele from a given metadata
	 * (e.g. when creating a new population with same genetic material).
	 */
	@Override
	public <T> Allele<T> generateFirst(GeneMetadata metadata) {
		Allele<T> result = new Allele<T>();
		result.value = (T)GeneMetadataPicker.pickFirst(metadata);
		return result;
	}
}

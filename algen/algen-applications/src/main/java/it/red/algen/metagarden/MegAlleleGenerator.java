package it.red.algen.metagarden;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import it.red.algen.domain.genetics.Allele;
import it.red.algen.engine.AlleleGenerator;
import it.red.algen.metadata.GeneMetadata;
import it.red.algen.metagarden.data.Tree;


@Component
public class MegAlleleGenerator implements AlleleGenerator {

	
	@Override
	public Allele<?> generate(GeneMetadata metadata) {
		Allele<Tree> result = new Allele<Tree>();
		result.value = (Tree)metadata.values.get(ThreadLocalRandom.current().nextInt(metadata.values.size()));
		return result;
	}
	
	

	@Override
	public Allele<?> generate(GeneMetadata metadata, Object value) {
		Allele<Tree> result = new Allele<Tree>();
		result.value = (Tree)value;
		return result;
	}



	@Override
	public Allele<?> generateExclusive(GeneMetadata metadata, List<Object> exclusions) {
		Allele<Tree> result = new Allele<Tree>();
		List<Tree> subtracted = (List<Tree>)metadata.values.stream().filter(t -> !exclusions.contains(t)).collect(Collectors.toList());
		if(subtracted.isEmpty()){
			throw new IllegalStateException("Remaining values for generating alleles cannot be empty! Maybe allele possibile values are not enough for this gene?");
		}
		result.value = (Tree)subtracted.get(ThreadLocalRandom.current().nextInt(subtracted.size()));
		return result;
	}
	
	
}

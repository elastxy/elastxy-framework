package it.red.algen.domain.genetics;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.cache.annotation.Cacheable;

/**
 * Simple sequence of Genes
 * 
 * Position: "<gene>"
 * E.g. "0", "1", .. , "N" 
 *
 * @author red
 */
public class SequenceGenotype implements Genotype {
	public List<Gene> genes = new ArrayList<Gene>();
	
	@Override
	public String encode() {
		return genes.stream().map(g -> g.encode()).collect(Collectors.joining());
	}
	
	@Override
	public SequenceGenotype copy() {
		SequenceGenotype result = new SequenceGenotype();
		result.genes = genes.stream().map(c -> c.copy()).collect(Collectors.toList());
		return result;
	}

//	@Cacheable(value = "genotype_positions") TODOM: cache
	@Override
	public List<String> getPositions() {
		List<String> result = IntStream.
				range(0, genes.size()).
				mapToObj(x -> String.valueOf(x)).
				collect(Collectors.toList());
		return result;
	}

	@Override
	public void replaceAllele(String position, Allele allele) {
		genes.get(Integer.parseInt(position)).allele = allele;
	}
	
	public String toString(){
		return genes.toString();
	}

	@Override
	public void swapAllele(String position, Allele newAllele) {
		GeneticsUtils.swapAllele(genes, position, newAllele);
	}

}

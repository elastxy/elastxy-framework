package it.red.algen.domain.genetics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Chromosome grouped lists of genes
 * 
 * Position: "<chromosome>.<gene>"
 * E.g. "0.0", "0.1", .. , "5.4", .. , "M.N" 
 *
 * @author red
 *
 */
public class ChromosomeGenotype implements Genotype{
	public List<Chromosome> strand = new ArrayList<Chromosome>();

	/**
	 * For every gene, encode the gene value
	 * 
	 */
	@Override
	public String encode() {
		String result = strand.stream().map(c -> c.encode()).collect(Collectors.joining());
		return result;
	}
	
	
	@Override
	public ChromosomeGenotype copy() {
		ChromosomeGenotype result = new ChromosomeGenotype();
		result.strand = strand.stream().map(c -> c.copy()).collect(Collectors.toList());
		return result;
	}


	@Override
	public List<String> getPositions() {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public void replaceAllele(String position, Allele allele) {
		throw new UnsupportedOperationException("NYI");
	}

	@Override
	public void swapAllele(String position, Allele newAllele) {
		throw new UnsupportedOperationException("NYI");
	}
}

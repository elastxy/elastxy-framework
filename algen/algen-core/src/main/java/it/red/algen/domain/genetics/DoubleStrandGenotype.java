package it.red.algen.domain.genetics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Double strand fashion genotype, similar to a two-strand DNA
 * For every gene, chose the dominant allele
 * 
 * TODOM: check if useful for improving performance
 *  
 * Position: "<strand>.<chromosome>.<gene>"
 * E.g. "0.0.0", "0.0.1", .. , "0.1.0", "5.4", .. , "M.N" 
 * 
 * @author red
 *
 */
public class DoubleStrandGenotype implements Genotype {
	public List<Chromosome> strand1 = new ArrayList<Chromosome>();
	public List<Chromosome> strand2 = new ArrayList<Chromosome>();

	@Override
	public String encode() {
		throw new UnsupportedOperationException("NYI"); 
	}

	
	@Override
	public DoubleStrandGenotype copy() {
		DoubleStrandGenotype result = new DoubleStrandGenotype();
		result.strand1 = cloneStrand(strand1);
		result.strand2 = cloneStrand(strand2);
		return result;
	}
	
	private List<Chromosome> cloneStrand(List<Chromosome> s){
		return s.stream().map(c -> c.copy()).collect(Collectors.toList());
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

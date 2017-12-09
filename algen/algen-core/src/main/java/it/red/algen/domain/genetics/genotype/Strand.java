package it.red.algen.domain.genetics.genotype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.red.algen.domain.genetics.Genotype;
import it.red.algen.engine.operators.MutatorLogics;

/**
 * Chromosome grouped lists of genes
 * 
 * Position: "<chromosome>.<gene>"
 * E.g. "0.0", "0.1", .. , "5.4", .. , "M.N" 
 * 
 * TODOM-4: rework to a GenePosition to represent position
 *
 * @author red
 *
 */
public class Strand implements Genotype {
	public List<Chromosome> chromosomes = new ArrayList<Chromosome>();

	/**
	 * For every gene, encode the gene value
	 * 
	 * TODOM-4: find a usage of encoded Genotype :)
	 */
	@Override
	public String encode() {
		String result = chromosomes.stream().map(c -> c.encode()).collect(Collectors.joining());
		return result;
	}
	
	
	@Override
	public Strand copy() {
		Strand result = new Strand();
		result.chromosomes = chromosomes.stream().map(c -> c.copy()).collect(Collectors.toList());
		return result;
	}


	@Override
	public List<String> getPositions() {
		List<String> result = new ArrayList<String>();
		for(int c=0; c < chromosomes.size(); c++){
			final int ch = c;
			result.addAll(IntStream.
				range(0, chromosomes.get(c).genes.size()).
				mapToObj(x -> ch+"."+String.valueOf(x)).
				collect(Collectors.toList()));
		}
		return result;
	}

	public int getNumberOfChromosomes() {
		return chromosomes.size();
	}
	
	public List<String> getPositions(int chromosome) {
		List<String> result = new ArrayList<String>();
		result.addAll(IntStream.
			range(0, chromosomes.get(chromosome).genes.size()).
			mapToObj(x -> chromosome+"."+String.valueOf(x)).
			collect(Collectors.toList()));
		return result;
	}

	@Override
	public void replaceAllele(String position, Allele allele) {
		String[] splitted = position.split("\\.");
		Chromosome chromosome = chromosomes.get(new Integer(splitted[0]));
		chromosome.genes.get(new Integer(splitted[1])).allele = allele;
	}

	@Override
	public void swapAllele(String position, Allele newAllele) {
		String[] splitted = position.split("\\.");
		Chromosome chromosome = chromosomes.get(new Integer(splitted[0]));
		MutatorLogics.swapAllele(chromosome.genes, position, newAllele);
	}
	
	public void assignAlleles(SortedMap<String,Allele> alleles){
		Iterator<Map.Entry<String,Allele>> it = alleles.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,Allele> entry = it.next();
			String[] splitted = entry.getKey().split("\\.");
			int chromosome = new Integer(splitted[0]);
			int gene = new Integer(splitted[1]);
			this.chromosomes.get(chromosome).genes.get(gene).allele = entry.getValue();
		}
	}
	
	public String toString(){
		StringBuffer result = new StringBuffer();
		result.append("Chromosomes: ");
		for(int c=0; c < this.chromosomes.size(); c++){
			result.append(chromosomes.get(c));
		}
		return result.toString();
	}
}

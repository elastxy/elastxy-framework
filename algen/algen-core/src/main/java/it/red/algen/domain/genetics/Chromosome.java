package it.red.algen.domain.genetics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Chromosome {
	public List<Gene> genes = new ArrayList<Gene>();
	
	public Chromosome copy(){
		Chromosome result = new Chromosome();
		result.genes = genes.stream().map(g -> g.copy()).collect(Collectors.toList());
		return result;
	}
	
	public String encode(){
		return genes.stream().map(g -> g.encode()).collect(Collectors.joining());
	}
	
	public String toString(){
		return genes.toString();
	}
}

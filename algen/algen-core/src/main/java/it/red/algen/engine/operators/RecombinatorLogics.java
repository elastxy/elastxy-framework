package it.red.algen.engine.operators;

import java.util.List;

import it.red.algen.domain.genetics.genotype.Gene;

public class RecombinatorLogics {


	public static void cutAndSwapSequence(List<Gene> off0genes, List<Gene> off1genes, int crossoverPoint){
		int sequenceEnd = off0genes.size();

		for(int pos=0; pos < sequenceEnd; pos++){
			if(pos < crossoverPoint){
				Gene tmp = off0genes.get(pos);
				off0genes.set(pos, off1genes.get(pos));
				off1genes.set(pos, tmp);
			}
			else {
				Gene tmp = off1genes.get(pos);
				off1genes.set(pos, off0genes.get(pos));
				off0genes.set(pos, tmp);
			}
		}
	}	
}

package it.red.algen.domain.experiment;

import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.Phenotype;

public class GenericSolution implements Solution {
	public Genotype genotype;
	public Phenotype phenotype;
	public Fitness fitness;

	@Override
	public Genotype getGenotype() {
		return genotype;
	}

	@Override
	public void setGenotype(Genotype genotype) {
		this.genotype = genotype;
	}

	@Override
	public Phenotype getPhenotype() {
		return phenotype;
	}

	@Override
	public Fitness getFitness() {
		return fitness;
	}

	@Override
	public void setFitness(Fitness fitness) {
		this.fitness = fitness;
	}

	@Override
	public Solution copy() {
		GenericSolution result = new GenericSolution();
		result.setFitness(fitness.copy());
		result.genotype = (Genotype)genotype.copy();
		result.phenotype = (Phenotype)phenotype.copy();
		return result;
	}

	@Override
	public String toString() {
		return String.format("Genotype: %s => %s", genotype, fitness);
	}
	
	@Override
	public String toStringDetails() {
		return String.format("[Solution: %s; Phenotype: %s; Fitness: %s", toString(), phenotype, fitness!=null?fitness.toString():null);
	}
	
}

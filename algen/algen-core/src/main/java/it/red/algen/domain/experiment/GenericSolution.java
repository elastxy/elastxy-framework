package it.red.algen.domain.experiment;

import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.Phenotype;

public class GenericSolution implements Solution {
	public transient Genotype genotype;
	public transient Phenotype phenotype;
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
	public void setPhenotype(Phenotype phenotype) {
		this.phenotype = phenotype;
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
		result.setFitness(fitness!=null?fitness.copy():null);
		result.genotype = (Genotype)genotype!=null?genotype.copy():null;
		result.phenotype = (Phenotype)phenotype!=null?phenotype.copy():null;
		return result;
	}

	@Override
	public String toString() {
		return String.format("Genotype %s => Phenotype %s => Fitness %s", genotype, phenotype, fitness);
	}
	
	@Override
	public String toStringDetails() {
		return String.format("[Solution: %s; Phenotype: %s; Fitness: %s", toString(), phenotype, fitness!=null?fitness.toString():null);
	}
	
}

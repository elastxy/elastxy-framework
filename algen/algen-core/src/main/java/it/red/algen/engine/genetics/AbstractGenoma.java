package it.red.algen.engine.genetics;

import java.util.List;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.GenomaPositionComparator;
import it.red.algen.domain.genetics.GenotypeStructure;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.utils.Randomizer;

public abstract class AbstractGenoma implements Genoma {
	protected static final GenomaPositionComparator POSITIONS_COMPARATOR = new GenomaPositionComparator();
	

	protected GenotypeStructure genotypeStructure;
	
	
	/**
	 * If FALSE
	 * 
	 * Any number of Alleles can be created of the same type
	 * 
	 * If TRUE
	 * 
	 * Limits the number of total Alleles to those predefined at the beginning.
	 * When generating a set of Alleles for a number of genes, takes care of excluding 
	 * those already selected
	 */
	// TODOM: manage by strategy
	protected boolean limitedAllelesStrategy = false;


	@Override
	public GenotypeStructure getGenotypeStructure() {
		return genotypeStructure;
	}
	
	@Override
	public void setGenotypeStructure(GenotypeStructure structure){
		this.genotypeStructure = structure;
	}
	
	@Override
	public void setLimitedAllelesStrategy(boolean limitedAllelesStrategy) {
		this.limitedAllelesStrategy = limitedAllelesStrategy;
	}
	
	
	/**
	 * Some methods are not allowed when limited alleles strategy is on
	 */
	protected void forbidLimitedAllelesStrategy(){
		if(limitedAllelesStrategy){
			throw new IllegalStateException("Cannot generate Allele in limited context: you must use aggregate methods.");
		}
	}


	/**
	 * Mutate given positions in the Solution, getting a new Allele 
	 * or swapping two existing, based on limited allele strategy.
	 * @param solution
	 * @param positions
	 */
	@Override
	public void mutate(Solution solution, List<String> positions) {
		String positionToMutate = positions.get(Randomizer.nextInt(positions.size()));
		if(limitedAllelesStrategy){
			Allele newAllele = getRandomAllele(positionToMutate);
			solution.getGenotype().swapAllele(positionToMutate, newAllele);
		}
		else {
			Allele newAllele = getRandomAllele(positionToMutate);
			solution.getGenotype().replaceAllele(positionToMutate, newAllele);
		}
	}

}

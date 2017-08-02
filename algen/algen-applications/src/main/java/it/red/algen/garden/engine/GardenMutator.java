package it.red.algen.garden.engine;

import java.util.Random;

import it.red.algen.domain.Solution;
import it.red.algen.engine.GenesFactory;
import it.red.algen.engine.Mutator;
import it.red.algen.garden.domain.GardenSolution;

public class GardenMutator implements Mutator {
    private static Random RANDOMIZER = new Random();

	/**
	 * Not used in Garden application
	 * 
	 */
    public void setGenesFactory(GenesFactory genesFactory) {
	}
	

    /**
     * Cambio l'ordine di alcune coppie a caso
     */
	public Solution mutate(Solution solution) {
		GardenSolution result = (GardenSolution)solution;
		int pos = RANDOMIZER.nextInt(result.placementGenes.size()-1); // posso mutare fino alla coppia [N-1, N]
		GardenSolution.substituteTree(result.placementGenes.get(pos), result.placementGenes.get(pos+1));
		return result;
	}

}

package it.red.algen.garden.engine;

import java.util.Random;

import it.red.algen.engine.interfaces.GenesFactory;
import it.red.algen.engine.interfaces.Mutator;
import it.red.algen.garden.domain.GardenSolution;

public class GardenMutator implements Mutator<GardenSolution> {
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
	public GardenSolution mutate(GardenSolution solution) {
		int pos = RANDOMIZER.nextInt(solution.placementGenes.size()-1); // posso mutare fino alla coppia [N-1, N]
		GardenSolution.substituteTree(solution.placementGenes.get(pos), solution.placementGenes.get(pos+1));
		return solution;
	}

}

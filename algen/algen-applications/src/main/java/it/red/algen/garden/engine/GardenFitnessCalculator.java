package it.red.algen.garden.engine;

import it.red.algen.engine.FitnessCalculator;
import it.red.algen.engine.IllegalSolutionException;
import it.red.algen.garden.domain.GardenFitness;
import it.red.algen.garden.domain.GardenRawFitness;
import it.red.algen.garden.domain.GardenSolution;
import it.red.algen.garden.domain.GardenTarget;
import it.red.algen.garden.domain.PlacementGene;

public class GardenFitnessCalculator  implements FitnessCalculator<GardenSolution,GardenTarget,GardenFitness,Double>{

	@Override
	public GardenFitness calculate(GardenSolution solution, GardenTarget target) {
        double sValue = 0;
        double normalized = 0.0;
        String legalCheck = null;
        try { 
            sValue = perform(solution); 
            // sValue in questo caso = distance, essendo sempre 0 il valore riferimento
            // es. sValue = 12, worst = 100, dist = 12, normalized = 88%
            // es. sValue = 76, worst = 100, dist = 76, normalized = 24%
            normalized = 1 - sValue / (double)((GardenRawFitness)target.getRawFitness()).rawFitness;
        } catch(IllegalSolutionException ex){ 
            legalCheck = "Divisione per 0 non ammessa: secondo operando non valido.";
            normalized = 0;
        }
        
        GardenFitness fitness = new GardenFitness();
        fitness.setValue(normalized);
        fitness.setLegalCheck(legalCheck);
        solution.setFitness(fitness);
        return fitness;
	}

	@Override
	public Double perform(GardenSolution solution) {
    	double result = 0;
    	int deadTreesCount = 0;
    	for(PlacementGene gene : solution.placementGenes){
    		result += gene.calcFitness();
    		deadTreesCount += result > 0.99 ? 1 : 0;
    	}
    	
    	// Se il numero di piante morte supera una soglia, la funzione ritorna il peggio (1)
    	if(deadTreesCount / solution.placementGenes.size() > 0.8){
    		result = solution.placementGenes.size();
    	}
    	return result;
	}

}

package it.red.algen.garden.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.red.algen.engine.Recombinator;
import it.red.algen.garden.domain.GardenSolution;
import it.red.algen.garden.domain.PlacementGene;

public class GardenRecombinator implements Recombinator<GardenSolution> {
    private static Random RANDOMIZER = new Random();


    /**
     * Un certo numero pari di posizioni cambiano pianta nei figli:
     * 
     * 1 a.	1 d		1A		1 b	1 d	
     * 2 b.	2 c		1B		2 a	2 c	
     * 3 c	3 b.	  2B	3 c	3 b	
     * 4 d	4 a.	  2A	4 d	4 a	
     * 5 e	5 e				5 e 5 e
     */
    public List<GardenSolution> recombine(List<GardenSolution> parents){
        
        // Inizialmente identiche ai genitori
        GardenSolution son1 = parents.get(0).clone();
        GardenSolution son2 = parents.get(1).clone();
        
        // 1: Seleziono le posizioni per le quali cambiare pianta: tutte
        List<Integer> positions = new ArrayList<Integer>(son1.placementGenes.size()); 
        for(int i=0; i < son1.placementGenes.size(); i++){
        	positions.add(i);
        }
        // Ordino in modo random (per evitare di prendere sempre le stesse)
        Collections.shuffle(positions);
        // Escludiamo gli estremi
        int crossoverPoint = 1+RANDOMIZER.nextInt(parents.get(0).placementGenes.size()-2);
        // Ci assicuriamo che il punto di taglio sia pari
        if(crossoverPoint % 2 != 0){
        	crossoverPoint++;
        }
        
        // 2: Crossover per coppie di geni
        for(int genePosition = 0; genePosition < crossoverPoint; genePosition++){
        	if(genePosition % 2 == 0 && genePosition < crossoverPoint){
        		// Tree of couple A
				recombine(son1, son2, genePosition);
        	}
        }
        
        // 3: Reset fitness (must be recalculated)
        son1.setFitness(null);
        son2.setFitness(null);
        
        return Arrays.asList(son1, son2);
    }

    
    // Scambio di coppia
	private void recombine(GardenSolution son1, GardenSolution son2, int genePosition) {
		PlacementGene gene1A = son1.placementGenes.get(genePosition);
		PlacementGene gene1B = son1.placementGenes.get(genePosition+1);
		PlacementGene gene2A = findByPlant(son2, gene1A.getTree().getCode());
		PlacementGene gene2B = findByPlant(son2, gene1B.getTree().getCode());
		GardenSolution.substituteTree(gene1A, gene1B);
		GardenSolution.substituteTree(gene2A, gene2B);
	}

    
    private PlacementGene findByPlant(GardenSolution solution, String code) {
    	for(PlacementGene gene : solution.placementGenes){
    		if(gene.getTree().getCode().equals(code)){
    			return gene;
    		}
    	}
		return null;
	}
	
}

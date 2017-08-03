/*
 * ExprSolution.java
 *
 * Created on 4 agosto 2007, 14.40
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.red.algen.domain.Solution;

/**
 *
 * @author grossi
 */
public class GardenSolution implements Solution<GardenSolution, GardenFitness> {
    private static Random RANDOMIZER = new Random();
    
    public transient List<PlacementGene> placementGenes;
    
    private GardenFitness fitness;
    
    public GardenSolution(Place[] places, Tree[] trees) {
    	placementGenes = new ArrayList<PlacementGene>(places.length);
    	for(int pos = 0; pos < places.length; pos++){
    		placementGenes.add(new PlacementGene(places[pos], trees[pos]));
    	}
    }

    
//    public String toJSON(){
//    	JsonObject json = Json.createObjectBuilder()
//    		     .add("name", "Falco")
//    		     .add("age", BigDecimal.valueOf(3))
//    		     .add("biteable", Boolean.FALSE).build();
//    		   String result = json.toString();
//    		   return result;
//    }
    
    
	@Override
    public GardenFitness getFitness(){
        return fitness;
    }

	@Override
	public void setFitness(GardenFitness fitness) {
		this.fitness = fitness;
	}

    
    
    /**
     * Un certo numero pari di posizioni cambiano pianta nei figli:
     * 
     * 1 a.	1 d		1A		1 b	1 d	
     * 2 b.	2 c		1B		2 a	2 c	
     * 3 c	3 b.	  2B	3 c	3 b	
     * 4 d	4 a.	  2A	4 d	4 a	
     * 5 e	5 e				5 e 5 e
     */
    public GardenSolution[] crossoverWith(GardenSolution other){
        
        // Inizialmente identiche ai genitori
        GardenSolution son1 = this.clone();
        GardenSolution son2 = other.clone();
        
        // 1: Seleziono le posizioni per le quali cambiare pianta: tutte
        List<Integer> positions = new ArrayList<Integer>(son1.placementGenes.size()); 
        for(int i=0; i < son1.placementGenes.size(); i++){
        	positions.add(i);
        }
        // Ordino in modo random (per evitare di prendere sempre le stesse)
        Collections.shuffle(positions);
        // Escludiamo gli estremi
        int crossoverPoint = 1+RANDOMIZER.nextInt(placementGenes.size()-2);
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
        
        GardenSolution[] sons = new GardenSolution[] {son1, son2};
        return sons;
    }

    
    // Scambio di coppia
	private void recombine(GardenSolution son1, GardenSolution son2, int genePosition) {
		PlacementGene gene1A = son1.placementGenes.get(genePosition);
		PlacementGene gene1B = son1.placementGenes.get(genePosition+1);
		PlacementGene gene2A = son2.findByPlant(gene1A.getTree().getCode());
		PlacementGene gene2B = son2.findByPlant(gene1B.getTree().getCode());
		substituteTree(gene1A, gene1B);
		substituteTree(gene2A, gene2B);
	}

	public static void substituteTree(PlacementGene gene1A, PlacementGene gene1B) {
		Tree tree = gene1A.getTree();
		gene1A.setTree(gene1B.getTree());
		gene1B.setTree(tree);
	}
    
    
    private PlacementGene findByPlant(String code) {
    	for(PlacementGene gene : placementGenes){
    		if(gene.getTree().getCode().equals(code)){
    			return gene;
    		}
    	}
		return null;
	}

    
    public GardenSolution clone(){
    	Place[] newPlaces = new Place[placementGenes.size()];
    	Tree[] newTrees = new Tree[placementGenes.size()];
    	for(int pos = 0; pos < placementGenes.size(); pos++){
    		newPlaces[pos] = placementGenes.get(pos).getPlace();
    		newTrees[pos] = placementGenes.get(pos).getTree();
    		
    	}
        return new GardenSolution(newPlaces, newTrees);
    }

	@Override
    public String getDetails(){
    	StringBuffer buffer = new StringBuffer();
    	for(int pos = 0; pos < placementGenes.size(); pos++){
    		PlacementGene gene = placementGenes.get(pos);
    		buffer.append(pos).append(gene.getPlace()).append(":").append(gene.getTree()).append(">").append(String.format("%.2f", gene.calcFitness()));
    		if(pos < placementGenes.size()-1){
    			buffer.append(";");
    		}
    	}
    	String details = toString();
    	if(details!=null && details.length()>0){
    		buffer.append("\n\t").append(toString());
    	}
        return buffer.toString();
    }

    
	@Override
    public String toString(){
        // Calcolo non ancora effettuato
        if(fitness==null){
            return "";
        }
        String res = fitness.getLegalCheck()!=null ? "###" : String.format("%1.5f", fitness.getValue());
        return " => F:"+res;
    }


}

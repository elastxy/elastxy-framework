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

import it.red.algen.domain.Fitness;
import it.red.algen.domain.Solution;
import it.red.algen.domain.Target;
import it.red.algen.engine.IllegalSolutionException;

/**
 *
 * @author grossi
 */
public class GardenSolution implements Solution {
    private static Random RANDOMIZER = new Random();
    
    private List<PlacementGene> placementGenes;
    
    private Fitness _fitness;
    private String _legalCheck;
    
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
    
    
	public String getRepresentation() {
		return toString();
	}
	
    public Fitness getFitness(){
        return _fitness;
    }
    
    
    private double compute() throws IllegalSolutionException {
    	double result = 0;
    	int deadTreesCount = 0;
    	for(PlacementGene gene : placementGenes){
    		result += gene.calcFitness();
    		deadTreesCount += result > 0.99 ? 1 : 0;
    	}
    	
    	// Se il numero di piante morte supera una soglia, la funzione ritorna il peggio (1)
    	if(deadTreesCount/placementGenes.size() > 0.8){
    		result = placementGenes.size();
    	}
    	return result;
    }
    
    public void calcFitness(Target target){
        GardenTarget t = (GardenTarget)target;
        double sValue = 0;
        double normalized = 0.0;
        try { 
            sValue = compute(); 
            // sValue in questo caso = distance, essendo sempre 0 il valore riferimento
            // es. sValue = 12, worst = 100, dist = 12, normalized = 88%
            // es. sValue = 76, worst = 100, dist = 76, normalized = 24%
            normalized = 1 - sValue / (double)((GardenRawFitness)t.getRawFitness()).rawFitness;
        } catch(IllegalSolutionException ex){ 
            _legalCheck = "Divisione per 0 non ammessa: secondo operando non valido.";
            normalized = 0;
        }
        _fitness = new GardenFitness(normalized);
    }
    
    public String legalCheck(){
        return _legalCheck;
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
    public Solution[] crossoverWith(Solution other){
        
        GardenSolution ot = (GardenSolution)other;

        // Inizialmente identiche ai genitori
        GardenSolution son1 = (GardenSolution)this.clone();
        GardenSolution son2 = (GardenSolution)ot.clone();
        
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
        
        Solution[] sons = new Solution[] {son1, son2};
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

	private void substituteTree(PlacementGene gene1A, PlacementGene gene1B) {
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

    
    /**
     * Cambio l'ordine di alcune coppie a caso
     */
	public void mute(){
        int pos = RANDOMIZER.nextInt(placementGenes.size()-1); // posso mutare fino alla coppia [N-1, N]
        substituteTree(placementGenes.get(pos), placementGenes.get(pos+1));
    }
    
    public Object clone(){
    	Place[] newPlaces = new Place[placementGenes.size()];
    	Tree[] newTrees = new Tree[placementGenes.size()];
    	for(int pos = 0; pos < placementGenes.size(); pos++){
    		newPlaces[pos] = placementGenes.get(pos).getPlace();
    		newTrees[pos] = placementGenes.get(pos).getTree();
    		
    	}
        return new GardenSolution(newPlaces, newTrees);
    }
    
    public String toString(){
    	StringBuffer buffer = new StringBuffer();
    	for(int pos = 0; pos < placementGenes.size(); pos++){
    		PlacementGene gene = placementGenes.get(pos);
    		buffer.append(pos).append(gene.getPlace()).append(":").append(gene.getTree()).append(">").append(String.format("%.2f", gene.calcFitness()));
    		if(pos < placementGenes.size()-1){
    			buffer.append(";");
    		}
    	}
    	String details = getDetails();
    	if(details!=null && details.length()>0){
    		buffer.append("\n\t").append(getDetails());
    	}
        return buffer.toString();
    }
    
    private String getDetails(){
        // Calcolo non ancora effettuato
        if(_fitness==null && _legalCheck==null){
            return "";
        }
        double fitness = ((GardenFitness)_fitness).getValue();
        String res = _legalCheck!=null ? "###" : String.format("%1.5f", fitness);
        return " => F:"+res;
    }
}

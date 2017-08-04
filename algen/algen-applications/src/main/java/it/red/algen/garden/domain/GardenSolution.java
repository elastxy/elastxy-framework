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
import java.util.List;

import it.red.algen.domain.Solution;

/**
 *
 * @author grossi
 */
public class GardenSolution implements Solution<GardenSolution, GardenFitness> {
    
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

    
    
	public static void substituteTree(PlacementGene gene1A, PlacementGene gene1B) {
		Tree tree = gene1A.getTree();
		gene1A.setTree(gene1B.getTree());
		gene1B.setTree(tree);
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

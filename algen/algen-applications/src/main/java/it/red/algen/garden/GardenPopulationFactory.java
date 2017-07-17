/*
 * PopulationFactory.java
 *
 * Created on 4 agosto 2007, 14.08
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import it.red.algen.Population;
import it.red.algen.conf.AlgorithmContext;
import it.red.algen.garden.domain.Place;
import it.red.algen.garden.domain.Tree;

/**
 *
 * @author grossi
 */
@Component
public class GardenPopulationFactory {
	private Place[] places;
	private Tree[] trees;

	public void init(Place[] places, Tree[] trees){
		this.places = places;
		this.trees = trees;
	}
	
	
	/**
	 * Crea una popolazione iniziale di piante collocate eventualmente casuale, i posti sono fissi
	 * @param number
	 * @return
	 */
    public Population createNew(AlgorithmContext context) {
    	Population population = new Population(context.parameters);
    	for(int i = 0; i < context.parameters._initialSelectionNumber; i++){
    		List<Tree> listOfTrees = Arrays.asList(trees);
    		if(context.parameters._initialSelectionRandom){
    			Collections.shuffle(listOfTrees);
    		}
    		GardenSolution solution = new GardenSolution(places, (Tree[])listOfTrees.toArray());
    		population.add(solution);
        }
        return population;
    }
    
    
}

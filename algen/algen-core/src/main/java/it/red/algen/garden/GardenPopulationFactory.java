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

import it.red.algen.Population;
import it.red.algen.garden.domain.Place;
import it.red.algen.garden.domain.Tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author grossi
 */
public class GardenPopulationFactory {
	private Place[] places;
	private Tree[] trees;

	
	public void init(Place[] places, Tree[] trees){
		this.places = places;
		this.trees = trees;
	}
	
	
	/**
	 * Crea una popolazione iniziale di piante collocate in modo casuale, i posti sono fissi
	 * @param number
	 * @return
	 */
    public Population createNew(int number) {
    	Population population = new Population();
    	for(int i = 0; i < number; i++){
    		List<Tree> listOfTrees = Arrays.asList(trees);
    		Collections.shuffle(listOfTrees);
    		GardenSolution solution = new GardenSolution(places, (Tree[])listOfTrees.toArray());
    		population.add(solution);
        }
        return population;
    }

    
}

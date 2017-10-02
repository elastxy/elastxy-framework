/*
 * Popolazione.java
 *
 * Created on 4 agosto 2007, 13.47
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain.experiment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.red.algen.engine.fitness.FitnessComparator;
import it.red.algen.engine.fitness.TargetFitnessComparator;

/** Population of solutions
 * 
 * @author grossi
 */
public class Population {
	
	/**
	 * Compares two fitness values
	 * 
	 * TODOM: make it injectable
	 */
    private static FitnessComparator FITNESS_COMPARATOR = new FitnessComparator();
    
    /**
     * List of all solutions
     */
    public transient List<Solution<?,?>> solutions = new ArrayList<Solution<?,?>>();

    
    public Solution<?,?> bestMatch;

    
    /**
     * TODOM: List of best-performing solutions ordered by fitness DESC
     */
//    public List<Solution> bestMatches = new ArrayList<Solution>();
//    public Solution getBestMatch(){
//    	return bestMatches.isEmpty() ? null : bestMatches.get(0);
//    }
//    public List<Solution> getBestMatches(){
//    	return bestMatches;
//    }
    
    public int size(){
    	return solutions.size();
    }
    
    public void add(Solution<?,?> solution){
        solutions.add(solution);
    }
    
    public void orderByFitnessDesc(){
    	Collections.sort(solutions, FITNESS_COMPARATOR);
    }
    
    public void orderByFitnessProximityDesc(BigDecimal targetFitness){
    	Collections.sort(solutions, new TargetFitnessComparator(targetFitness)); // TODOM: re-create too much expensive!
    }
    
    public String toString(){
    	return String.format("BestMatch %s amongst %d solutions", bestMatch, solutions.size());
    }
}

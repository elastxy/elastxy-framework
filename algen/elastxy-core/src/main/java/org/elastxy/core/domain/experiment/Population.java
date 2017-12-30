/*
 * Popolazione.java
 *
 * Created on 4 agosto 2007, 13.47
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.core.domain.experiment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.elastxy.core.engine.fitness.FitnessComparator;
import org.elastxy.core.engine.fitness.TargetFitnessComparator;

/** Population of solutions
 * 
 * @author grossi
 */
public class Population {
	
	/**
	 * Compares two fitness values
	 * 
	 * TODO2-2: make FitnessComparator injectable?
	 */
    private static FitnessComparator FITNESS_COMPARATOR = new FitnessComparator();
    
    
    /**
     * List of all solutions
     */
    public transient List<Solution<?,?>> solutions = new ArrayList<Solution<?,?>>();
    

    /**
     * The better solution of this generation.
     */
    public Solution<?,?> bestMatch;
    

    /**
     * The best solutions of this generation, ordered by fitness desc.
     * 
     * The set includes the overall best match, referenced by bestMatch
     * attribute.
     * 
     * The number of solutions is driven by ElitismParameters configuration.
     */
    public List<Solution<?,?>> bestMatches;

    
    /**
     * Goal is reached while processing these population solutions:
     * - fitness is 1.0
     * - fitness is identical to desider fitness value (if set)
     * - fitness is over threshold fitness value (if set)
     */
    public boolean goalReached = false;

    
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
    	// FIXME: TargetFitnessComparator in context: re-create is too much expensive!
    	Collections.sort(solutions, new TargetFitnessComparator(targetFitness));
    }
    
    public String toString(){
    	return String.format("BestMatch %s amongst %d solutions", bestMatch, solutions.size());
    }
}

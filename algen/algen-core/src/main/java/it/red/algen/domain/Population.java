/*
 * Popolazione.java
 *
 * Created on 4 agosto 2007, 13.47
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.red.algen.engine.FitnessComparator;

/** Contiene la popolazione di soluzioni
 * 
 * @author grossi
 */
public class Population {
    private static FitnessComparator FITNESS_COMPARATOR = new FitnessComparator();
    public transient List<Solution> solutions = new ArrayList<Solution>();
    public Solution bestMatch;
    
    
    public int size(){
    	return solutions.size();
    }
    
    public void add(Solution solution){
        solutions.add(solution);
    }
    
    public void orderByFitnessDesc(){
    	Collections.sort(solutions, FITNESS_COMPARATOR);
    }
    
    
    public String toString(){
    	StringBuffer result = new StringBuffer();
    	for(Solution s : solutions){
    		result.append(s).append("\n");
    	}
    	return result.toString();
    }
}

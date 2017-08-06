/*
 * Solution.java
 *
 * Created on 4 agosto 2007, 13.48
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain.interfaces;

/**
 *
 * @author grossi
 */
@SuppressWarnings("rawtypes")
public interface Solution<S extends Solution, F extends Fitness> {
	
    public F getFitness();
    
    public void setFitness(F fitness);
    
    public S clone();
        
    public String toString();
        
    public String getDetails();
}

/*
 * Solution.java
 *
 * Created on 4 agosto 2007, 13.48
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain.experiment;

import it.red.algen.domain.genetics.Genotype;
import it.red.algen.domain.genetics.phenotype.Phenotype;

/**
 *
 * @author grossi
 */
@SuppressWarnings("rawtypes")
public interface Solution<S extends Solution, F extends Fitness> {
	
	
	/**
	 * Hidden genetic information held by the solution
	 * 
	 * E.g. DNA sequence in the human individuals
	 * 
	 * @return
	 */
	public Genotype getGenotype();
	public void setGenotype(Genotype genotype);
	
	
	/**
	 * Visible manifestation of the hidden genotype
	 * 
	 * E.g. the whole individual with its exterior characteristics,
	 * such as green eyes or height
	 * 
	 * @return
	 */
	public Phenotype getPhenotype();
	public void setPhenotype(Phenotype phenotype);
	
	
	/**
	 * Performance indicator about how the individual performs in the environment,
	 * normalized to 1
	 * 
	 * TODOM: explain and apply normalization techniques
	 * 
	 * @return
	 */
    public F getFitness();
    
    public void setFitness(F fitness);
    
    
    public S copy();
    
    
    /**
     * Short description
     * @return
     */
    public String toString();
        
    
    /**
     * Long description
     * @return
     */
    public String toStringDetails();
}

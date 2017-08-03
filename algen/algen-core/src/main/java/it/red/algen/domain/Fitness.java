/*
 * Fitness.java
 *
 * Created on 4 agosto 2007, 13.50
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain;

/**
 *
 * @author grossi
 */
public interface Fitness {
	
	/**
	 * Value for fitness, possibly normalized in a [0..1] interval
	 * 
	 * @return
	 */
    public Double getValue();
    
    public void setValue(Double value);

    /**
     * Not null if it's not possible to calculate fitness
     * @return
     */
    public String getLegalCheck();
    
    public void setLegalCheck(String legalCheck);
    
    public boolean fit();
    
    public boolean greaterThan(Fitness other);

    public boolean sameOf(Fitness other);
}

/*
 * Target.java
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
public interface Target<R extends RawFitness> {
    
	/**
	 * Returns the non-normalized fitness values, useful to evaluate any solution against
	 * @return
	 */
	public R getRawFitness();
	
	public void setRawFitness(R raw);
}

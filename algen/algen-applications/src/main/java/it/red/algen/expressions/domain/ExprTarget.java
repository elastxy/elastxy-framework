/*
 * ExprTarget.java
 *
 * Created on 4 agosto 2007, 14.32
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions.domain;

import it.red.algen.domain.RawFitness;
import it.red.algen.domain.Target;

/**
 *TODOM: distance is a indirect measure of the fitness of a chromosome,
 *Comes from environment contraints and characteristics, and can be used globally in an experiment to simplify 
 *other chromosomes fitness calculation
 *distance <= number interval
 *
 *fitness <= environment constraints
 *
 *Could be taken outside in an interface
 *
 * @author grossi
 */
public class ExprTarget implements Target {
    private int _computeValue;
    private RawFitness rawFitness;
    
    public ExprTarget(int computeValue) {
    	this._computeValue = computeValue;
    }

    public int getComputeValue(){
        return _computeValue;
    }
    
	@Override
	public RawFitness getRawFitness() {
		return rawFitness;
	}

	@Override
	public void setRawFitness(RawFitness raw) {
		this.rawFitness = raw;
	}
}

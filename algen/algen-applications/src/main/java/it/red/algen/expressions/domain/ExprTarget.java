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

import it.red.algen.domain.interfaces.Target;

/**
 *TODOM: distance is an indirect absolute measure of the fitness of a chromosome,
 *Comes from environment contraints and characteristics, and can be used globally in an experiment to simplify 
 *other chromosomes fitness calculation
 *distance <= number interval
 *fitness <= environment constraints
 *
 *Could be taken outside in an interface
 *
 * @author grossi
 */
public class ExprTarget implements Target<ExprRawFitness> {
    private long targetValue;
    private transient ExprRawFitness rawFitness;
    
    public ExprTarget(long targetValue) {
    	this.targetValue = targetValue;
    }

    public long getComputeValue(){
        return targetValue;
    }
    
	@Override
	public ExprRawFitness getRawFitness() {
		return rawFitness;
	}

	@Override
	public void setRawFitness(ExprRawFitness raw) {
		this.rawFitness = raw;
	}
}

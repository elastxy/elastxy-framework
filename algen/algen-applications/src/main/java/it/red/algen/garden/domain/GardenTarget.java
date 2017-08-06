/*
 * ExprTarget.java
 *
 * Created on 4 agosto 2007, 14.32
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden.domain;

import it.red.algen.domain.interfaces.RawFitness;
import it.red.algen.domain.interfaces.Target;

/**
 *
 * @author grossi
 */
public class GardenTarget implements Target {
	private RawFitness rawFitness;
	
	
	@Override
	public RawFitness getRawFitness() {
		return rawFitness;
	}


	@Override
	public void setRawFitness(RawFitness raw) {
		this.rawFitness = raw;
	}
	


}

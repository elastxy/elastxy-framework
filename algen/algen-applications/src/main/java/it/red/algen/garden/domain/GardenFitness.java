/*
 * ExprFitness.java
 *
 * Created on 4 agosto 2007, 14.59
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden.domain;

import it.red.algen.engine.standard.StandardFitness;

/**
 *
 * @author grossi
 */
public class GardenFitness extends StandardFitness {
	public static final int FITNESS_WEIGHT_SUN = 5;
	public static final int FITNESS_WEIGHT_WIND = 3;
	public static final int FITNESS_WEIGHT_WET = 1;
	
}

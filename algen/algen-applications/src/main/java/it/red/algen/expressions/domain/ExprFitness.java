/*
 * ExprFitness.java
 *
 * Created on 4 agosto 2007, 14.59
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions.domain;

import it.red.algen.domain.Fitness;

/**
 * Normalized fitness in [0..1]
 * @author grossi
 */
public class ExprFitness implements Fitness {
    // Valore massimo: tutti i valori di fitness devono essere normalizzati a 1
    public static final double MAX = 1.0;
    public static final double APPROX = 0.000000001;
    private double _value;
    
    public ExprFitness(double value) {
        _value = value;
    }
    
    public double getValue(){
        return _value;
    }
    
    /** Essendo double occorre approssimare il valore
     */
    public boolean fit(){
        return Math.abs(MAX-_value) < APPROX;
    }
    
    public boolean greaterThan(Fitness other){
        return _value > ((ExprFitness)other)._value;
    }

	public boolean sameOf(Fitness other) {
        return Math.abs(((ExprFitness)other)._value -_value) < APPROX;
	}
}

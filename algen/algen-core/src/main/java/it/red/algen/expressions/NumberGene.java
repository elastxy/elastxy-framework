/*
 * ExpressionGene.java
 *
 * Created on 4 agosto 2007, 14.13
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions;

import it.red.algen.Gene;

/**
 *
 * @author grossi
 */
public class NumberGene implements Gene {
    private int _value;
    
    public NumberGene(int value) {
        _value = value;
    }
    
    public int getValue(){
        return _value;
    }
    
    public String toString(){
        return String.valueOf(_value);
    }
}

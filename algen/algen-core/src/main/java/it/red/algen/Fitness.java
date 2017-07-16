/*
 * Fitness.java
 *
 * Created on 4 agosto 2007, 13.50
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen;

/**
 *
 * @author grossi
 */
public interface Fitness {
    public boolean fit();
    
    public boolean greaterThan(Fitness other);
    
    public double getValue();

    public boolean sameOf(Fitness other);
}

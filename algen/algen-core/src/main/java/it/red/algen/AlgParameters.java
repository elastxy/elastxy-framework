/*
 * Parameters.java
 *
 * Created on 4 agosto 2007, 13.52
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen;

import org.springframework.stereotype.Component;

/**
 *
 * @author grossi
 */
public class AlgParameters {
    
	private double _recombinationPerc = Conf.DEFAULT_RECOMBINANTION_PERC;
    private double _mutationPerc = Conf.DEFAULT_MUTATION_PERC;
    private boolean _elitarism = Conf.DEFAULT_ELITARISM;
    
    public void init(double recombinationPerc, double mutationPerc, boolean elitarism){
        _recombinationPerc = recombinationPerc;
        _mutationPerc = mutationPerc;
        _elitarism = elitarism;
    }
    
    public double getRecombinationPerc(){
        return _recombinationPerc;
    }
    
    public double getMutationPerc(){
        return _mutationPerc;
    }
    
    public boolean getElitarism(){
    	return _elitarism;
    }
}

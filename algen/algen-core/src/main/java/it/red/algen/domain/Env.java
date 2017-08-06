/*
 * Env.java
 *
 * Created on 4 agosto 2007, 14.04
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain;

import java.util.ArrayList;
import java.util.List;

import it.red.algen.domain.interfaces.Target;

/** Ambiente in cui la popolazione evolve in base al target.
 *  
 * @author grossi
 */
public class Env {

    // DATI CORRENTI
	public Target<?> target;
    public Population currentGen;
    public int currentGenNumber = 0; // first generation starts from 0
    public int totIdenticalFitnesses = 0; // total of subsequent best matches with same fitness value
    
    // STORICO
    public List<Population> generationsHistory = new ArrayList<Population>();
    public long startTime;
    public long endTime;
    public boolean targetReached;
    
    public Env(Target<?> target, Population currentGen){
    	this.target = target;
    	this.currentGen = currentGen;
    }
        
}

/*
 * Env.java
 *
 * Created on 4 agosto 2007, 14.04
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain.experiment;

import java.util.ArrayList;
import java.util.List;


/** Environment where population evolves based on target
 *  
 * @author grossi
 */
public class Env {

    // LIVE DATA
	public Target<?,?> target;
    public Population currentGen;
    
    public int currentGenNumber = 0; // first generation starts from 0
    public long startTime;
    public long endTime;
    public boolean targetReached;
    public int totIdenticalFitnesses = 0; // number of sequential best population fitness value
    
    // HISTORY
    // TODOM: move to another entity EnvHistory, Tracking...
    public List<Population> generationsHistory = new ArrayList<Population>();
    
    public Env(Target<?,?> target, Population currentGen){
    	this.target = target;
    	this.currentGen = currentGen;
    }
        
}
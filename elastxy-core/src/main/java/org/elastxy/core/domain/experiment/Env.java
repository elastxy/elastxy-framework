/*
 * Env.java
 *
 * Created on 4 agosto 2007, 14.04
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.core.domain.experiment;

import java.util.ArrayList;
import java.util.List;

import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.genetics.Genoma;


/** Environment where population evolves based on target.
 * 
 * It maintains a reference to genoma, current population, 
 * target status, statistic data on evolution.
 *  
 * @author grossi
 */
public class Env {

    // LIVE DATA
	public Target<?,?> target;
	
	public int lastGenNumber = 0; // first generation starts from 0
    public Population lastGen;
    
    public Genoma genoma;

	/**
	 * Represents the reference to data algorithm is working on, 
	 * which must not be maintained in solutions because it is too much expensive.
	 */
    public WorkingDataset workingDataset;

    public long startTime;
    public long endTime;
    public long totalLifeTime;
    public boolean targetReached;
    public int totIdenticalFitnesses = 0; // number of sequential best population fitness value
    
    // HISTORY
    // TODO2-4: move to another entity/service, such as EnvHistory, Tracking...
    public List<Population> generationsHistory = new ArrayList<Population>();
    
    public Env(Target<?,?> target, Population currentGen, Genoma genoma, WorkingDataset workingDataset){
    	this.target = target;
    	this.lastGen = currentGen;
    	this.genoma = genoma;
    	this.workingDataset = workingDataset;
    }
        
    
    public String toString(){
    	return String.format("Env [Target: %s, GenNumber: %d, Identical Fit: %d]", target, lastGenNumber, totIdenticalFitnesses);
    }
    
}

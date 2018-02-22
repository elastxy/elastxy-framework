/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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

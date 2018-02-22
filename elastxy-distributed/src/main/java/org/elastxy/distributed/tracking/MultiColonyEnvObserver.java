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
package org.elastxy.distributed.tracking;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.EnvObserver;

/**
 * Observer of Multicolony related events.
 * 
 * TODO3-1: add meaningful events..
 * TODO2-2: hierarchy not good: detach MultiColony EnvObserver/Observable/Evolver from Single Colony ones.
 * TODO0-4: add comments with disclaimer and author on every source.
 * @author red
 *
 */
public class MultiColonyEnvObserver extends EnvObserver {
    
    public MultiColonyEnvObserver(AlgorithmContext context){
    	super(context);
    }
    
    
    public void evolutionStartedEvent(){
    	context.monitoringConfiguration.logger.out("Evolution started");
    }
    
    public void eraStartedEvent(long eraNumber){
    	context.monitoringConfiguration.logger.out("Era "+eraNumber+" started");
    }
    
    public void eraEndedEvent(long eraNumber){
    	context.monitoringConfiguration.logger.out("Era "+eraNumber+" ended");
    }
    
    // TODO2-2: ResultsRenderer: reuse the ResultsRenderer
    public void targetReachedEvent(ExperimentStats stats){
    	context.monitoringConfiguration.logger.out("Goal reached! \nStats:"+stats);
    }
    
    public void reshuffleEvent(long eraNumber){
    	context.monitoringConfiguration.logger.out("Reshuffle after era "+eraNumber);
    }
    
    public void evolutionEndedEvent(boolean targetReached, int totIdenticalFitnesses){
    	if(context.monitoringConfiguration.verbose) context.monitoringConfiguration.logger.out("Evolution ended. Target reached: "+targetReached+". Tot identical fitnesses: "+totIdenticalFitnesses);
    }

}

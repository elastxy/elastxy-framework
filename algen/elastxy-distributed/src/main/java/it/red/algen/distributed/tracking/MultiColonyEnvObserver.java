package it.red.algen.distributed.tracking;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.stats.ExperimentStats;
import it.red.algen.tracking.EnvObserver;

/**
 * Observer of Multicolony related events.
 * 
 * TODOM-1: add meaningful events..
 * TODOA-2: hierarchy not good: detach MultiColony EnvObserver/Observable/Evolver from Single Colony ones.
 * TODOA-4: add comments with disclaimer and author on every source.
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
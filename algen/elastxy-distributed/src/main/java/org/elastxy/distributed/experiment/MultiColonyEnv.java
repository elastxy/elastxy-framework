package org.elastxy.distributed.experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.util.LongAccumulator;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.distributed.dataprovider.BroadcastWorkingDataset;


/**
 * Holds distributed environment evolution info, such as eras, broadcast and accumulator variable.
 * 
 * @author red
 *
 */
public class MultiColonyEnv {
    public static final String ACCUMULATOR_NAME = "MultiColonyGoalAccumulator";

    // CONFIGURATIONS
	public Target<?,?> target;
    public Genoma genoma;

    // LIVE DATA
    public long currentEraNumber = 0L; // first Era starts from 0
    public long currentEonNumber = 0L; // first Eon starts from 0
    public List<Solution> eraBestMatches = new ArrayList<Solution>();
    public List<Solution> allBestMatches = new ArrayList<Solution>();

    public long startTime;
    public long endTime;
    public boolean targetReached;
    public int totIdenticalFitnesses = 0; // number of sequential best era fitness value

    // DATA SHARED BETWEEN COLONIES
    public Optional<LongAccumulator> goalAccumulator = Optional.empty();
    public Optional<Broadcast<List<Allele>>> mutationGenesBroadcast = Optional.empty();
    public Optional<Broadcast<List<Solution>>> previousBestMatchesBroadcast = Optional.empty();
    public Map<String, BroadcastWorkingDataset> broadcastWorkingDatasets = new HashMap<String, BroadcastWorkingDataset>();
    
    public JavaRDD<Solution> bestMatchesRDD = null;

    // HISTORY
    // TODO3-2: eras history
//    public List<Population> generationsHistory = new ArrayList<Population>();
    
    public MultiColonyEnv(Target<?,?> target, Genoma genoma, Map<String, BroadcastWorkingDataset> broadcastWorkingDatasets){
    	this.target = target;
    	this.genoma = genoma;
    	this.broadcastWorkingDatasets = broadcastWorkingDatasets;
    }
        
    public String toString(){
    	// TODO3-2: evaluate a specific target builder in distributed environment to set overall goals?
//    	return String.format("MultiColonyEnv [Target: %s, EraNumber: %d, Identical Fit: %d]", target, currentEraNumber, totIdenticalFitnesses);
    	return String.format("MultiColonyEnv [EraNumber: %d, Identical Fit: %d]", currentEraNumber, totIdenticalFitnesses);
    }
    
}


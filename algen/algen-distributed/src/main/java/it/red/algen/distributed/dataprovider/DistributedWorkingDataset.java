package it.red.algen.distributed.dataprovider;

import it.red.algen.dataprovider.WorkingDataset;

/**
 * Marker interface for distinguish distributed from local datasets.
 * 
 * In case of distributed dataset (RDD), it holds the reference 
 * of the original RDD within the Driver program.
 * 
 * @author red
 */
public interface DistributedWorkingDataset extends WorkingDataset {

}

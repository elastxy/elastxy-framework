package org.elastxy.distributed.dataprovider;

import org.elastxy.core.dataprovider.WorkingDataset;

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

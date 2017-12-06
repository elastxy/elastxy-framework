package it.red.algen.distributed.dataprovider;

import java.util.Map;

import it.red.algen.dataprovider.DatasetProvider;
import it.red.algen.dataprovider.WorkingDataset;

/**
 * DatasetProvider implementation capable of collecting
 * raw input data into an RDD and random repartitioning
 * (coalesce) data across nodes.
 * 
 * TODOD: input data in streaming
 *  
 * After this operation, genetic material is shared
 * by spreading Genoma either (within same partition),
 * in a normal evolution.
 * 
 * It also offer capability to broadcast and maintain
 * reference to broadcasted set of data used in the nodes to operate.
 * E.g. the garden database.
 * 
 * @author red
 *
 */
public interface DistributedDatasetProvider extends DatasetProvider {

	/**
	 * Redistributed data across colonies (nodes of a cluster).
	 * 
	 * It's a needed pre-requisite for spreading genetic material
	 * around to increment diversity and improving global fitness.
	 */
	public void redistribute();
	
	/**
	 * Create broadcast datasets for spreading any needed data 
	 * across nodes, and assign them to a distributed dataset.
	 * 
	 */
	public void broadcast();
	
	/**
	 * Returns the datasets created by broadcasting data to colonies.
	 * @return
	 */
	public Map<String, BroadcastWorkingDataset> getBroadcastDatasets();
}

package it.red.algen.distributed.dataprovider;

import it.red.algen.dataprovider.DatasetProvider;

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
	
}

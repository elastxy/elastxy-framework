package it.red.algen.distributed.dataprovider;

import java.io.Serializable;

import org.apache.spark.broadcast.Broadcast;

public class BroadcastWorkingDataset<D> implements DistributedWorkingDataset, Serializable {
	private static final long serialVersionUID = -7770144306600513219L;
	
	public Broadcast<D> bc;
}

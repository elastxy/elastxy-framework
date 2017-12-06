package it.red.algen.distributed.dataprovider;

import java.io.Serializable;

import org.apache.spark.broadcast.Broadcast;

import it.red.algen.dataprovider.WorkingDataset;

public class BroadcastWorkingDataset<D> implements WorkingDataset, Serializable {
	public Broadcast<D> bc;
}

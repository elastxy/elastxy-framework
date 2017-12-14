package org.elastxy.distributed.dataprovider;

import java.util.Map;

import org.elastxy.core.dataprovider.DatasetProvider;

/**
 * This DatasetProvider is used locally to retrieve
 * the broadcasted variable and fill the local WorkingDataset.
 * 
 * The setup is done during context intialization in the closure,
 * based on broadcast variables eventually passed.
 * 
 * @author red
 */
public interface BroadcastedDatasetProvider extends DatasetProvider {
	public void setBroadcastDatasets(Map<String, BroadcastWorkingDataset> broadcastDatasets);
}

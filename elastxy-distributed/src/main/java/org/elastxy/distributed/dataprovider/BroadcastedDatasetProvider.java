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

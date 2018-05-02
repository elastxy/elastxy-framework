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
package ${groupId}.multicolonyxo;

import org.apache.log4j.Logger;
import org.elastxy.core.conf.ReadConfigSupport;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.GenomaProvider;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.engine.metadata.GenesMetadataConfiguration;
import org.elastxy.core.engine.metadata.MetadataGenomaBuilder;
import org.elastxy.core.engine.metadata.StandardMetadataGenoma;


public class AppGenomaProvider implements GenomaProvider {
	private static Logger logger = Logger.getLogger(AppGenomaProvider.class);

	private StandardMetadataGenoma cachedGenoma;

	private AlgorithmContext context;

	@Override
	public void setup(AlgorithmContext context) {
		this.context = context;
	}
	
	@Override
	public void setWorkingDataset(WorkingDataset workingDataset) {
	}

	@Override
	public Genoma getGenoma(){
		return cachedGenoma;
	}

	@Override
	public void collect() {
		
		// Retrieves metadata
		GenesMetadataConfiguration genes = ReadConfigSupport.retrieveGenesMetadata(context.application.appName);
		
		// Add context specific values
		Long maxValue = context.applicationSpecifics.getParamLong(AppConstants.MAX_OPERAND_VALUE);
		genes.metadata.get("operand").max = maxValue;
		genes.metadata.get("operand").min = -1L * maxValue;

		// Initialize Genoma
		StandardMetadataGenoma genoma = MetadataGenomaBuilder.build(context, genes);
		cachedGenoma = genoma;
	}
	
	/**
	 * Does nothing: returns the whole genoma
	 */
	@Override
	public Genoma shrink(Target<?, ?> target) {
		return cachedGenoma;
	}

}

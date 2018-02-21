package org.elastxy.app.metaexpressions;

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


/**
 * TODO2-2: cache!
 * @author red
 *
 */
public class MexGenomaProvider implements GenomaProvider {
	private static Logger logger = Logger.getLogger(MexGenomaProvider.class);

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
		Long maxValue = context.applicationSpecifics.getParamLong(MexConstants.MAX_OPERAND_VALUE);
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

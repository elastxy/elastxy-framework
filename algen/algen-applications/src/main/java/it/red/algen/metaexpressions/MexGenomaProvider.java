package it.red.algen.metaexpressions;

import org.apache.log4j.Logger;

import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.GenomaProvider;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.engine.metadata.GenesMetadataConfiguration;
import it.red.algen.engine.metadata.MetadataGenomaBuilder;
import it.red.algen.engine.metadata.StandardMetadataGenoma;


/**
 * TODOM: cache!
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
		GenesMetadataConfiguration genes = ReadConfigSupport.retrieveGenesMetadata(context.application.name);
		
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

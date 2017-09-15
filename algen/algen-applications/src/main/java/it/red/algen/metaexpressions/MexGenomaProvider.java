package it.red.algen.metaexpressions;

import org.apache.log4j.Logger;

import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.metadata.Genes;
import it.red.algen.metadata.StandardMetadataGenoma;


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
	public Genoma getGenoma(){
		return cachedGenoma;
	}

	
	
	@Override
	public void collect() {
		
		// Instantiate Genoma
		StandardMetadataGenoma genoma = new StandardMetadataGenoma();
		genoma.setupAlleleGenerator(context.application.alleleGenerator);
		
		// Retrieves metadata
		Genes genes = ReadConfigSupport.retrieveGenesMetadata(context.application.name);
		
		// Add context specific values
		Long maxValue = context.applicationSpecifics.getParamLong(MexConstants.MAX_OPERAND_VALUE);
		genes.metadata.get("operand").max = maxValue;
		genes.metadata.get("operand").min = -1L * maxValue;

		// Initialize Genoma
		genoma.initialize(genes);
		cachedGenoma = genoma;
	}

	
	/**
	 * Does nothing: returns the whole genoma
	 * TODOA: reduceable interface....
	 */
	@Override
	public Genoma shrink(Target<?, ?> target) {
		return cachedGenoma;
	}



}

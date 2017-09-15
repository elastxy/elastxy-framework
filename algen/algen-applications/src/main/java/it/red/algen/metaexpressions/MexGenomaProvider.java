package it.red.algen.metaexpressions;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.ContextSupplier;
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
@Component
public class MexGenomaProvider implements GenomaProvider {
	private static Logger logger = Logger.getLogger(MexGenomaProvider.class);

	@Autowired private ContextSupplier contextSupplier;

//	public void setAlleleGenerator(AlleleGenerator alleleGenerator){
//		this.alleleGenerator = alleleGenerator;
//	}
	
	private StandardMetadataGenoma cachedGenoma;
	
	@Override
	public Genoma getGenoma(){
		return cachedGenoma;
	}

	
	
	@Override
	public void collect() {
		
		// Instantiate Genoma
		StandardMetadataGenoma genoma = new StandardMetadataGenoma();
		genoma.setupAlleleGenerator(contextSupplier.getContext().application.alleleGenerator);
		
		// Retrieves metadata
		Genes genes = ReadConfigSupport.retrieveGenesMetadata(this.contextSupplier.getContext().application.name);
		
		// Add context specific values
		Long maxValue = contextSupplier.getContext().applicationSpecifics.getParamLong(MexConstants.MAX_OPERAND_VALUE);
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

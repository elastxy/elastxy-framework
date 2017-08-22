package it.red.algen.metaexpressions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.conf.ConfigurationException;
import it.red.algen.conf.ReadConfigSupport;
import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.metadata.GeneMetadata;
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

	@Autowired private MexAlleleGenerator alleleGenerator;
	
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
		genoma.setupAlleleGenerator(alleleGenerator);
		
		// Retrieves metadata
		Genes genes = retrieveGenesMetadata();
		
		// Add context specific values
		Long maxValue = contextSupplier.getContext().applicationSpecifics.getParamLong(MexApplication.MAX_OPERAND_VALUE);
		genes.metadata.get("operand").max = maxValue;
		genes.metadata.get("operand").min = -1L * maxValue;

		// Initialize Genoma
		genoma.initialize(genes);
		cachedGenoma = genoma;
	}



	private Genes retrieveGenesMetadata() {
		Genes genes;
		String classpathResource = "/"+this.contextSupplier.getContext().application.name+"/genes.json";
		try {
			genes = (Genes)ReadConfigSupport.readJSON(classpathResource, Genes.class);
		} catch (IOException e) {
			String msg = "Error while getting classpath resource "+classpathResource+". Ex: "+e;
			logger.error(msg, e);
			throw new ConfigurationException(msg, e);
		}
		return genes;
	}

	
	/**
	 * Does nothing: returns the whole genoma
	 * TODOA: reduceable interface....
	 */
	@Override
	public Genoma reduce(Target<?, ?> target) {
		// Does nothing
		return getGenoma();
	}

}

package it.red.algen.metaexpressions;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.engine.AlleleGenerator;
import it.red.algen.metadata.GeneMetadata;
import it.red.algen.metadata.GeneMetadataType;
import it.red.algen.metadata.Genoma;
import it.red.algen.metadata.MetadataBasedGenoma;


/**
 * TODOA: cache!
 * @author red
 *
 */
@Component
public class MexGenomaProvider implements GenomaProvider {

	@Autowired
	private ContextSupplier contextSupplier;

	@Autowired
	private AlleleGenerator alleleGenerator;
	
	private Genoma cachedGenoma;
	
	@Override
	public Genoma getGenoma(){
		return cachedGenoma;
	}

	@Override
	public Genoma collect() {
		if(cachedGenoma!=null){
			return cachedGenoma;
		}
		MetadataBasedGenoma cachedGenoma = new MetadataBasedGenoma();
		cachedGenoma.setupAlleleGenerator(alleleGenerator);
		cachedGenoma.genesMetadataByCode = new HashMap<String, GeneMetadata>();

		GeneMetadata metadata = new GeneMetadata();
		metadata.code = "operand";
		metadata.name = "operand";
		metadata.type = GeneMetadataType.INTEGER;
		Long maxValue = contextSupplier.getContext().applicationSpecifics.getParamLong(MexApplication.MAX_OPERAND_VALUE);
		metadata.max = maxValue;
		metadata.min = -1L * maxValue;
		cachedGenoma.genesMetadataByCode.put(metadata.code, metadata);
		cachedGenoma.genesMetadataByPos.put("0", metadata);
		cachedGenoma.genesMetadataByPos.put("2", metadata);
		
		metadata = new GeneMetadata();
		metadata.code = "operator";
		metadata.name = "operator";
		metadata.type = GeneMetadataType.CHAR;
		metadata.values = Arrays.asList('+', '-', '*', '/');
		cachedGenoma.genesMetadataByCode.put(metadata.code, metadata);
		cachedGenoma.genesMetadataByPos.put("1", metadata);
		
		return cachedGenoma;
	}

}

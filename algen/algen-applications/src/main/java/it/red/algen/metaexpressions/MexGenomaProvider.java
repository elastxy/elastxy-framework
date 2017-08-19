package it.red.algen.metaexpressions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.ContextSupplier;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.metadata.GeneMetadata;
import it.red.algen.metadata.GeneMetadataType;
import it.red.algen.metadata.StandardMetadataGenoma;


/**
 * TODOA: cache!
 * @author red
 *
 */
@Component
public class MexGenomaProvider implements GenomaProvider {

	@Autowired private ContextSupplier contextSupplier;

	@Autowired private MexAlleleGenerator alleleGenerator;
	
	private StandardMetadataGenoma cachedGenoma;
	
	@Override
	public Genoma getGenoma(){
		return cachedGenoma;
	}

	@Override
	public void collect() {
		StandardMetadataGenoma genoma = new StandardMetadataGenoma();
		genoma.setupAlleleGenerator(alleleGenerator);
		Map<String, GeneMetadata> genesMetadataByCode = new HashMap<String, GeneMetadata>();
		Map<String, GeneMetadata> genesMetadataByPos = new HashMap<String, GeneMetadata>();

		GeneMetadata metadata = new GeneMetadata();
		metadata.code = "operand";
		metadata.name = "operand";
		metadata.type = GeneMetadataType.INTEGER;
		Long maxValue = contextSupplier.getContext().applicationSpecifics.getParamLong(MexApplication.MAX_OPERAND_VALUE);
		metadata.max = maxValue;
		metadata.min = -1L * maxValue;
		genesMetadataByCode.put(metadata.code, metadata);
		genesMetadataByPos.put("0", metadata);
		genesMetadataByPos.put("2", metadata);
		
		metadata = new GeneMetadata();
		metadata.code = "operator";
		metadata.name = "operator";
		metadata.type = GeneMetadataType.CHAR;
		metadata.values = Arrays.asList('+', '-', '*', '/');
		genesMetadataByCode.put(metadata.code, metadata);
		genesMetadataByPos.put("1", metadata);
		
		genoma.initialize(genesMetadataByCode, genesMetadataByPos);
		
		cachedGenoma = genoma;
	}

	
	/**
	 * TODOA: reduceable interface....
	 */
	@Override
	public Genoma reduce(Target<?, ?> target) {
		throw new UnsupportedOperationException("Not available for this GenomaProvider implementation: all Genoma already collected with collect()");
	}

}

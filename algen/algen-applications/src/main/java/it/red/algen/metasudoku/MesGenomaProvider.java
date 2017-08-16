package it.red.algen.metasudoku;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

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
 * 
 * Positions in sudoku matrix are: 
 * 	first row: 	0 to 8 (incl.)
 *  second row: 9 to 17 (incl.)
 *  ..
 *  ninth row: 72 to 80 (incl.)
 *  
 * TODOA: cache!
 * @author red
 *
 */
@Component
public class MesGenomaProvider implements GenomaProvider {

	@Autowired
	private ContextSupplier contextSupplier;

	@Autowired
	@Resource(name="mesAlleleGenerator")
	private AlleleGenerator alleleGenerator;
	
	private MetadataBasedGenoma cachedGenoma;
	
	@Override
	public Genoma getGenoma(){
		if(cachedGenoma==null){
			collect();
		}
		return cachedGenoma;
	}

	@Override
	public Genoma collect() {
		cachedGenoma = new MetadataBasedGenoma();
		cachedGenoma.setupAlleleGenerator(alleleGenerator);
		cachedGenoma.genesMetadataByCode = new HashMap<String, GeneMetadata>();

		GeneMetadata metadata = new GeneMetadata();
		metadata.code = "cell";
		metadata.name = "cell";
		metadata.type = GeneMetadataType.INTEGER;
		metadata.values = IntStream.rangeClosed(0, 9).boxed().map(i -> i).collect(Collectors.toList());
		cachedGenoma.genesMetadataByCode.put(metadata.code, metadata);
		for(int cell=0; cell < 81; cell++){
			cachedGenoma.genesMetadataByPos.put(String.valueOf(cell), metadata);
		}
		
		
		return cachedGenoma;
	}

}

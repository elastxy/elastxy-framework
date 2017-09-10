package it.red.algen.metagarden;

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
import it.red.algen.metagarden.data.GardenDatabase;
import it.red.algen.metagarden.data.GardenDatabaseCSV;
import it.red.algen.metagarden.data.Place;
import it.red.algen.metagarden.data.PlaceProperty;


/**
 * TODOM: cache!
 * @author red
 *
 */
@Component
public class MegGenomaProvider implements GenomaProvider {

	@Autowired private ContextSupplier contextSupplier;

	@Autowired private MegAlleleGenerator alleleGenerator;

//	private GardenDatabase db = new GardenDatabaseInMemory();
	private GardenDatabase db = new GardenDatabaseCSV();

	private StandardMetadataGenoma cachedGenoma;

	
//	@Cacheable(value = "genoma") TODOM: cache
	@Override
	public Genoma getGenoma(){
		return cachedGenoma;
	}
	
	
//	@Cacheable(value = "genoma")
	@Override
	public void collect() {
		StandardMetadataGenoma genoma = new StandardMetadataGenoma();
		genoma.setupAlleleGenerator(alleleGenerator);
		genoma.setLimitedAllelesStrategy(contextSupplier.getContext().applicationSpecifics.getParamBoolean(MegApplication.LIMITED_TREES));
		Map<String, GeneMetadata> genesMetadataByCode = new HashMap<String, GeneMetadata>();
		Map<String, GeneMetadata> genesMetadataByPos = new HashMap<String, GeneMetadata>();

		Place[] places = db.getAllPlaces();
		
		for(int pos=0; pos < places.length; pos++){
			Place place = places[pos];
		
			GeneMetadata metadata = new GeneMetadata();
			metadata.code = "place"+pos;
			metadata.name = "Location "+metadata.code+" in "+place.zone;
			
			metadata.type = GeneMetadataType.USER;
			// TODOM: generic properties in Place
			metadata.userProperties.put(PlaceProperty.SUN_EXPOSURE.name(), 	place.getSunExposure());
			metadata.userProperties.put(PlaceProperty.WET_LEVEL.name(), 	place.getWetLevel());
			metadata.userProperties.put(PlaceProperty.WIND_LEVEL.name(), 	place.getWindLevel());
			
			metadata.values = Arrays.asList(db.getAllTrees());
			
			genesMetadataByCode.put(metadata.code, metadata);
			genesMetadataByPos.put(String.valueOf(pos), metadata);
		}
		genoma.initialize(genesMetadataByCode, genesMetadataByPos);
		
		cachedGenoma = genoma;
	}

	
	/**
	 * Does nothing: returns the genoma as is
	 */
	@Override
	public Genoma shrink(Target<?, ?> target) {
		return cachedGenoma;
	}

}

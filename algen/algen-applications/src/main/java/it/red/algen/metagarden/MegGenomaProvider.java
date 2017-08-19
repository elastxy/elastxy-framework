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
 * TODOA: cache!
 * @author red
 *
 */
@Component
public class MegGenomaProvider implements GenomaProvider {

	@Autowired private ContextSupplier contextSupplier;

	@Autowired private MegAlleleGenerator alleleGenerator;

//	private GardenDatabase db = new GardenDatabaseInMemory();
	private GardenDatabase db = new GardenDatabaseCSV(MegApplication.DATABASE_DIR);

	private StandardMetadataGenoma cachedGenoma;

//	@Cacheable(value = "genoma") TODOA: cache
	@Override
	public Genoma getGenoma(){
		if(cachedGenoma==null){
			collect();
		}
		return cachedGenoma;
	}
	
//	@Cacheable(value = "genoma")
	@Override
	public Genoma collect() {
		cachedGenoma = new StandardMetadataGenoma();
		cachedGenoma.setupAlleleGenerator(alleleGenerator);
		cachedGenoma.setLimitedAllelesStrategy(contextSupplier.getContext().applicationSpecifics.getParamBoolean(MegApplication.LIMITED_TREES));
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

			cachedGenoma.initialize(genesMetadataByCode, genesMetadataByPos);

		}
		
		this.cachedGenoma = cachedGenoma;
		return cachedGenoma;
	}

	@Override
	public void reduce(Target<?, ?> target) {
		throw new UnsupportedOperationException("Not available for this GenomaProvider implementation");
	}

}

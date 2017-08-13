package it.red.algen.metagarden;

import java.util.Arrays;
import java.util.HashMap;

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
import it.red.algen.metagarden.data.GardenDatabase;
import it.red.algen.metagarden.data.GardenDatabaseCSV;
import it.red.algen.metagarden.data.GardenDatabaseInMemory;
import it.red.algen.metagarden.data.Place;
import it.red.algen.metagarden.data.PlaceProperty;


/**
 * TODOA: cache!
 * @author red
 *
 */
@Component
public class MegGenomaProvider implements GenomaProvider {
	private GardenDatabase db = new GardenDatabaseCSV(MegApplication.DATABASE_DIR);

	@Autowired
	private ContextSupplier contextSupplier;

	private Genoma cachedGenoma;
	
	@Autowired
	@Resource(name="megAlleleGenerator")
	private AlleleGenerator alleleGenerator;

	
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
		cachedGenoma.limitedAllelesStrategy = contextSupplier.getContext().applicationSpecifics.getParamBoolean(MegApplication.LIMITED_TREES);
		cachedGenoma.genesMetadataByCode = new HashMap<String, GeneMetadata>();

//		GardenDatabaseInMemory db = new GardenDatabaseInMemory();
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
			
			cachedGenoma.genesMetadataByCode.put(metadata.code, metadata);
			cachedGenoma.genesMetadataByPos.put(String.valueOf(pos), metadata);
		}
		
		return cachedGenoma;
	}

}

package it.red.algen.metagarden;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.GenomaProvider;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.engine.metadata.GeneMetadata;
import it.red.algen.engine.metadata.GeneMetadataType;
import it.red.algen.engine.metadata.MetadataGenomaBuilder;
import it.red.algen.engine.metadata.StandardMetadataGenoma;
import it.red.algen.metagarden.data.MegWorkingDataset;
import it.red.algen.metagarden.data.Place;
import it.red.algen.metagarden.data.PlaceProperty;


/**
 * TODOM: cache!
 * @author red
 *
 */
public class MegGenomaProvider implements GenomaProvider {
	
	private StandardMetadataGenoma cachedGenoma;

	private AlgorithmContext context;
	
	private MegWorkingDataset workingDataset;

	@Override
	public void setup(AlgorithmContext context) {
		this.context = context;
	}


	@Override
	public void setWorkingDataset(WorkingDataset workingDataset) {
		this.workingDataset = (MegWorkingDataset)workingDataset;
	}
	
//	@Cacheable(value = "genoma") TODOM: cache
	@Override
	public Genoma getGenoma(){
		return cachedGenoma;
	}
	
	
//	@Cacheable(value = "genoma")
	@Override
	public void collect() {
		
		StandardMetadataGenoma genoma = MetadataGenomaBuilder.create(
				context,
				context.applicationSpecifics.getParamBoolean(MegConstants.LIMITED_TREES));
		
		for(int pos=0; pos < workingDataset.places.length; pos++){
			Place place = workingDataset.places[pos];
		
			GeneMetadata metadata = new GeneMetadata();
			metadata.code = "place"+pos;
			metadata.name = "Location "+metadata.code+" in "+place.zone;
			
			metadata.type = GeneMetadataType.USER;
			// TODOM: generic properties in Place
			metadata.userProperties.put(PlaceProperty.SUN_EXPOSURE.name(), 	place.getSunExposure());
			metadata.userProperties.put(PlaceProperty.WET_LEVEL.name(), 	place.getWetLevel());
			metadata.userProperties.put(PlaceProperty.WIND_LEVEL.name(), 	place.getWindLevel());
			
			metadata.values = Arrays.asList(workingDataset.trees);
			
			MetadataGenomaBuilder.addGene(genoma, String.valueOf(pos), metadata); // TODOM: rework to a GenePosition to represent position
		}

		MetadataGenomaBuilder.finalize(genoma);
		
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

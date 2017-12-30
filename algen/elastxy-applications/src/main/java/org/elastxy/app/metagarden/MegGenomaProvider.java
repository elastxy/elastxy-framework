package org.elastxy.app.metagarden;

import java.util.Arrays;

import org.elastxy.app.metagarden.data.MegWorkingDataset;
import org.elastxy.app.metagarden.data.Place;
import org.elastxy.app.metagarden.data.PlaceProperty;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.GenomaProvider;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.engine.metadata.GeneMetadata;
import org.elastxy.core.engine.metadata.GeneMetadataType;
import org.elastxy.core.engine.metadata.MetadataGenomaBuilder;
import org.elastxy.core.engine.metadata.StandardMetadataGenoma;


/**
 * TODO2-2: cache!
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
	
//	@Cacheable(value = "genoma")  // TODO2-2: cache!
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
			// TODO2-4: generic properties in Place to allow different kind of analysis
			metadata.userProperties.put(PlaceProperty.SUN_EXPOSURE.name(), 	place.getSunExposure());
			metadata.userProperties.put(PlaceProperty.WET_LEVEL.name(), 	place.getWetLevel());
			metadata.userProperties.put(PlaceProperty.WIND_LEVEL.name(), 	place.getWindLevel());
			
			metadata.values = Arrays.asList(workingDataset.trees);
			
			// TODO3-4: rework to a GenePosition to represent position
			MetadataGenomaBuilder.addGene(genoma, String.valueOf(pos), metadata);
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

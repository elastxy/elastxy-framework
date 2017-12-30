package org.elastxy.app.metagarden;

import org.elastxy.app.metagarden.data.GardenWellness;
import org.elastxy.app.metagarden.data.PlaceProperty;
import org.elastxy.app.metagarden.data.Tree;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.domain.genetics.phenotype.UserPhenotype;
import org.elastxy.core.engine.fitness.Incubator;
import org.elastxy.core.engine.metadata.GeneMetadata;
import org.elastxy.core.engine.metadata.MetadataGenoma;

public class MegIncubator implements Incubator<Chromosome, UserPhenotype<GardenWellness>>{
	private static final double FITNESS_WEIGHT_SUN = 	5.0;
	private static final double FITNESS_WEIGHT_WIND = 	3.0;
	private static final double FITNESS_WEIGHT_WET = 	1.0;

	/**
	 * Solution grows to a big Garden with its values of wellness, in the interval [0..2]
	 */
	@Override
	public UserPhenotype<GardenWellness> grow(Chromosome genotype, Env environment) {
		UserPhenotype<GardenWellness> result = new UserPhenotype<GardenWellness>();
		result.value = new GardenWellness();
		
		MetadataGenoma genoma = (MetadataGenoma)environment.genoma;
		for(Gene gene : genotype.genes){
			GeneMetadata metadata = genoma.getMetadataByCode(gene.metadataCode);
			result.value.locationsUnhappyness.add(calculateUnhappiness(gene, metadata));
		}
		return result;
	}
	
	
	/**
	 * Calculates plant unhappiness.
	 * 
	 * Distance from the optimal situation, based on importance of the criteria.
	 * 
	 * 0: Optimal
	 * 1: Worst
	 * 2: Plant is dead
	 * 
	 * @return
	 */
	public Double calculateUnhappiness(Gene gene, GeneMetadata metadata){
		double unhappiness = 0;
		boolean dead = false;
		
		// TODO3-1: meg: remove redundancy
		
		// distanza della richiesta dalla pianta alla fornita dal posto
		{
		int sunExposure = (int)metadata.userProperties.get(PlaceProperty.SUN_EXPOSURE.name());
		int sunRequest = ((Tree)gene.allele.value).getSunRequest();
		int sunRequestDifference = Math.abs(sunExposure-sunRequest);
		dead = sunRequestDifference==2;
		unhappiness += sunRequestDifference * FITNESS_WEIGHT_SUN;
		}
		
		{
		// umidita' in eccesso rispetto a quella accettata dalla pianta
		int wetLevel = (int)metadata.userProperties.get(PlaceProperty.WET_LEVEL.name());
		int wetAllowed = ((Tree)gene.allele.value).getWetAllowed();
		int wetRequestDifference = Math.abs(wetLevel - wetAllowed);
//		dead |= wetRequestDifference==2;
		unhappiness += wetRequestDifference * FITNESS_WEIGHT_WET;
		}
		
		{
		// vento in eccesso rispetto a quello ammesso dalla pianta
		int windLevel = (int)metadata.userProperties.get(PlaceProperty.WIND_LEVEL.name());
		int windAllowed = ((Tree)gene.allele.value).getWindAllowed();
		int windRequestDifference = Math.abs(windLevel - windAllowed);
//		dead |= windRequestDifference==2;
		unhappiness += windRequestDifference * FITNESS_WEIGHT_WIND;
		}
		
		// weights unhappiness based on criteria weights
		unhappiness = unhappiness / (FITNESS_WEIGHT_SUN+FITNESS_WEIGHT_WET+FITNESS_WEIGHT_WIND);
		
		// wellness is derived from death of the plant or from the unhappiness
		return dead ? 2.0 : unhappiness;
	}


}

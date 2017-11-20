package it.red.algen.metagarden;

import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.domain.genetics.genotype.Gene;
import it.red.algen.domain.genetics.phenotype.UserPhenotype;
import it.red.algen.engine.fitness.Incubator;
import it.red.algen.engine.metadata.GeneMetadata;
import it.red.algen.engine.metadata.MetadataGenoma;
import it.red.algen.metagarden.data.GardenWellness;
import it.red.algen.metagarden.data.PlaceProperty;
import it.red.algen.metagarden.data.Tree;

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
		
		// TODOM: remove redundancy
		
		// distanza della richiesta dalla pianta alla fornita dal posto
		int sunExposure = (int)metadata.userProperties.get(PlaceProperty.SUN_EXPOSURE.name());
		int sunRequest = ((Tree)gene.allele.value).getSunRequest();
		int sunRequestDifference = Math.abs(sunExposure-sunRequest);
		boolean dead = sunRequestDifference==2;
		unhappiness += sunRequestDifference * FITNESS_WEIGHT_SUN;
		
		
		// umidita' in eccesso rispetto a quella accettata dalla pianta
		int wetLevel = (int)metadata.userProperties.get(PlaceProperty.WET_LEVEL.name());
		int wetAllowed = ((Tree)gene.allele.value).getWetAllowed();
		int wetRequestDifference = Math.abs(wetLevel - wetAllowed);
//		dead |= wetRequestDifference==2;
		unhappiness += wetRequestDifference * FITNESS_WEIGHT_WET;
		
		// vento in eccesso rispetto a quello ammesso dalla pianta
		int windLevel = (int)metadata.userProperties.get(PlaceProperty.WIND_LEVEL.name());
		int windAllowed = ((Tree)gene.allele.value).getWindAllowed();
		int windRequestDifference = Math.abs(windLevel - windAllowed);
//		dead |= windRequestDifference==2;
		unhappiness += windRequestDifference * FITNESS_WEIGHT_WIND;
		
		// weights unhappiness based on criteria weights
		unhappiness = unhappiness / (FITNESS_WEIGHT_SUN+FITNESS_WEIGHT_WET+FITNESS_WEIGHT_WIND);
		
		// wellness is derived from death of the plant or from the unhappiness
		return dead ? 2.0 : unhappiness;
	}


}

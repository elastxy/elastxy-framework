package it.red.algen.garden.domain;

import it.red.algen.garden.conf.GardenConf;

public class PlacementGene {
	private Place place;
	private Tree tree;

	public PlacementGene(Place place, Tree tree) {
		super();
		this.place = place;
		this.tree = tree;
	}

	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	public Tree getTree() {
		return tree;
	}
	public void setTree(Tree tree) {
		this.tree = tree;
	}
	
	
	/**
	 * 0 = il top
	 * 1 = il peggio
	 * 
	 * Distanza dall'ottimo pesata in base all'importanza dei criteri e normalizzata a 1
	 * 
	 * @return
	 */
	public double calcFitness(){
		double result = 0;
		
		// distanza della richiesta dalla pianta alla fornita dal posto
		int sunExcess = Math.abs(place.getSunExposure()-tree.getSunRequest());
		boolean dead = sunExcess==2;
		result += sunExcess/2.0 * GardenConf.FITNESS_WEIGHT_SUN;
		
		
		// umidita' in eccesso rispetto a quella accettata dalla pianta
		int wetExcess = place.getWetLevel() - tree.getWetAllowed();
		dead |= wetExcess==2;
		result += wetExcess > 0 ? wetExcess/2.0 * GardenConf.FITNESS_WEIGHT_WET : 0;
		
		// vento in eccesso rispetto a quello ammesso dalla pianta
		int windExcess = place.getWindLevel() - tree.getWindAllowed();
		dead |= windExcess==2;
		result += windExcess > 0 ? windExcess/2.0 * GardenConf.FITNESS_WEIGHT_WIND : 0;
		
		result = result / (GardenConf.FITNESS_WEIGHT_SUN+GardenConf.FITNESS_WEIGHT_WET+GardenConf.FITNESS_WEIGHT_WIND);
		
		// Ritorna 
		//	1 se la pianta e' molto infelice o morirebbe
		//	0 se la pianta e' abbastanza felice
		//	0.5 altrimenti
		if(dead || result > 0.6){
			result = 1.0;
		}
		else if(result < 0.2){
			result = 0.0;
		}
		else {
			result = 0.5;
		}
		return result;
	}
	
	public String toString(){
		return "[P"+place+" > T"+tree+"]";
	}
	
}

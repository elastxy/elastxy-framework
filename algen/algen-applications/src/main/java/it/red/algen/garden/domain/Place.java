package it.red.algen.garden.domain;

/**
 * 
Ogni posizione Gi ha le seguenti caratteristiche:
Ga = esposizione alla pioggia: sì, no
Gs = esposizione al sole: alta, media, bassa, nessuna
Gv = quantità di vento: alta, bassa, nessuna
 * @author Gabriele
 *
 */
public class Place {
	private int sunExposure; 	// 0,1,2
	private int wetLevel; 		// 0,1,2
	private int windLevel;		// 0,1,2

	public Place(int sunExposure, int wetLevel, int windLevel) {
		super();
		this.sunExposure = sunExposure;
		this.wetLevel = wetLevel;
		this.windLevel = windLevel;
	}
	
	public int getWetLevel() {
		return wetLevel;
	}
	public void setWetLevel(int wetLevel) {
		this.wetLevel = wetLevel;
	}
	public int getSunExposure() {
		return sunExposure;
	}
	public void setSunExposure(int sunExposure) {
		this.sunExposure = sunExposure;
	}
	public int getWindLevel() {
		return windLevel;
	}
	public void setWindLevel(int windLevel) {
		this.windLevel = windLevel;
	}
	
	public String toString(){
		String result = "[";
		
		if(sunExposure==0) result += "_";
		else if(sunExposure==1) result += "s";
		else if(sunExposure==2) result += "S";
		
		if(wetLevel==0) result += "_";
		else if(wetLevel==1) result += "a";
		else if(wetLevel==2) result += "A";
		
		if(windLevel==0) result += "_";
		else if(windLevel==1) result += "v";
		else if(windLevel==2) result += "V";
		
		return result+"]";
	}
}

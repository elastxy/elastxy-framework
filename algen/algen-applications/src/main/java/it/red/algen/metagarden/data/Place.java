package it.red.algen.metagarden.data;

import java.io.Serializable;

/**
 * 
Ogni posizione Gi ha le seguenti caratteristiche:
Ga = esposizione alla pioggia: s�, no
Gs = esposizione al sole: alta, media, bassa, nessuna
Gv = quantit� di vento: alta, bassa, nessuna
location
 * @author Gabriele
 *
 */
public class Place implements Serializable {
	private static final long serialVersionUID = -4999646790650862071L;
	
	private int sunExposure; 	// 0,1,2
	private int wetLevel; 		// 0,1,2
	private int windLevel;		// 0,1,2
	public String zone;

	public Place(int sunExposure, int wetLevel, int windLevel, String zone) {
		super();
		this.sunExposure = sunExposure;
		this.wetLevel = wetLevel;
		this.windLevel = windLevel;
		this.zone = zone;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sunExposure;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (sunExposure != other.sunExposure)
			return false;
		return true;
	}
}

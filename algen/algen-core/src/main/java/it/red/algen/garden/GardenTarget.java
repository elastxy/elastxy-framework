/*
 * ExprTarget.java
 *
 * Created on 4 agosto 2007, 14.32
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden;

import it.red.algen.Target;
import it.red.algen.garden.domain.Place;
import it.red.algen.garden.domain.Tree;

/**
 *
 * @author grossi
 */
public class GardenTarget implements Target {
	private int bestLuxurianceLevel;
	private int worstLuxurianceLevel;
	
	
	public GardenTarget(Place[] places, Tree[] trees) {
		bestLuxurianceLevel = 0;
		worstLuxurianceLevel = places.length * 1; // 1=distanza massima dall'ottimo
	}


	public int getBestLuxurianceLevel() {
		return bestLuxurianceLevel;
	}


	public void setBestLuxurianceLevel(int bestLuxurianceLevel) {
		this.bestLuxurianceLevel = bestLuxurianceLevel;
	}


	public long getWorstLuxurianceLevel() {
		return worstLuxurianceLevel;
	}


	public void setWorstLuxurianceLevel(int worstLuxurianceLevel) {
		this.worstLuxurianceLevel = worstLuxurianceLevel;
	}
	


}

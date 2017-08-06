package it.red.algen.engine.standard;

import it.red.algen.domain.interfaces.Fitness;

/**
 * 
 * Normalized fitness in [0..1]
 * 
 * @author red
 *
 */
public class StandardFitness implements Fitness {
	
    // Valore massimo: tutti i valori di fitness devono essere normalizzati a 1
    public static final double MAX = 1.0;
    public static final double APPROX = 0.000000001;
    
    protected Double value;
    protected String legalCheck;
    
    
    public Double getValue(){
        return value;
    }

    public void setValue(Double value) {
    	this.value = value;
    }
    
	public String getLegalCheck() {
		return legalCheck;
	}

	public void setLegalCheck(String legalCheck) {
		this.legalCheck = legalCheck;
	}
	
    /** Essendo double occorre approssimare il valore
     */
    public boolean fit(){
        return Math.abs(MAX-value) < APPROX;
    }
    
    public boolean greaterThan(Fitness other){
        return value > other.getValue();
    }

	public boolean sameOf(Fitness other) {
        return Math.abs(other.getValue() - value) < APPROX;
	}


}

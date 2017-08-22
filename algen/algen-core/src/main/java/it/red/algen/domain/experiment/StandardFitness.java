package it.red.algen.domain.experiment;

import java.math.BigDecimal;

/**
 * 
 * Normalized fitness in [0..1]
 * 
 * @author red
 *
 */
public class StandardFitness implements Fitness {
	
    // Valore massimo: tutti i valori di fitness devono essere normalizzati a 1
    public static final BigDecimal MAX = BigDecimal.ONE;
    public static final int SCALE = 20;
    
    protected BigDecimal value;
    protected String legalCheck;
    protected RawFitness rawValue;
    

	@Override
    public BigDecimal getValue(){
        return value;
    }

	@Override
    public void setValue(BigDecimal value) {
    	this.value = value;
    }

	@Override
	public String getLegalCheck() {
		return legalCheck;
	}

	@Override
	public void setLegalCheck(String legalCheck) {
		this.legalCheck = legalCheck;
	}
	
	
	
	
	@Override
    public boolean fit(){
        return compareTo(MAX)==0;
     }

	/**
	 * TODOM: use only compareTo for both
	 */
	@Override
    public boolean greaterThan(Fitness other){
        return compareTo(other.getValue()) > 0;
    }

	/**
	 * Evaluates the nearest to the target fitness
	 * 
	 * Nearest = the nearer based on absolute value of the distance to fitness
	 * 
	 */
	@Override
    public boolean nearestThan(Fitness other, BigDecimal targetFitness) {
		BigDecimal otherDistance = other.getValue().subtract(targetFitness).abs();
		BigDecimal thisDistance = value.subtract(targetFitness).abs();
        return otherDistance.compareTo(thisDistance) >  0;
    }
	

	/**
	 * Returns true if current fitness is (strictly) greater than the desider threshold
	 */
	@Override
    public boolean overThreshold(BigDecimal targetThreshold) {
		return value.compareTo(targetThreshold) > 0;
    }

	
	@Override
	public boolean sameOf(Fitness other) {
        return compareTo(other.getValue())==0;
	}
	
	
	private int compareTo(BigDecimal other){
        return value.setScale(SCALE, BigDecimal.ROUND_HALF_UP).
        		compareTo(other.setScale(SCALE, BigDecimal.ROUND_HALF_UP));
	}
	

	@Override
	public Fitness copy(){
		Fitness result = new StandardFitness();
		result.setValue(value);
		result.setRawValue(rawValue);
		result.setLegalCheck(legalCheck);
		return result;
	}

	@Override
	public RawFitness getRawValue() {
		return rawValue;
	}

	@Override
	public void setRawValue(RawFitness raw) {
		this.rawValue = raw;
	}
	


	@Override
	public String toString() {
		return String.format("Value: %.20f, Check: %b", value, (legalCheck==null));
	}
}

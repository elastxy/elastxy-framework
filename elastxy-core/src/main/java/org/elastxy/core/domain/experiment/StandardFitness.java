/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.elastxy.core.domain.experiment;

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

	
	@Override
    public boolean fit(BigDecimal targetThreshold, BigDecimal targetFitness){
		boolean result = false;
		
		// Check threshold (if set)
		if(targetThreshold != null && overThreshold(targetThreshold)){
			result = true;
		}
		// Check exact fitness (if set)
		else if(targetFitness != null && sameOf(targetFitness)){
			result = true;
		}
		// Check maximum fitness
		else if(targetThreshold==null && targetFitness == null && fit()){
			result = true;
		}
		return result;
    }

	
	/**
	 * TODO3-2: use only compareTo for all??
	 */
	@Override
    public boolean greaterThan(Fitness other){
        return compareTo(other.getValue()) > 0;
    }

	/**
	 * Evaluates the nearest to the target fitness.
	 * 
	 * Nearest = the nearer based on absolute value of the distance to fitness.
	 * 
	 * E.g. with target fitness = 0.9, 0.901 is nearest than 0.898, 
	 * while 0.91 is NOT nearest than 0.89.
	 * 
	 */
	@Override
    public boolean nearestThan(Fitness other, BigDecimal targetFitness) {
		// TODO1-1: investigate null pointer in local cluster test
		BigDecimal otherDistance = other.getValue().subtract(targetFitness).abs();
		BigDecimal thisDistance = value.subtract(targetFitness).abs();
        return otherDistance.compareTo(thisDistance) >  0;
    }
	

	/**
	 * Returns true if current fitness is (strictly) greater than the desider threshold.
	 * 
	 * E.g. 0.997 is greater than 0.99, while 0.99 is NOT greater 0.99
	 */
	@Override
    public boolean overThreshold(BigDecimal targetThreshold) {
		return value.compareTo(targetThreshold) > 0;
    }

	
	@Override
	public boolean sameOf(Fitness other) {
        return compareTo(other.getValue())==0;
	}
	
	@Override
	public boolean sameOf(BigDecimal other) {
        return compareTo(other)==0;
	}
	
	
	private int compareTo(BigDecimal other){
//        return value.setScale(SCALE, BigDecimal.ROUND_HALF_UP).
//                compareTo(other.setScale(SCALE, BigDecimal.ROUND_HALF_UP));

		// null are worst cases
		BigDecimal thisFitness = value==null ? BigDecimal.ZERO : value.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
		BigDecimal otherFitness = other==null ? BigDecimal.ZERO : other.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
		return thisFitness.compareTo(otherFitness);
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

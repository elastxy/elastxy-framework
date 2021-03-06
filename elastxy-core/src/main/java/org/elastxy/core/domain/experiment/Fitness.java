
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
/*
 * Fitness.java
 *
 * Created on 4 agosto 2007, 13.50
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package org.elastxy.core.domain.experiment;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Fitness of a solution calculated against the Target
 * 
 * @author grossi
 */
public interface Fitness extends Serializable {
	
	/**
	 * Value for fitness, normalized in a [0..1] interval
	 * 
	 * @return
	 */
    public BigDecimal getValue();
    
    public void setValue(BigDecimal value);

    /**
     * Returns the raw fitness data before normalization: useful for
     * quickly comparing the two solutions.
     * 
     * It's optional.
     * 
     * @return
     */
    public RawFitness getRawValue();
    
    public void setRawValue(RawFitness raw);
    
    /**
     * Not null if it's not possible to calculate fitness for any reason
     * 
     * Create a LegalCheck object
     * 
     * @return
     */
    public String getLegalCheck();
    
    public void setLegalCheck(String legalCheck);
    
    
    /**
     * Returns true if current fitness value is next to the maximum (1.0)
     * with a given approximation 
     * @return
     */
    public boolean fit();

    /**
     * Returns true if current fitness value is next to the given values,
     * with a given approximation.
     * 
     * If parameters are not set, checks for maximum value.
     * @return
     */
    public boolean fit(BigDecimal targetThreshold, BigDecimal targetFitness);
    
    
    /**
     * Returns true if this fitness value is greater than a given one
     * @param other
     * @return
     */
    public boolean greaterThan(Fitness other);


    /**
     * Returns true if this fitness value is nearest to the desired fitness than the other
     * @param other
     * @return
     */
    public boolean nearestThan(Fitness other, BigDecimal targetFitness);
    
	/**
	 * Returns true if current fitness is (strictly) greater than the desider threshold
	 */
    public boolean overThreshold(BigDecimal targetThreshold);

    /**
     * Returns true if this fitness value is equals to a given one
     * @param other
     * @return
     */
    public boolean sameOf(Fitness other);

    /**
     * Returns true if this fitness value is equals to a given one
     * @param other
     * @return
     */
    public boolean sameOf(BigDecimal other);
    
    
    /**
     * Clones the fitness value
     * @return
     */
    public Fitness copy(); 
}

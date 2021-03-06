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
package org.elastxy.core.conf;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author grossi
 */
public class DefaultConfiguration {
	
	// SELECTION
    public static final long 	DEFAULT_INITIAL_SELECTION_NUMBER = 100L;
	public static final boolean DEFAULT_INITIAL_SELECTION_RANDOM = true;

    // RECOMBINATION
    public static final double 	DEFAULT_RECOMBINANTION_PERC = 0.8;
    public static final boolean DEFAULT_CROSSOVER_POINT_RANDOM = false; // false: 1/2 of the sequence
    
    // MUTATION
    public static final double 	DEFAULT_MUTATION_PERC = 0.1;
    
    // STOP CONDITIONS
    public static final BigDecimal	TARGET_LEVEL = null; // Default: maximize
    public static final BigDecimal	TARGET_THRESHOLD = null;//BigDecimal.ONE; // Default: not active
    public static final int 		MAX_GENERATIONS = -1;
    public static final int 		MAX_LIFETIME_MS = 1000;
    public static final int 		MAX_IDENTICAL_FITNESSES = -1;

	// ELITISM
    public static final boolean 	DEFAULT_SINGLECOLONY_ELITISM_ENABLED = true; // elitism enable
    public static final Long 		DEFAULT_SINGLECOLONY_ELITISM_NUMBER = 1L; // 1 elite solution maintained by default
    public static final Double 		DEFAULT_SINGLECOLONY_ELITISM_PERC = null; // 0.0-1.0 perc of individuals maintained between generations
    
    public static final boolean		DEFAULT_MULTICOLONY_ELITISM_ENABLED = true;
    public static final Long 		DEFAULT_MULTICOLONY_ELITISM_NUMBER = 1L;
    public static final Double 		DEFAULT_MULTICOLONY_ELITISM_PERC = null;

    public static final boolean 	DEFAULT_RECOMBINE_ELITE = true;

    // DISTRIBUTED
    public static final int 		DEFAULT_MAX_ERAS = 3;
    public static final int 		DEFAULT_MAX_ERAS_IDENTICAL_FITNESSES = -1;
    public static final int 		DEFAULT_PARTITIONS = 4;
    public static final int 		DEFAULT_RESHUFFLE_EVERY_ERAS = 2;

    // REQUEST CONTEXT
	public static final boolean WEB_REQUEST 	= true; // most restrictive
	public static final Locale USER_LOCALE 	= Locale.forLanguageTag("en-GB");
	// TODO2-1: properties file
	public static final List<Locale> AVAILABLE_LOCALES = Arrays.asList(
			Locale.forLanguageTag("en-GB"),
			Locale.forLanguageTag("en-US"),
			Locale.forLanguageTag("it-IT"),
			Locale.forLanguageTag("fr-FR"));

}

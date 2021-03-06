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

import java.io.Serializable;

/**
 * Parameters for genetics operators:
 * - selection: directs the search towards promosing regions of the search space
 * - recombination: changes the context of already available, useful information
 * - mutation: introduces innovations into the population
 * 
 * Acting together, mutation and recombination explore the search space while selection exploits the information
 * represented within the population. The balance between exploration and exploitation or, in other words,
 * between the creation of diversity and its reduction by focusing on the individuals of higher fittness, is critical.
 * 
 * @author grossi
 */
public class AlgorithmParameters implements Serializable {

	/*
	 * ======================================
	 * EVOLUTION
	 * ======================================
	 */
	public boolean randomEvolution = false;

	public StopConditions stopConditions = new StopConditions();
	
	/*
	 * ======================================
	 * SELECTION
	 * ======================================
	 */
	public long initialSelectionNumber = DefaultConfiguration.DEFAULT_INITIAL_SELECTION_NUMBER;    
	public boolean initialSelectionRandom = DefaultConfiguration.DEFAULT_INITIAL_SELECTION_RANDOM;

	// TODO2-8: Selection types: stochastic sampling, roulette-wheel, tournament (con % gruppi), truncation (con %) + elitism, reward based
    // At the end they are 4: Proportional selection (in combination with a scaling method), linear ranking, tournament selection, and
    // (,)-selection (respectively (+)-selection).
    //	public SelectionType selectionType = DefaultConfiguration.DEFAULT_SELECTION_TYPE;

	/*
	 * The selection operator provides a mechanism to affect this balance towards exploitation by increasing emphasis 
	 * on the better individuals or towards exploration by providing similar chances to survive even for worse individuals. 
	 *
	 * Informally, the term selective pressure is widely used to characterize the strong (high selective pressure) 
	 * respectively weaker (smaller selective pressure) emphasis of selection on the best individuals.
	 * 
	 * See:
	 * https://pdfs.semanticscholar.org/ea3c/6ef1b13eb007a5a633a71c011fb0f9843218.pdf
	 */
	// TODO2-4: selection pressure increase
    //	public double pressureIncrease = DefaultConfiguration.DEFAULT_SELECTION_PRESSURE_INCREASE;

    // TODO3-8: base selection on individuals analysing promising groups of genes instead at looking only at fitness
	// public BlockSearchType blockSearchType = DefaultConfiguration.DEFAULT_BUILDING_BLOCK_SEARCH_TYPE
	
    
	/*
	 * ======================================
	 * RECOMBINATION
	 * ======================================
	 */
	public double recombinationPerc = DefaultConfiguration.DEFAULT_RECOMBINANTION_PERC;
	
	public boolean crossoverPointRandom = DefaultConfiguration.DEFAULT_CROSSOVER_POINT_RANDOM;
	
	// TODO3-4: Ordering: not ordered, ordered (based on natural genes ordering given by application)
	//	public RecombinationOrderType recombinationOrder = DefaultConfiguration.DEFAULT_RECOMBINANTION_ORDER;
	
	// TODO2-8: Recombination Types: single point, multipoint, uniform, half uniform, three parents, CX
	// https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_crossover.htm
	// http://ictactjournals.in/paper/IJSC_V6_I1_paper_4_pp_1083_1092.pdf
	
	//	public RecombinationType recombinationType = DefaultConfiguration.DEFAULT_RECOMBINATION_TYPE;
	//	public int recombinationPoints = DefaultConfiguration.DEFAULT_RECOMBINATION_POINTS;

	
	/*
	 * ======================================
	 * MUTATION
	 * ======================================
	 */
	public double mutationPerc = DefaultConfiguration.DEFAULT_MUTATION_PERC;

	// TODO3-8: Mutation types: https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_mutation.htm
	// TODO3-4: Auto (decrease based on current results and genes alphabet length if available), Fixed ratio, Increase pressure
	//	public MutationDecreaseType mutationDecreaseType =  = DefaultConfiguration.DEFAULT_MUTATION_DECREASE_TYPE; 
	//	public double mutationDecreaseRatio =  = DefaultConfiguration.DEFAULT_MUTATION_DECREASE_RATIO; 


	/*
	 * ======================================
	 * DISTRIBUTED
	 * ======================================
	 */
	public int partitions = 		DefaultConfiguration.DEFAULT_PARTITIONS;
    public int reshuffleEveryEras = DefaultConfiguration.DEFAULT_RESHUFFLE_EVERY_ERAS;


    /*
	 * ======================================
	 * ELITISM
	 * ======================================
	 */
    public ElitismParameters elitism = new ElitismParameters();

}

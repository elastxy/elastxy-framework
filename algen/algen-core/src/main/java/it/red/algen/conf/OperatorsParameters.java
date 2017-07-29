/*
 * Parameters.java
 *
 * Created on 4 agosto 2007, 13.52
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.conf;

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
public class OperatorsParameters {
	
	/*
	 * ======================================
	 * SELECTION
	 * ======================================
	 */
	public long _initialSelectionNumber = DefaultConfiguration.DEFAULT_INITIAL_SELECTION_NUMBER;    
	public boolean _initialSelectionRandom = DefaultConfiguration.DEFAULT_INITIAL_SELECTION_RANDOM;
    public boolean _elitarism = DefaultConfiguration.DEFAULT_ELITARISM;

	// TODOM: type of selection: stochastic sampling, roulette-wheel, tournament (con % gruppi), truncation (con %) + elitism, reward based
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
	// TODOM: pressure increase
    //	public double pressureIncrease = DefaultConfiguration.DEFAULT_SELECTION_PRESSURE_INCREASE;

    // TODOM: base selection on individuals analysing promising groups of genes instead at looking only at fitness
	// public BlockSearchType blockSearchType = DefaultConfiguration.DEFAULT_BUILDING_BLOCK_SEARCH_TYPE
	
    
	/*
	 * ======================================
	 * RECOMBINATION
	 * ======================================
	 */
	public double _recombinationPerc = DefaultConfiguration.DEFAULT_RECOMBINANTION_PERC;
	
	// TODOM: Ordering: not ordered, ordered (based on natural genes ordering given by application)
	//	public RecombinationOrderType recombinationOrder = DefaultConfiguration.DEFAULT_RECOMBINANTION_ORDER;
	
	// TODOM: Types: single point, multipoint, uniform, half uniform, three parents
	//	public RecombinationType recombinationType = DefaultConfiguration.DEFAULT_RECOMBINATION_TYPE;
	//	public int recombinationPoints = DefaultConfiguration.DEFAULT_RECOMBINATION_POINTS;

	
	/*
	 * ======================================
	 * MUTATION
	 * ======================================
	 */
	public double _mutationPerc = DefaultConfiguration.DEFAULT_MUTATION_PERC;
	
	// TODOM: Auto (decrease based on current results and genes alphabet length if available), Fixed ratio
	//	public MutationDecreaseType mutationDecreaseType =  = DefaultConfiguration.DEFAULT_MUTATION_DECREASE_TYPE; 
	//	public double mutationDecreaseRatio =  = DefaultConfiguration.DEFAULT_MUTATION_DECREASE_RATIO; 
    
}

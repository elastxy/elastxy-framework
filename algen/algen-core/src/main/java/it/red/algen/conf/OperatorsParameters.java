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
 * Parameters for genetics operatiors:
 * - selection
 * - recombination
 * - mutation
 *
 * @author grossi
 */
public class OperatorsParameters {
	public long _initialSelectionNumber = DefaultConfiguration.DEFAULT_INITIAL_SELECTION_NUMBER;    
	public boolean _initialSelectionRandom = DefaultConfiguration.DEFAULT_INITIAL_SELECTION_RANDOM;
	public double _recombinationPerc = DefaultConfiguration.DEFAULT_RECOMBINANTION_PERC;
    public double _mutationPerc = DefaultConfiguration.DEFAULT_MUTATION_PERC;
    public boolean _elitarism = DefaultConfiguration.DEFAULT_ELITARISM;
    
}

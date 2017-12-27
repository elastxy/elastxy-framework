package org.elastxy.core.conf;

import java.io.Serializable;

/**
 * Elitism operator parameters, driving single- and multi- colony evolution.
 * 
 * @author red
 *
 */
public class ElitismParameters implements Serializable {
	public boolean singleColonyElitism = 	DefaultConfiguration.DEFAULT_SINGLECOLONY_ELITISM_ENABLED;
	public Double singleColonyElitismPerc = 	DefaultConfiguration.DEFAULT_SINGLECOLONY_ELITISM_PERC;
	public Long singleColonyElitismNumber = 	DefaultConfiguration.DEFAULT_SINGLECOLONY_ELITISM_NUMBER;

	// All multiColonyElitismNumber are reinserted into ALL colonies to spread their (good) genes around
    public boolean multiColonyElitism = DefaultConfiguration.DEFAULT_MULTICOLONY_ELITISM_ENABLED;
	public Double multiColonyElitismPerc = 	DefaultConfiguration.DEFAULT_MULTICOLONY_ELITISM_PERC;
	public Long multiColonyElitismNumber = 	DefaultConfiguration.DEFAULT_MULTICOLONY_ELITISM_NUMBER;
	
}

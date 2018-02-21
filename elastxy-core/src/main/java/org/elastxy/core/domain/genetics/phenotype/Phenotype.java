package org.elastxy.core.domain.genetics.phenotype;

import java.io.Serializable;

/**
 * Exterior manifestation of a Solution, used to calculate fitness.
 * 
 * @param <T>
 * 
 * @author red
 */
public interface Phenotype<T>  extends Serializable {

	public T getValue();
	
	public Phenotype<T> copy();

}

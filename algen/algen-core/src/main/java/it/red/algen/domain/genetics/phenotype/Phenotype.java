package it.red.algen.domain.genetics.phenotype;

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

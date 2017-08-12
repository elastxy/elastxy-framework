package it.red.algen.domain.genetics;


/**
 * Exterior manifestation of a Solution, used to calculate fitness.
 * 
 * @param <T>
 * 
 * @author red
 */
public interface Phenotype<T> {

	public T getValue();
	
	public Phenotype<T> copy();

}

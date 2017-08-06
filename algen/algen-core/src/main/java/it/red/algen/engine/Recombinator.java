package it.red.algen.engine;

import java.util.List;

import it.red.algen.domain.Solution;

@SuppressWarnings("rawtypes")
public interface Recombinator<R extends Solution> {

	public List<R> recombine(List<R> parents);
	
}

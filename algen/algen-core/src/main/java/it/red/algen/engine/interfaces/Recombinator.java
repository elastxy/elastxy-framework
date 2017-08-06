package it.red.algen.engine.interfaces;

import java.util.List;

import it.red.algen.domain.interfaces.Solution;

@SuppressWarnings("rawtypes")
public interface Recombinator<R extends Solution> {

	public List<R> recombine(List<R> parents);
	
}

package it.red.algen.context;

/**
 * Convenient interface for building up an AlgorithmContext,
 * given its simplest coordinates
 * @author red
 */
public interface ContextBuilder {
	
	/**
	 * Build an AlgorithmContext given application name and if it is a benchmark or not
	 * @param application
	 * @param benchmark
	 * @return
	 */
	public AlgorithmContext build(String applicationName, boolean benchmark);

}

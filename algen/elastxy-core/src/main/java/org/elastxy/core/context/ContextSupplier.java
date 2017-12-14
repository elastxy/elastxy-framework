package org.elastxy.core.context;

/**
 * Implementing classes must ensure the clients to get current
 * algorithm context during the whole experiment.
 * 
 * Initialization and finalization steps may also be provided.
 * 
 * Algorithm context can be built by a {@link ContextBuilder}
 * 
 * @author red
 *
 */
public interface ContextSupplier {

	public void init(AlgorithmContext context);

	public AlgorithmContext getContext();

	public void destroy();
	
}

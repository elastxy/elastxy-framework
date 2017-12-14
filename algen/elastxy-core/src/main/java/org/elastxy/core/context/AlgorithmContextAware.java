package org.elastxy.core.context;


/**
 * Implementations can be made aware of the context.
 * 
 * @author red
 *
 */
public interface AlgorithmContextAware {
	
	/**
	 * Sets current context.
	 * 
	 * @param context
	 */
	public void setAlgorithmContext(AlgorithmContext context);
}

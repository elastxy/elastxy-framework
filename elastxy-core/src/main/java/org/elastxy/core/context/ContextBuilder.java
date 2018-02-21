package org.elastxy.core.context;

import org.elastxy.core.applications.AppStage;

/**
 * Convenient interface for building up an AlgorithmContext,
 * given its simplest coordinates
 * @author red
 */
public interface ContextBuilder {
	
	/**
	 * Build an AlgorithmContext given application name and stage.
	 * @param application
	 * @param benchmark
	 * @return
	 */
	public AlgorithmContext build(String applicationName, AppStage appStage);

}

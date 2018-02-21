package org.elastxy.core.tracking;

import org.elastxy.core.stats.ExperimentStats;

public interface Reporter {

	public void createReports(ExperimentStats stats);
	
}

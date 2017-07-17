package it.red.algen.tracking;

import it.red.algen.stats.ExperimentStats;

public interface Reporter {

	public void createReports(ExperimentStats stats);
	
}

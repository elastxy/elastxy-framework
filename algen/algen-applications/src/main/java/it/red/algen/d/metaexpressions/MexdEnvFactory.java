package it.red.algen.d.metaexpressions;

import java.math.BigDecimal;

import it.red.algen.applications.ApplicationException;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.experiment.NumberRawFitness;
import it.red.algen.domain.experiment.PerformanceTarget;
import it.red.algen.domain.experiment.Target;
import it.red.algen.engine.factory.AbstractEnvFactory;
import it.red.algen.engine.metadata.StandardMetadataGenoma;
import it.red.algen.metaexpressions.MexConstants;

public class MexdEnvFactory extends AbstractEnvFactory<PerformanceTarget, BigDecimal, StandardMetadataGenoma>{


	// TODOM: take outside Target definition code, as a new Component
	// TODOD: duplicate with MexEnvFactory: make EnvFactory a Strategy, not an abstract class!
	@Override
	protected Target<PerformanceTarget, BigDecimal> defineTarget(WorkingDataset dataset) {
		return TargetBuilder.build(context);
	}

}

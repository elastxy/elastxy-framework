package org.elastxy.web.renderer;

import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.ErrorCode;
import org.elastxy.web.controller.ExperimentResponse;
import org.springframework.stereotype.Component;

/**
 * An implementation to be used for web clients.
 * @author red
 *
 */
@Component
public class InternalExperimentResponseRenderer implements ExperimentResponseRenderer {

	@Override
	public ExperimentResponse render(AlgorithmContext context, ExperimentStats stats) {
		ExperimentResponse response = new ExperimentResponse();
		response.experimentStats = stats;
		return response;
	}

	@Override
	public ExperimentResponse render(AlgorithmContext context, String content) {
		ExperimentResponse response = new ExperimentResponse();
		response.content = content;
		return response;
	}

	@Override
	public ExperimentResponse render(AlgorithmContext context, Throwable t) {
		ExperimentResponse response = new ExperimentResponse();
		response.errorCode = ErrorCode.ERROR.getCode(); 
		response.errorDescription = t.toString();
		return response;
	}

}

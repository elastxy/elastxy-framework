/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
public class WebExperimentResponseRenderer implements ExperimentResponseRenderer {

	@Override
	public ExperimentResponse render(AlgorithmContext context, ExperimentStats stats) {
		ExperimentResponse response = new ExperimentResponse();
		response.experimentStats = null; // does not provide any detailed info
		response.clientFriendlyResults = context.application.resultsRenderer.renderFriendly(stats); 
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

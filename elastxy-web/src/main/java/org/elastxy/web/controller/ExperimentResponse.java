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
package org.elastxy.web.controller;

import java.io.Serializable;

import org.elastxy.core.stats.ClientFriendlyResults;
import org.elastxy.core.stats.ExperimentStats;
import org.elastxy.core.tracking.ErrorCode;

/**
 * Represents a generic response from ElastXY controllers.
 * 
 * Detailed experiment stats maybe omitted in case of web client. 
 * 
 * @author red
 *
 */
public class ExperimentResponse implements Serializable {
	private static final long serialVersionUID = 4694737846031429143L;
	
	// STATUS
	public ResponseStatus status = ResponseStatus.OK;
	public String content = null; // generic content
	public int errorCode = ErrorCode.NO_ERROR.getCode();
	public String errorDescription = null;
	
	// OUTCOME
	// raw stats details
	public ExperimentStats experimentStats = null;
	// more friendly details to be exported to a client
	public ClientFriendlyResults clientFriendlyResults = new ClientFriendlyResults();
}

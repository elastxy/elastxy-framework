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

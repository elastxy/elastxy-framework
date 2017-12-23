package org.elastxy.app.d.metaexpressions;

import org.elastxy.app.metaexpressions.HTMLExpressionsSolutionRenderer;
import org.elastxy.core.tracking.SolutionRenderer;
import org.elastxy.distributed.tracking.DistributedResultsRenderer;

/**
 * Simple: X OP Y = RES
 * 
 * @author red
 *
 */
public class MexdResultsRenderer extends DistributedResultsRenderer {

	@Override
	public void setSolutionRenderer(SolutionRenderer solutionRenderer) {
		super.setSolutionRenderer(new HTMLExpressionsSolutionRenderer());
	}
	
}

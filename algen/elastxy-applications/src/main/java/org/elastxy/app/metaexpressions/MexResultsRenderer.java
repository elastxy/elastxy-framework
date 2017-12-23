package org.elastxy.app.metaexpressions;

import org.elastxy.core.tracking.DefaultResultsRenderer;
import org.elastxy.core.tracking.SolutionRenderer;

/**
 * Simple: X OP Y = RES
 * 
 * @author red
 *
 */
public class MexResultsRenderer extends DefaultResultsRenderer {

	@Override
	public void setSolutionRenderer(SolutionRenderer solutionRenderer) {
		super.setSolutionRenderer(new HTMLExpressionsSolutionRenderer());
	}
	
}

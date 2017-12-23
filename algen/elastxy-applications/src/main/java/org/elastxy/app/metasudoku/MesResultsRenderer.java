package org.elastxy.app.metasudoku;

import org.elastxy.core.tracking.DefaultResultsRenderer;
import org.elastxy.core.tracking.SolutionRenderer;

/**
 * Simple: HTML Table for client-side representation
 * 
 * @author red
 *
 */
public class MesResultsRenderer extends DefaultResultsRenderer {

	@Override
	public void setSolutionRenderer(SolutionRenderer solutionRenderer) {
		super.setSolutionRenderer(new HTMLMatrixSolutionRenderer());
	}
	
}

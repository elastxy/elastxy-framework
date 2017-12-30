package org.elastxy.app.d.metasudoku;

import org.elastxy.app.metasudoku.HTMLMatrixSolutionRenderer;
import org.elastxy.core.tracking.SolutionRenderer;
import org.elastxy.distributed.tracking.DistributedResultsRenderer;

/**
 * Simple: HTML Table for client-side representation
 * 
 * @author red
 *
 */
public class MesdResultsRenderer extends DistributedResultsRenderer {

	
	@Override
	public void setSolutionRenderer(SolutionRenderer solutionRenderer) {
		// TODO0-2: no good: separate local from distributed solution renderer and inject
		super.setSolutionRenderer(new HTMLMatrixSolutionRenderer());
	}
	
}

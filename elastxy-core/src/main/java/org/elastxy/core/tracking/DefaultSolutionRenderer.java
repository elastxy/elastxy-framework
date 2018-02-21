package org.elastxy.core.tracking;

import org.elastxy.core.domain.experiment.Solution;

public class DefaultSolutionRenderer implements SolutionRenderer<String> {

	@Override
	public String render(Solution solution){
		return solution==null ? "null solution" : solution.toString(); 
	}
	
}

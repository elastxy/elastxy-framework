package it.red.algen.tracking;

import it.red.algen.domain.experiment.Solution;

public class DefaultSolutionRenderer implements SolutionRenderer<String> {

	@Override
	public String render(Solution solution){
		return solution==null ? "null solution" : solution.toString(); 
	}
	
}

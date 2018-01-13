package ${groupId}.appArchetype;

import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.phenotype.NumberPhenotype;
import org.elastxy.core.tracking.SolutionRenderer;

public class AppSolutionRenderer  implements SolutionRenderer<String> {

	@Override
	public String render(Solution solution){
		if(solution==null) return "";
		Chromosome chromosome = (Chromosome)solution.getGenotype();
		String result = 
				chromosome.genes.get(0).allele.value+
				" "+
				chromosome.genes.get(1).allele.value+
				" "+
				chromosome.genes.get(2).allele.value+
				" = "+
				((NumberPhenotype)solution.getPhenotype()).value;
		return result;
	}
	
}
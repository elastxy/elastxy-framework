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

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
package ${groupId}.multicolonyxo;

import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.phenotype.NumberPhenotype;
import org.elastxy.core.engine.core.IllegalSolutionException;
import org.elastxy.core.engine.fitness.Incubator;

public class AppIncubator implements Incubator<Chromosome, NumberPhenotype>{

	@Override
	public NumberPhenotype grow(Chromosome genotype, Env environment) throws IllegalSolutionException{
		NumberPhenotype result = new NumberPhenotype();
		
		Character operator = (Character)genotype.genes.get(1).allele.value;
		Long operand2 = (Long)genotype.genes.get(2).allele.value;
        try {
        	result.value = calculate(
				(Long)genotype.genes.get(0).allele.value,
				operator, 
				operand2);
        }
        catch(IllegalSolutionException ex){
        	throw ex;
        }
        catch(Exception ex){ 
            String legalCheck = String.format("Generic exception while growing solution genotype: "+genotype);
			throw new IllegalSolutionException(legalCheck, legalCheck);
        }
		return result;
	}
	
	
	// TODO: math expression evaluator (ex. exp4j)
    public long calculate(long val1, char op, long val2) throws IllegalSolutionException {
        long result = 0;
        switch(op){
            case '+':
                result = val1 + val2;
                break;
            case '-':
                result = val1 - val2;
                break;
            case '*':
                result = val1 * val2;
                break;
            case '/':
                if(val2==0){
                    String legalCheck = "Division by 0 not allowed: second operand not valid in expression ["+val1+""+op+""+val2+"].";
        			throw new IllegalSolutionException(legalCheck, legalCheck);
                }
                result = val1 / val2;
                break;
            default:
                throw new IllegalArgumentException("Operator not admitted: "+op);
        }
        return result;
    }


}

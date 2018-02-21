package org.elastxy.app.metaexpressions;

import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.phenotype.NumberPhenotype;
import org.elastxy.core.engine.core.IllegalSolutionException;
import org.elastxy.core.engine.fitness.Incubator;

public class MexIncubator implements Incubator<Chromosome, NumberPhenotype>{

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
	
	
	// TODO3-4: math expression evaluator (ex. exp4j)
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

package it.red.algen.metaexpressions;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.domain.genetics.phenotype.NumberPhenotype;
import it.red.algen.engine.core.IllegalSolutionException;
import it.red.algen.engine.fitness.Incubator;

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
	
	
	// TODOB: math expression evaluator (ex. exp4j)
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

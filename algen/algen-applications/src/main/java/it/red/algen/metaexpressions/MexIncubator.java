package it.red.algen.metaexpressions;

import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.genetics.genotype.Chromosome;
import it.red.algen.domain.genetics.phenotype.NumberPhenotype;
import it.red.algen.engine.core.IllegalSolutionException;
import it.red.algen.engine.fitness.Incubator;

public class MexIncubator implements Incubator<Chromosome, NumberPhenotype>{

	@Override
	public NumberPhenotype grow(WorkingDataset workingDataset, Chromosome genotype, Env environment) {
		NumberPhenotype result = new NumberPhenotype();
		result.value = calculate(
				(Long)genotype.genes.get(0).allele.value,
				(Character)genotype.genes.get(1).allele.value, 
				(Long)genotype.genes.get(2).allele.value);
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
                    throw new IllegalSolutionException("Division by zero.");
                }
                result = val1 / val2;
                break;
            default:
                throw new IllegalArgumentException("Operator not admitted: "+op);
        }
        return result;
    }


}

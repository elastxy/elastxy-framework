package it.red.algen.metaexpressions;

import it.red.algen.dataaccess.WorkingDataset;
import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.genetics.NumberPhenotype;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.engine.IllegalSolutionException;
import it.red.algen.engine.fitness.Incubator;

public class MexIncubator implements Incubator<SequenceGenotype, NumberPhenotype>{

	@Override
	public NumberPhenotype grow(WorkingDataset workingDataset, SequenceGenotype genotype, Env environment) {
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
                    throw new IllegalSolutionException("Divisione per zero.");
                }
                result = val1 / val2;
                break;
            default:
                throw new IllegalArgumentException("Operatore non ammesso: "+op);
        }
        return result;
    }


}

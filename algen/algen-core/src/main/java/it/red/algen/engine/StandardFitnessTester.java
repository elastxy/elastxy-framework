package it.red.algen.engine;

import java.util.Iterator;

import it.red.algen.domain.Fitness;
import it.red.algen.domain.Population;
import it.red.algen.domain.Solution;
import it.red.algen.domain.Target;
import it.red.algen.tracking.EnvObserver;

public class StandardFitnessTester implements FitnessTester {
	private FitnessCalculator calculator;
	private EnvObserver observer;

	public StandardFitnessTester(FitnessCalculator calculator){
		this.calculator = calculator;
	}
	
	@Override
	public void subscribe(EnvObserver observer) {
		this.observer = observer;
	}
	
	@Override
    public Fitness test(Target<?> target, Population population){
    	population.bestMatch = null;
        Iterator<Solution> it = population.solutions.iterator();
        while(it.hasNext()){ // TODOA: MapReduce!
            Solution solution = it.next();
            
            // Skip fitness test for solutions already tested
            if(solution.getFitness()==null || solution.getFitness().getValue()==null){
            	Fitness fitness = calculator.calculate(solution, target);
            	if(fitness.getLegalCheck()!=null) {
            		fireIllegalSolutionEvent(solution);
            	}
            	else {
            		fireFitnessCalculatedEvent(solution);
            	}
            }
            if(population.bestMatch==null || 
            		(population.bestMatch!=null && solution.getFitness().greaterThan(population.bestMatch.getFitness()))){
            	population.bestMatch = solution;
            }
        }
        
        // Order by fitness desc
        population.orderByFitnessDesc();
        
        return population.bestMatch.getFitness();
    }
    


    private void fireFitnessCalculatedEvent(Solution s){
        observer.fitnessCalculatedEvent(s);
    }
    
    private void fireIllegalSolutionEvent(Solution s){
    	observer.illegalSolutionEvent(s);
    }

    
}

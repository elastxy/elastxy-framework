package org.elastxy.core.engine.fitness;

import java.util.Iterator;

import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.Fitness;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.tracking.EnvObserver;

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
    public Fitness test(Population population, Env env){

		// Reset current bestMatch
    	population.bestMatch = null;
    	
        Iterator<Solution<?,?>> it = population.solutions.iterator();
        while(it.hasNext()){
            Solution<?,?> solution = it.next();
            
            // Skip fitness test for solutions already tested
            if(solution.getFitness()==null || solution.getFitness().getValue()==null){
            	Fitness fitness = calculator.calculate(solution, env);
            	if(fitness.getLegalCheck()!=null) { 
            		fireIllegalSolutionEvent(solution);
            	}
            	else {
            		fireFitnessCalculatedEvent(solution);
            	}
            }

            // Check if desidered fitness is matched => it's best match ABSOLUTE: stop here!
    		if(solution.getFitness().fit(env.target.getTargetThreshold(), env.target.getTargetFitness())) {
    			// TODOM-4: multiple best matches!
    			population.bestMatch = solution;
    			population.goalReached = true;
    			break;
    		}
            
            // Check if it's best match RELATIVE
    		else if(isBestMatch(env.target, population.bestMatch, solution)){
            	population.bestMatch = solution;
            }
            
//            // Else remove phenotype to free memory: not useful anymore
//            // TODOM-2: configurable, in some cases doesn't work
//            else {
//            	solution.setPhenotype(null);
//            }
        }
        
        // No target fitness: Order by fitness desc
        if(env.target.getTargetFitness()==null){     
        	population.orderByFitnessDesc();
        }
        // Target fitness set: Order by proximity to the fitness
        else {
        	population.orderByFitnessProximityDesc(env.target.getTargetFitness());
        }
        
        return population.bestMatch.getFitness();
    }

	private boolean isBestMatch(Target<?, ?> target, Solution<?, ?> currentBestMatch, Solution<?, ?> currentSolution) {
		boolean bestMatch = false;
		
		// No current best match: first solution is set to TRUE
		if(currentBestMatch==null){
			bestMatch = true;
		}
		
		// Target fitness set: Nearer to desired fitness than current best => TRUE
		else if(target.getTargetFitness()!=null) {
			bestMatch = currentSolution.getFitness().nearestThan(currentBestMatch.getFitness(), target.getTargetFitness());
		}
		// No target fitness: More than current best => TRUE
		else {
			bestMatch = currentSolution.getFitness().greaterThan(currentBestMatch.getFitness());
		}
		return bestMatch;
	}
    


    private void fireFitnessCalculatedEvent(Solution s){
        observer.fitnessCalculatedEvent(s);
    }
    
    private void fireIllegalSolutionEvent(Solution s){
    	observer.illegalSolutionEvent(s);
    }

    
}

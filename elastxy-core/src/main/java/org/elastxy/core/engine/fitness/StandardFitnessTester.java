package org.elastxy.core.engine.fitness;

import java.util.ArrayList;
import java.util.Iterator;

import org.elastxy.core.conf.ElitismParameters;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.experiment.Fitness;
import org.elastxy.core.domain.experiment.Population;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.engine.core.BestMatchesSupport;
import org.elastxy.core.tracking.EnvObserver;

/**
 * Tests population fitness of every individual solutions in a population.
 * 
 * Assign the population with the single best match reference found 
 * for following checks, order solutions and calculate best matches.
 * 
 * @author red
 *
 */
public class StandardFitnessTester implements FitnessTester {
	private FitnessCalculator calculator;
	private ElitismParameters elitismParameters;
	
	private EnvObserver observer;

	public StandardFitnessTester(FitnessCalculator calculator, ElitismParameters elitismParameters){
		this.calculator = calculator;
		this.elitismParameters = elitismParameters;
	}
	
	
	@Override
	public void subscribe(EnvObserver observer) {
		this.observer = observer;
	}
	
	@Override
    public Fitness test(Population population, Env env){

		// Reset current bestMatch and best matches
		population.bestMatch = null;
		population.bestMatches = new ArrayList<Solution<?,?>>();
    	
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

            // Check if desired fitness is reached => it's best match ABSOLUTE
    		if(solution.getFitness().fit(env.target.getTargetThreshold(), env.target.getTargetFitness())) {
    			population.bestMatch = solution;
    			population.goalReached = true;
    			// TODO2-2: stop configurable: this is more efficient, but other perfectly fitting solutions 
    			// and their fitness and phenotype calcs are left out with fitness null
//    			break;
    		}
            
            // Check if it's best match RELATIVE
    		else if(isBestMatch(env.target, population.bestMatch, solution)){
            	population.bestMatch = solution;
            }
            
//            // Else remove phenotype to free memory: not useful anymore
//            // TODO3-2: configurable, in some cases doesn't work
//            else {
//            	solution.setPhenotype(null);
//            }
        }
        
		// Order solutions
        // No target fitness: Order by fitness desc
        if(env.target.getTargetFitness()==null){     
        	population.orderByFitnessDesc();
        }
        // Target fitness set: Order by proximity to the fitness
        else {
        	population.orderByFitnessProximityDesc(env.target.getTargetFitness());
        }

        // Creates best matches list for future use
        BestMatchesSupport.calculateBestMatches(population, elitismParameters);
        
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

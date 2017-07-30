package it.red.algen.engine;

import java.util.Iterator;
import java.util.Observable;

import it.red.algen.domain.Fitness;
import it.red.algen.domain.Population;
import it.red.algen.domain.Solution;
import it.red.algen.domain.Target;
import it.red.algen.tracking.EnvObserver;

public class StandardFitnessTester implements FitnessTester {

	private EnvObserver observer;

	@Override
	public void subscribe(EnvObserver observer) {
		this.observer = observer;
	}
	
    public Fitness testFitness(Target target, Population population){
    	population.bestMatch = null;
        Iterator<Solution> it = population._solutions.iterator();
        while(it.hasNext()){ // TODOA: MapReduce!
            Solution solution = it.next();
            solution.calcFitness(target);
            if(solution.legalCheck()!=null) {
                fireIllegalSolutionEvent(solution);
            }
            else {
                fireFitnessCalculatedEvent(solution);
            }
            if(population.bestMatch==null || 
            		(population.bestMatch!=null && solution.getFitness().greaterThan(population.bestMatch.getFitness()))){
            	population.bestMatch = solution;
            }
        }
        return population.bestMatch.getFitness();
    }
    


    private void fireFitnessCalculatedEvent(Solution s){
        observer.fitnessCalculatedEvent(s);
    }
    
    private void fireIllegalSolutionEvent(Solution s){
    	observer.illegalSolutionEvent(s);
    }

    
}

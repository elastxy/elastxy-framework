package it.red.algen.mex.encapsulated

import org.apache.log4j._
import it.red.algen.mex.partitioned.Monitoring
import it.red.algen.mex.partitioned.Closures
import it.red.algen.mex.partitioned.Functions


object GeneticFunctionsE {
  val logger : Logger = Logger.getLogger("it.red")
  
  
  /**
   * Create a new RDD, cloning every solutions and excluding best match
   */
  def selection(population: MexPopulationE): MexPopulationE = {

    if(logger.isDebugEnabled()) {
      val totSolutions = population.solutions.size
      logger.debug(f"Selecting solutions from a population of $totSolutions")
      Monitoring.printListSolutions(population.solutions)
    }

    var result : MexPopulationE = new MexPopulationE
    result.solutions = population.solutions.filter(s => !s.best).map(Closures.cloneSolution)
    
    if(logger.isDebugEnabled()) {
      val totSolutions = result.solutions.size
      logger.debug(f"Selection done. Actual population of $totSolutions solutions")
      Monitoring.printListSolutions(result.solutions)
    }
    result
  }
  
}
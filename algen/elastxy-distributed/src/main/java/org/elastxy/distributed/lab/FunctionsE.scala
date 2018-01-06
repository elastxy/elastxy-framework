package org.elastxy.mex.encapsulated

import org.apache.log4j._
import org.elastxy.mex.partitioned.GenomaFunctions
import org.apache.spark.rdd.RDD
import scala.collection.mutable.ListBuffer
import org.elastxy.mex.partitioned.MexSolution
import org.elastxy.mex.partitioned.Functions
import org.elastxy.mex.partitioned.Closures


object FunctionsE {

  def reinsertBestMatch(lastPopulation: MexPopulationE, newPopulation: MexPopulationE) : MexPopulationE = {
    if(logger.isDebugEnabled()) {
      val newLength = newPopulation.solutions.size
	    logger.debug(s"Reinserting last best match cloned on new populations of $newLength solutions")
    }
    val bestMatch : MexSolution = Closures.cloneSolution(lastPopulation.bestMatch)
    newPopulation.solutions = bestMatch :: newPopulation.solutions
    
    if(logger.isDebugEnabled()) {
      val lastLength = lastPopulation.solutions.size
	    val newLength = newPopulation.solutions.size
	    logger.debug(s"New population with $newLength solutions from last population with $lastLength solutions")
    }
    newPopulation
  }
  
}
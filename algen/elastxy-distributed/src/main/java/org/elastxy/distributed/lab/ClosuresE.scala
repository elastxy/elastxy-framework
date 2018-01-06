package org.elastxy.mex.encapsulated

import org.apache.log4j._
import org.elastxy.mex.partitioned.MexSolution
import org.apache.spark.util.LongAccumulator
import org.apache.spark.broadcast.Broadcast

object ClosuresE {
  val logger : Logger = Logger.getLogger("it.red")
  
  
  def era(
      era:Int,
		  initialGenomaIterator: Iterator[Int],
		  numberOfSolutions: Int,
		  maxGenerations: Int,
      fitnessTolerance:Double,
      mutationPerc:Double,
      target:Int,
      maxOperandValue:Int,
      townsTargetStatus:Option[LongAccumulator],
      mutatedGenesBC:Option[Broadcast[List[Int]]]
      ) : Iterator[MexSolution] = {
    
    logger.info(s">>> 2.1 Population Creation [era $era] 					WORKER => List[Solution]")
    val initialGenomaList: List[Int] = initialGenomaIterator.toList
    if(logger.isDebugEnabled()) {
    	val length = initialGenomaList.size
      logger.debug(s"    Starting from $length genes")
    }
    var lastPopulation: MexPopulationE = FunctionsE.createInitialPopulation(numberOfSolutions, initialGenomaList)

    var stop:Boolean = false
    var generations = 1
    while(!stop) {
    
      val best = lastPopulation.bestMatch
      logger.info(s"----------->>> Generation $generations [era $era][Last best $best] <<<-----------")
      
      logger.info(">>> 2.2 Population Selection					WORKER => List[Solution]")
      // Elitism: exclude last best match
    	var newPopulation:MexPopulationE = GeneticFunctionsE.selection(lastPopulation)
    	
      logger.info(">>> 2.3 Genetic Operators							WORKER => List[Solution]")
      if(mutatedGenesBC.isDefined){
        val mutatedGenoma = mutatedGenesBC.get.value
//    	newPopulation = GeneticFunctions.recombination(newPopulation, numberOfSolutions, recombinationPerc)
    	  newPopulation = GeneticFunctionsE.mutation(newPopulation, mutatedGenoma, numberOfSolutions, mutationPerc)
      }
    
      logger.info(">>> 2.4 Fitness Calculation						WORKER => List[Solution] WORKER => List[Best]")
      // Elitism: re-include last best match
      // TODOA: reinclude also in Era
      // TODOA: reinclude a percentage
      if(generations > 1) newPopulation = FunctionsE.reinsertBestMatch(lastPopulation, newPopulation)
      newPopulation = GeneticFunctionsE.testFitness(newPopulation, fitnessTolerance, target, maxOperandValue)
      
      logger.info(">>> 2.5 Check End Condition						WORKER => Accumulator")
      lastPopulation = newPopulation;
      if(newPopulation.goalReached){
        stop = true
        setTownsTarget(newPopulation.goalReached, townsTargetStatus)
      }
      else if(generations >= maxGenerations || checkTownsTarget(townsTargetStatus)){
        stop = true
      }
      
      val bestPrint = newPopulation.bestMatch
      logger.info(s"   End of generation [Gen: $generations][Last best $bestPrint][Stop: $stop]")
      generations += 1
    }
    
    var bestMatches = List(lastPopulation.bestMatch)
    bestMatches.iterator
  }
  
  
  def checkTownsTarget(townsTargetStatus:Option[LongAccumulator]) : Boolean = {
    townsTargetStatus.isDefined && townsTargetStatus.get.value > 0
  }

  def setTownsTarget(stop : Boolean, townsTargetStatus:Option[LongAccumulator]) {
    if(townsTargetStatus.isDefined && stop) townsTargetStatus.get.add(1)
  }
  
  
}
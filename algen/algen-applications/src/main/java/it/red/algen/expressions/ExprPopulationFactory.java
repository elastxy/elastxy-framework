/*
 * PopulationFactory.java
 *
 * Created on 4 agosto 2007, 14.08
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions;

import org.springframework.stereotype.Component;

import it.red.algen.Population;
import it.red.algen.conf.OperatorsParameters;

/**
 *
 * @author grossi
 */
@Component
public class ExprPopulationFactory {
    	
    public Population createNew(OperatorsParameters algParameters, int number) {
        ExprGenesFactory factory = new ExprGenesFactory();
        Population population = new Population(algParameters);
        for(int i = 0; i < number; i++){
            population.add(new ExprSolution(
                    factory.getNumber(0),
                    factory.getOperator('+'),
                    factory.getNumber(0)));
        }
        return population;
    }

    public Population createNewRandom(OperatorsParameters algParameters, int number) {
        ExprGenesFactory factory = new ExprGenesFactory();
        Population population = new Population(algParameters);
        for(int i = 0; i < number; i++){
            population.add(new ExprSolution(
                    factory.getRandomNumber(),
                    factory.getRandomOperator(),
                    factory.getRandomNumber()));
        }
        return population;
    }
    
    
}

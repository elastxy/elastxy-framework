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

import it.red.algen.Population;

/**
 *
 * @author grossi
 */
public class ExprPopulationFactory {
    
    public static Population createNew(int number) {
        ExprGenesFactory factory = new ExprGenesFactory();
        Population population = new Population();
        for(int i = 0; i < number; i++){
            population.add(new ExprSolution(
                    factory.getNumber(0),
                    factory.getOperator('+'),
                    factory.getNumber(0)));
        }
        return population;
    }

    public static Population createNewRandom(int number) {
        ExprGenesFactory factory = new ExprGenesFactory();
        Population population = new Population();
        for(int i = 0; i < number; i++){
            population.add(new ExprSolution(
                    factory.getRandomNumber(),
                    factory.getRandomOperator(),
                    factory.getRandomNumber()));
        }
        return population;
    }
    
    
}

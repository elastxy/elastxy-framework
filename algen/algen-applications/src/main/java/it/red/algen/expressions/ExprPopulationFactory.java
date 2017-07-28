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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.ContextSupplier;
import it.red.algen.domain.Population;
import it.red.algen.engine.PopulationFactory;

/**
 *
 * @author grossi
 */
@Component
public class ExprPopulationFactory implements PopulationFactory {
	
	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private ExprGenesFactory genesFactory;
	
	@Override
    public Population createNew() {
        Population population = new Population(contextSupplier.getContext().parameters);
        for(int i = 0; i < contextSupplier.getContext().parameters._initialSelectionNumber; i++){
        	if(contextSupplier.getContext().parameters._initialSelectionRandom){
                population.add(new ExprSolution(genesFactory));
        	}
        	else {
        		population.add(new ExprSolution(genesFactory,
        				genesFactory.getNumber(0),
        				genesFactory.getOperator('+'),
        				genesFactory.getNumber(0)));
        	}
        }
        return population;
    }

}

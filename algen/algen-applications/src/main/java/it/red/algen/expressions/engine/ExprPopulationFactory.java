/*
 * PopulationFactory.java
 *
 * Created on 4 agosto 2007, 14.08
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.ContextSupplier;
import it.red.algen.domain.Population;
import it.red.algen.engine.factories.PopulationFactory;

/**
 *
 * @author grossi
 */
@Component
public class ExprPopulationFactory implements PopulationFactory {
	
	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private ExprSolutionFactory solutionsFactory;
	
	@Override
    public Population createNew() {
        Population population = new Population();
        for(int i = 0; i < contextSupplier.getContext().parameters.initialSelectionNumber; i++){
        	if(contextSupplier.getContext().parameters.initialSelectionRandom){
                population.add(solutionsFactory.createRandom());
        	}
        	else {
        		population.add(solutionsFactory.createBaseModel());
        	}
        }
        return population;
    }

}

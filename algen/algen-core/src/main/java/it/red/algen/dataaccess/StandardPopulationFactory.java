/*
 * PopulationFactory.java
 *
 * Created on 4 agosto 2007, 14.08
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.dataaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.ContextSupplier;
import it.red.algen.domain.experiment.Population;

/**
 *
 * @author grossi
 */
@Component
public class StandardPopulationFactory implements PopulationFactory {
	
	@Autowired
	private ContextSupplier contextSupplier;
	
	@Autowired
	private SolutionsFactory solutionsFactory;
	
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
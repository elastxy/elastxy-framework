/*
 * ExpressionGenes.java
 *
 * Created on 4 agosto 2007, 14.11
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import it.red.algen.context.ContextSupplier;

/** Componenti base dell'applicazione matematica
 * @author grossi
 */
@Component
public class ExprGenesFactory {
    private static Random RANDOMIZER = new Random();
    
	@Autowired
	private ContextSupplier contextSupplier;
    
    @Cacheable(value = "exprgene")//, cacheManager = "springCM")
    public OperatorGene getOperator(Character o){
        return new OperatorGene(o);
    }
    
    public NumberGene getNumber(int n){
        return new NumberGene(n);
    }
    
    public OperatorGene getRandomOperator(){
        return new OperatorGene(RANDOMIZER.nextInt(4));
    }
    
    public NumberGene getRandomNumber(){
    	int maxOperandValue = contextSupplier.getContext().applicationSpecifics.getParamInteger(ExprConf.MAX_OPERAND_VALUE);
        return new NumberGene(RANDOMIZER.nextInt(2 * maxOperandValue + 1) - maxOperandValue); // From -max to +max
    }
}

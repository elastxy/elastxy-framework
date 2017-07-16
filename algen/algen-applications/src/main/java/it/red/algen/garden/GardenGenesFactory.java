/*
 * ExpressionGenes.java
 *
 * Created on 4 agosto 2007, 14.11
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden;
import java.util.Random;

/** Componenti base dell'applicazione matematica
 * TODO: cache dei geni
 * @author grossi
 */
public class GardenGenesFactory {
    private static Random RANDOMIZER = new Random();
    
    public OperatorGene getOperator(char o){
        return new OperatorGene(o);
    }
    
    public NumberGene getNumber(int n){
        return new NumberGene(n);
    }
    
    public OperatorGene getRandomOperator(){
        return new OperatorGene(RANDOMIZER.nextInt(4));
    }
    
    public NumberGene getRandomNumber(){
        return new NumberGene(RANDOMIZER.nextInt(10));
    }
}

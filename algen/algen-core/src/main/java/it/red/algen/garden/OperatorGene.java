/*
 * ExpressionGene.java
 *
 * Created on 4 agosto 2007, 14.13
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.garden;

import it.red.algen.Gene;
import it.red.algen.IllegalSolutionException;

/** Singolo gene dell'espressione matematica
 *
 * @author grossi
 */
public class OperatorGene implements Gene {
    private char _value;
    
    public OperatorGene(char value) {
        _value = value;
    }
    
    public OperatorGene(int value) {
        switch(value){
            case 0:
                _value = '+';
                break;
            case 1:
                _value = '-';
                break;
            case 2:
                _value = '*';
                break;
            case 3:
                _value = '/';
                break;
        }
    }
    
    public char getValue(){
        return _value;
    }
    
    public int apply(int val1, int val2) throws IllegalSolutionException {
        int result = 0;
        switch(_value){
            case '+':
                result = val1 + val2;
                break;
            case '-':
                result = val1 - val2;
                break;
            case '*':
                result = val1 * val2;
                break;
            case '/':
                if(val2==0){
                    throw new IllegalSolutionException("Divisione per zero.");
                }
                result = val1 / val2;
                break;
            default:
                throw new IllegalArgumentException("Operatore non ammesso: "+_value);
        }
        return result;
    }
    
    public String toString(){
        return String.valueOf(_value);
    }
}

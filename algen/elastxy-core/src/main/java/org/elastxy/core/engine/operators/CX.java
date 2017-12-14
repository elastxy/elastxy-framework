package org.elastxy.core.engine.operators;

import org.elastxy.core.domain.genetics.genotype.Gene;

/**
 * 
 * TODOM-8: add a good set of Recombination operators
 * 
 * @author red
 *
 */
public class CX {

    Gene[] parent1;
    Gene[] parent2;
    Gene[] offspring1;
    Gene[] offspring2;

    public CX(Gene[] parent1, Gene[] parent2){
        this.parent1 = parent1;
        this.parent2 = parent2;
        offspring1 = new Gene[parent1.length];
        offspring2 = new Gene[parent2.length];
        crossOver(offspring1, parent1, parent2);
        crossOver(offspring2, parent2, parent1);
        
    }
    

    // (1 x x 5 ) eg. element to search is 5 in 1st parent after 1 matches to 5..
    // (5 x x x )  // its position in parent 1 is 3.
    private int getPosOfSecondParentInFirst(Gene[] firstParent, Gene element){
        int position = 0;
        for(int index = 0; index < parent1.length; index++){
            if(firstParent[index].equals(element)){
               position = index;
               break;
            }
        }
        return position;
    }


    // (1 x x 5 ) eg. element to search is 1, after look for it in 2nd parent.
    // (5 x x 1 )  // 1 has already been filled so return true.
    private boolean elementInOffspring(Gene[] offspring, Gene element){
        for(int index = 0; index < offspring.length; index++){
            if(offspring[index]!=null && offspring[index].equals(element)){
                return true;
            }
        }
        return false;
    }
    

    private void crossOver(Gene[] offspring, Gene[] parentX, Gene[] parentY){
        int index = 0;
        while(!elementInOffspring(offspring, parentY[index])){
           offspring[index] = parentX[index];
           int position = getPosOfSecondParentInFirst(parentX, parentY[index]);
           offspring[position] = parentY[index];
           index = position;
        }

        for(int offspring_index = 0; offspring_index < offspring.length; offspring_index++){
            if(offspring[offspring_index] == null){
                offspring[offspring_index] = parentY[offspring_index];
            }
        }
    }
	
}

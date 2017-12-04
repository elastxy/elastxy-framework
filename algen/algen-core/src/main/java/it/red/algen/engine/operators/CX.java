package it.red.algen.engine.operators;

import it.red.algen.domain.genetics.genotype.Gene;

/**
 * TODOA: rewrite to own code!
 * @author red
 *
 */
public class CX {

    Gene[] parent1;
    Gene[] parent2;
    Gene[] offspring1;
    Gene[] offspring2;

    public CX(Gene[] parent1, Gene[] parent2){
        this.parent1 = new Gene[parent1.length];
        this.parent2 = new Gene[parent2.length];
        for(int index = 0; index < parent1.length; index ++){
           this.parent1[index] = parent1[index];
           this.parent2[index] = parent2[index];
        }
        offspring1 = new Gene[parent1.length];
        offspring2 = new Gene[parent2.length];
        for(int index = 0; index < offspring1.length; index++){
            offspring1[index] = null;
            offspring2[index] = null;
        }
        crossOver(offspring1, parent1, parent2);
        crossOver(offspring2, parent2, parent1);
        
    }
    

    // (1 x x 5 ) eg. element to search is 5 in 1st parent after 1 matches to 5..
    // (5 x x x )  // its position in parent 1 is 3.
    
    private int getPosition_ofSecondParentElement_infirstParent
                                    (Gene[] firstParent, Gene element_toSearch){
        int position = 0;
        for(int index = 0; index < parent1.length; index++){
            if(firstParent[index].equals(element_toSearch)){
               position = index;
               break;
            }
        }
        return position;
    }


    // (1 x x 5 ) eg. element to search is 1, after look for it in 2nd parent.
    // (5 x x 1 )  // 1 has already been filled so return true.

    private boolean element_already_inOffspring(Gene[] offspring, Gene element){
        for(int index = 0; index < offspring.length; index++){
            if(offspring[index]!=null && offspring[index].equals(element)){
                return true;
            }
        }
        return false;
    }
    

    private void crossOver(Gene[] offspring, Gene[] parentX, Gene[] parentY){
        int index = 0;
        while(!element_already_inOffspring(offspring, parentY[index])){
           offspring[index] = parentX[index];
           int position = getPosition_ofSecondParentElement_infirstParent
                                                      (parentX, parentY[index]);
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

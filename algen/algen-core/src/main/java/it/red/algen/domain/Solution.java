/*
 * Solution.java
 *
 * Created on 4 agosto 2007, 13.48
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain;

/**
 * TODOA: separate algorithm from domain data
 *
 * @author grossi
 */
public interface Solution {
        public Fitness getFitness();
        public void calcFitness(Target target);
        public Solution[] crossoverWith(Solution other);
        public Object clone();
        // Se ritorna una stringa, la soluzione non � valida
        public String legalCheck();
        public Object getRepresentation();
}

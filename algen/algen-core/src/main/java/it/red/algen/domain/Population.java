/*
 * Popolazione.java
 *
 * Created on 4 agosto 2007, 13.47
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.domain;
import java.util.ArrayList;
import java.util.List;

/** Contiene la popolazione di soluzioni
 * 
 * TODOA: separate algorithm from domain data
 *
 * @author grossi
 */
public class Population {
    
    public transient List<Solution> _solutions = new ArrayList<Solution>();
    public Solution bestMatch;
    
    
    public int size(){
    	return _solutions.size();
    }
    
    public void add(Solution solution){
        _solutions.add(solution);
    }
    
    
    public String toString(){
    	StringBuffer result = new StringBuffer();
    	for(Solution s : _solutions){
    		result.append(s).append("\n");
    	}
    	return result.toString();
    }
}

/*
 * ExprTarget.java
 *
 * Created on 4 agosto 2007, 14.32
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.expressions;

import it.red.algen.Target;

/**
 *
 * @author grossi
 */
public class ExprTarget implements Target {
    private int _computeValue;
    private int _distance;

    public ExprTarget(int computeValue) {
    	this._computeValue = computeValue;
    }

    public ExprTarget(int computeValue, int minValue, int maxValue) {
        _computeValue = computeValue;
        _distance = Math.max(_computeValue-minValue, maxValue-_computeValue);
    }
    
    public int getComputeValue(){
        return _computeValue;
    }
    
    public int getDistance(){
        return _distance;
    }
}

/*
 * SimpleLogger.java
 *
 * Created on 4 agosto 2007, 14.36
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.tracking;

import java.io.Serializable;

/**
 *
 * @author grossi
 */
public class SimpleLogger implements Logger, Serializable {
    public String prefix = "ALiGEN> ";
	
    public void out(Object msg){
        System.out.println(prefix()+msg);
    }
    
    public void err(Object msg, Throwable t){
    	System.out.println(prefix()+msg);
    	t.printStackTrace();
    }
    
    private final String prefix(){
    	return "["+Thread.currentThread().getId()+"] "+prefix;
    }
}

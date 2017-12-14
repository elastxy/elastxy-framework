/*
 * Logger.java
 *
 * Created on 4 agosto 2007, 14.07
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.elastxy.core.tracking;


/**
 *
 * @author grossi
 */
public interface Logger {
    public void out(Object msg);
    public void err(Object msg, Throwable t);
}

/*
 * LoggerManager.java
 *
 * Created on 5 agosto 2007, 13.17
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package it.red.algen.tracking;


/**
 *
 * @author grossi
 */
public class LoggerManager implements Logger {
    // SINGLETON
    private static LoggerManager _instance;
    public static LoggerManager instance(){
        if(_instance==null){
            _instance = new LoggerManager();
        }
        return _instance;
    }
    public LoggerManager() {
    }
    
    // INIT
    private Logger _defaultLogger;
    public void init(Logger logger){
        _defaultLogger = logger;
    }
    
    public void out(Object msg){
        if(_defaultLogger==null){
            return;
        }
        _defaultLogger.out(msg);
    }

    public void err(Object msg, Throwable t){
        if(_defaultLogger==null){
            return;
        }
        _defaultLogger.err(msg, t);
    }
}

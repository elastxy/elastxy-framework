package org.elastxy.core.tracking;


/**
 * Interface for components that needs to subscribe
 * and send messages to an EnvObserver.
 * 
 * @author red
 *
 */
public interface EnvObservable {

    public void subscribe(EnvObserver observer);
    
}

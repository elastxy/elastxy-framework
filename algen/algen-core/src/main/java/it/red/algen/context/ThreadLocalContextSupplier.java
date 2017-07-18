package it.red.algen.context;

import org.springframework.stereotype.Component;

@Component
public class ThreadLocalContextSupplier implements ContextSupplier {
	private ThreadLocal<AlgorithmContext> threadContext = ThreadLocal.withInitial(AlgorithmContext::new);
	
	@Override
	public void init(AlgorithmContext context){
		threadContext.set(context);
	}
	
	@Override
	public AlgorithmContext getContext() {
		return threadContext.get();
	}
		
	@Override
	public void destroy(){
		threadContext.remove();
	}
	
}

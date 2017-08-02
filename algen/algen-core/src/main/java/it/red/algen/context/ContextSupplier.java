package it.red.algen.context;

public interface ContextSupplier {

	public void init(AlgorithmContext context);

	public AlgorithmContext getContext();

	public void destroy();
	
}

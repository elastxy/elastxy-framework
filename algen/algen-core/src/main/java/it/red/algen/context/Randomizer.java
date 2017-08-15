package it.red.algen.context;

import java.util.Random;

public class Randomizer {
	
	// java.util.Random implementation
	private static final Random GENERATOR = new Random(7L);//ThreadLocalRandom.current();
	public static final long nextLong(long bound){
		return (long)GENERATOR.nextInt((int)bound);
	}


	// java.util.concurrent.ThreadLocalRandom implementation
//	private static final ThreadLocalRandom GENERATOR = ThreadLocalRandom.current();
	
	public static final int nextInt(int bound){
		return GENERATOR.nextInt(bound);
	}

	public static final double nextDouble(){
		return GENERATOR.nextDouble();
	}
}

package it.red.algen.engine.core;

import java.util.Random;

public class Randomizer {
	private static final long TEST_SEED = 123456L;
	
	// java.util.Random implementation
	private static final Random GENERATOR = new Random(TEST_SEED);//ThreadLocalRandom.current();
	public static final long nextLong(long bound){
		return (long)GENERATOR.nextInt((int)bound);
	}


	// java.util.concurrent.ThreadLocalRandom implementation
//	private static final ThreadLocalRandom GENERATOR = ThreadLocalRandom.current();
	
	
	public static long seed(){
		return TEST_SEED;
	}
	
	
	public static final int nextInt(int bound){
		return GENERATOR.nextInt(bound);
	}

	public static final double nextDouble(){
		return GENERATOR.nextDouble();
	}

	public static final double nextDouble(double bound){
		return GENERATOR.nextDouble() * bound;
	}
	
}

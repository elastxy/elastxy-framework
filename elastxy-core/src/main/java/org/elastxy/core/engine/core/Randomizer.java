/*******************************************************************************
 * Copyright 2018 Gabriele Rossi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.elastxy.core.engine.core;

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

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

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {
	public static final int DEFAULT_SCALE = 20;
	public static final int EQUALS_SCALE = 20;
	
	public static boolean equals(BigDecimal x, BigDecimal y){
		return equals(x,y,RoundingMode.HALF_UP);
	}

	public static boolean equals(BigDecimal x, BigDecimal y, RoundingMode r){
		return x.setScale(EQUALS_SCALE, r).compareTo(y.setScale(EQUALS_SCALE, r))==0;
	}
}

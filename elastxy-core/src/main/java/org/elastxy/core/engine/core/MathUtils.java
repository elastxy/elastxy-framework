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

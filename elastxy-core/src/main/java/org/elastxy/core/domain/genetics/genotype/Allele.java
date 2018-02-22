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
package org.elastxy.core.domain.genetics.genotype;

import java.io.Serializable;

/**
 * Represents the value hosted by a single gene in the genotype.
 * @author red
 *
 * @param <T>
 */
public class Allele<T> implements Serializable {
	
	public T value;
	
	public boolean dominant;
	
	public Allele(){}
	
	public Allele(T value){
		this.value = value;
	}
	
	public String encode(){
		return value.toString(); // TODO3-2: encoding based on type
	}

	public Allele<T> copy(){
		Allele<T> result = new Allele<T>();
		result.value = value;
		result.dominant = dominant;
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Allele other = (Allele) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return value==null?"":value.toString();
	}

}

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
package org.elastxy.core.domain.genetics.phenotype;

public class UserPhenotype<U> implements Phenotype<U> {
	public U value;
	
	@Override
	public U getValue() {
		return value;
	}
	
	
	public UserPhenotype<U> copy(){
		UserPhenotype<U> result = new UserPhenotype<U>();
		result.value = value;
		return result;
	}


	@Override
	public String toString() {
		return String.format("(UserPhenotype) %s", value==null?"N/A":value.toString());
	}
}

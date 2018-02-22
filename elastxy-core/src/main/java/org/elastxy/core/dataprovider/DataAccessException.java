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
package org.elastxy.core.dataprovider;

/**
 * Exception thrown in case of any data access exception.
 * 
 * @author red
 *
 */
public class DataAccessException extends RuntimeException {
	public DataAccessException(String msg){
		super(msg);
	}
	public DataAccessException(String msg, Throwable initCause){
		super(msg, initCause);
	}
}

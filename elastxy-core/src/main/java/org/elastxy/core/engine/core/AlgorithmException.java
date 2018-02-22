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

/**
 * Generic error encountered during algorithm execution.
 * 
 * @author red
 *
 */
public class AlgorithmException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AlgorithmException(String msg) {
        super(msg);
    }

	public AlgorithmException(String msg, Throwable t) {
        super(msg, t);
    }
}

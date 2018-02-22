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
 * Checked exception to be managed in case of
 * problems during solutions growth.
 * 
 * TODO3-2: define better structure for validation rule and breaks
 * 
 * @author grossi
 */
public class IllegalSolutionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String legalCheck = "No legal check details."; // TODO0-8: i18n, i10n

	public IllegalSolutionException(String msg) {
        super(msg);
    }

	public IllegalSolutionException(String msg, String legalCheck) {
        super(msg);
        this.legalCheck = legalCheck;
    }
	
	
	/**
	 * Legal check hosts details on problem occourred.
	 * @return
	 */
	public String getLegalCheck(){
		return legalCheck;
	}
}

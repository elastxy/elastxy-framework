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
package org.elastxy.core.engine.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneMetadata {
	
	/**
	 * Unique code of the metadata
	 */
	public String code;
	
	/**
	 * Short description of the metadata
	 */
	public String name;
	
	/**
	 * Metadata content type of Allele
	 */
	public GeneMetadataType type;
	
	/**
	 * List of admitted possible values, of the specified metadata type.
	 * 
	 * If content is big, the name of a AlleleValuesProvider could be 
	 * provided instead in the valuesProvider property.
	 */
	public List values = new ArrayList();

	/**
	 * Name of the Provider for Allele values,
	 * to be retrieved in the registry held by Genoma.
	 */
	public String valuesProvider;
	
	/**
	 * The minimum value based on type, if sortable
	 * @return
	 */
	public Object min;
	
	/**
	 * The maximum value based on type, if sortable
	 * @return
	 */
	public Object max;

	
//	/**
//	 * After the Gene is given a new Allele in the Genoma Provider, cannot be modified further
//	 */
//	public boolean blocked;
	
	/**
	 * User defined structure to be used in application specific logics,
	 * preferably a short serializable YAML/JSON representation, or a small binary content.
	 */
	public Object userStructure;
	
	/**
	 * User defined fixed properties for this metadata gene.
	 * 
	 * E.g. location properties for a garden position "sun, wet, wind, ..."
	 * 
	 */
	public Map<String,Object> userProperties = new HashMap<String,Object>();

	
	public String toString(){
		return String.format("GeneMetadata:code=%s,name=%s,type=%s", code, name, type);
	}
	

}

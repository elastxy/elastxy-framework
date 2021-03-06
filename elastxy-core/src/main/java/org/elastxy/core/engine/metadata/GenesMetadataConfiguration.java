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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Genes pojo for reading configuration metadata.
 * @author red
 *
 */
public class GenesMetadataConfiguration {
	public Map<String, GeneMetadata> metadata = new HashMap<String, GeneMetadata>(); 
	public Map<String, List<String>> positions = new HashMap<String, List<String>>(); 
}

package it.red.algen.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Genes {
	public Map<String, GeneMetadata> metadata = new HashMap<String, GeneMetadata>(); 
	public Map<String, List<String>> positions = new HashMap<String, List<String>>(); 
}

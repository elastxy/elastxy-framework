package it.red.algen.conf;

import java.util.HashMap;
import java.util.Map;

public class CustomParameters {
	public Map<String, Object> params = new HashMap<String, Object>();
	
	public String getString(String parKey){
		return params.get(parKey).toString();
	}
	
	public Integer getInteger(String parKey){
		Integer result = null;
		Object parValue = params.get(parKey);
		if(parValue instanceof Number) {
			result = ((Number)parValue).intValue();
		}
		else if(parValue instanceof String){
			result = Integer.parseInt((String) parValue);
		}
		return result;
	}
	
	public void put(String key, Object value){
		params.put(key, value);
	}

}

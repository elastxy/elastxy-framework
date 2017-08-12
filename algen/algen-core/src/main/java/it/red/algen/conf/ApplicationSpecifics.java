package it.red.algen.conf;

import java.util.HashMap;
import java.util.Map;



/**
 * Generic format specifications passed to REST algorithm services
 * 
 * @author red
 */
public class ApplicationSpecifics {
	
	// TODOM: inheritance
	// http://www.baeldung.com/jackson-inheritance
	public Map<String, Object> target = new HashMap<String, Object>();
	public Map<String, Object> params = new HashMap<String, Object>();

	
	public String getTargetString(String parKey){
		return getString(target, parKey);
	}

	public Integer getTargetInteger(String parKey){
		return getInteger(target, parKey);
	}

	public Long getTargetLong(String parKey){
		return getLong(target, parKey);
	}
	
	public void putTarget(String key, Object value){
		target.put(key, value);
	}
	
	
	
	public String getParamString(String parKey){
		return getString(params, parKey);
	}

	public Integer getParamInteger(String parKey){
		return getInteger(params, parKey);
	}
	
	public Long getParamLong(String parKey){
		return getLong(params, parKey);
	}
	
	private String getString(Map<String, Object> map, String parKey){
		return map.get(parKey).toString();
	}
	
	public Integer getInteger(Map<String, Object> map, String parKey){
		Integer result = null;
		Object parValue = map.get(parKey);
		if(parValue instanceof Number) {
			result = ((Number)parValue).intValue();
		}
		else if(parValue instanceof String){
			result = Integer.parseInt((String) parValue);
		}
		return result;
	}

	
	public Long getLong(Map<String, Object> map, String parKey){
		Long result = null;
		Object parValue = map.get(parKey);
		if(parValue instanceof Number) {
			result = ((Number)parValue).longValue();
		}
		else if(parValue instanceof String){
			result = Long.parseLong((String) parValue);
		}
		return result;
	}
	
	public void putParam(String key, Object value){
		params.put(key, value);
	}

}

package it.red.algen.conf;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Generic format specifications passed to REST algorithm services
 * 
 * @author red
 */
public class ApplicationSpecifics implements Serializable {
	
	// TODOM-4: Jackson inheritance
	// http://www.baeldung.com/jackson-inheritance
	public Map<String, Object> target = new HashMap<String, Object>();
	public Map<String, Object> params = new HashMap<String, Object>();



	public Boolean getTargetBoolean(String parKey, Boolean defaultValue){
		Boolean result = getBoolean(target, parKey);
		return result==null ? defaultValue : result;
	}
	
	public String getTargetString(String parKey){
		return getString(target, parKey);
	}

	public Integer getTargetInteger(String parKey){
		return getInteger(target, parKey);
	}

	public Integer getTargetInteger(String parKey, Integer defaultValue){
		Integer result = getInteger(target, parKey);
		return result==null ? defaultValue : result;
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
	
	public String getParamString(String parKey, String defaultValue){
		String result = getString(params, parKey);
		return result==null ? defaultValue : result;
	}
	
	public Boolean getParamBoolean(String parKey){
		return getBoolean(params, parKey);
	}
	
	public Boolean getParamBoolean(String parKey, Boolean defaultValue){
		Boolean result = getBoolean(params, parKey);
		return result==null ? defaultValue : result;
	}
	
	public Integer getParamInteger(String parKey){
		return getInteger(params, parKey);
	}

	public Integer getParamInteger(String parKey, Integer defaultValue){
		Integer result = getInteger(params, parKey);
		return result==null ? defaultValue : result;
	}
	
	public Long getParamLong(String parKey, Long defaultValue){
		Long result = getLong(params, parKey);
		return result==null ? defaultValue : result;
	}
	
	public Long getParamLong(String parKey){
		return getLong(params, parKey);
	}

	public List<String> getParamList(String parKey){
		return getList(params, parKey);
	}
	
	private String getString(Map<String, Object> map, String parKey){
		return map.get(parKey).toString();
	}

	
	private Boolean getBoolean(Map<String, Object> map, String parKey){
		Boolean result = null;
		Object parValue = map.get(parKey);
		if(parValue instanceof String) {
			result = Boolean.parseBoolean((String)parValue);
		}
		else if(parValue instanceof Boolean){
			result = (Boolean)parValue;
		}
		return result;
	}
	
	private Integer getInteger(Map<String, Object> map, String parKey){
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

	
	private Long getLong(Map<String, Object> map, String parKey){
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


	private List<String> getList(Map<String, Object> map, String parKey){
		List<String> result = null;
		Object parValue = map.get(parKey);
		if(parValue instanceof List) {
			result = (List)parValue;
		}
		else if(parValue instanceof String[]){
			result = Arrays.asList((String[])parValue);
		}
		return result;
	}
	
	public void putParam(String key, Object value){
		params.put(key, value);
	}

}

package org.elastxy.core.tracking;

/**
 * // TODOM-2: exceptions management
 * @author red
 *
 */
public enum ErrorCode {
	NO_ERROR(0), 
	ERROR(-1);
	
	private int code = 0;
	
	ErrorCode(int code){
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
}

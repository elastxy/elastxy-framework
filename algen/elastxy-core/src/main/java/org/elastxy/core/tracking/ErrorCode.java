package org.elastxy.core.tracking;

/**
 * TODOA-4: proper error management, now it's generic
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

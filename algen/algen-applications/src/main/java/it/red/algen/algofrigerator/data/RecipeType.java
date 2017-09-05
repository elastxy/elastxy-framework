package it.red.algen.algofrigerator.data;

public enum RecipeType {
	SAVOURY("SA", 0),SWEET("SW", 1),NEUTRAL("NE", 2);
	
	private String code;
	private int order;
	
	private RecipeType(String code, int order){
		this.code = code;
		this.order = order;
	}
	
	public String getCode(){
		return code;
	}

	public Integer order(){
		return order;
	}
	
	public static RecipeType fromCode(String code){
		RecipeType result = null;
		switch(code){
			case "SA": result = SAVOURY; 	break;
			case "SW": result = SWEET; 		break;
			case "NE": result = NEUTRAL; 	break;
		}
		return result;
	}

}

package it.red.algen.algofrigerator.data;

// TODOA: move partial points to fitness calculator
public enum IngredientsCoverage {
	
	FULL_MAIN_INGR(1.0, 0), 
	PARTIAL_MAIN_INGR(0.8, 1), 
	
	FULL(0.6, 2),
	PARTIAL(0.4, 3), 
	
	ONLY_MAIN_INGR(0.2, 4),
	
	NONE(0.0, 5),
	
	UNDEFINED(-1.0,-1);
	
	private double points = 0.0;
	private Integer order = 0;
	
	private IngredientsCoverage(double points, Integer order){
		this.points = points;
		this.order = order;
	}
	
	public double getPoints(){
		return points;
	}

	public Integer order(){
		return order;
	}
}

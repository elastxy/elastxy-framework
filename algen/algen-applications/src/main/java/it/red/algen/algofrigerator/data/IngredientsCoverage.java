package it.red.algen.algofrigerator.data;

public enum IngredientsCoverage {
	UNDEFINED(-1.0,-1), FULL(1.0, 0), PARTIAL(0.5, 1), NONE(0.0, 2);
	
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

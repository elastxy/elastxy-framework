package it.red.algen.algofrigerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MefGoal implements Serializable {
	public List<String> refrigeratorFoods = new ArrayList<String>();
	public List<String> pantry = new ArrayList<String>();
	public int desiredMeals = 0;
	public boolean fridgeMandatory = false;
	
	public int savouryMeals = 0;
	public int sweetMeals = 0;
	
	public String toString(){
		return String.format("Goal: %d meals from %d foods from refrigerator. Mandatory fridge foods: %b", desiredMeals, refrigeratorFoods.size(), fridgeMandatory);
	}
}

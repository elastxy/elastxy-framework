//package it.red.algen.algofrigerator;
//
//import java.util.List;
//
//import it.red.algen.algofrigerator.data.RecipeType;
//import it.red.algen.domain.experiment.Target;
//
//public class MefWorkingDataset {
//
//	public void shrink(Target<?, ?> target){
//
//		// Goal
//		MefGoal goal = (MefGoal)target.getGoal();
//		List<String> fridgeFoods = goal.refrigeratorFoods;
//		logger.debug("Requested "+goal.refrigeratorFoods.size()+" foods from refrigerator and "+goal.pantry+" foods from pantry.");
//		
//		// Restricts to feasible recipes and collecting detailed info on coverage
//		logger.debug("Restricting recipes to those feasible with given ingredients.");
//		MefWorkingDataset dataset = new MefWorkingDataset();
//		restrictToFeasible(dataset, fridgeFoods, goal.pantry);
//		for(RecipeType type : RecipeType.values()){
//			logger.debug(type+" type restricted to "+dataset.feasibleByType.get(type).size()+" recipes.");
//		}
//	}
//	
//}

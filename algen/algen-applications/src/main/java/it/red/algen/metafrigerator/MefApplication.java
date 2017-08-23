package it.red.algen.metafrigerator;

/**
 * 
 * Algorifero
 * 
 * Target: fill the maximum number of receipts with ingredients
 * from refrigerator. Receipts must be X% salted, Y% sweet (multi-goal problem).
 * 
 * Solution phenotype: the complete list of sweet or salted receipts, 
 * 		wit some of them feasible or almost feasible with the ingredients from refrigerator.
 * 		Receipts with no ingredients are not taken into account.
 * 
 * Fitness: maximize the number of filled receipts, proportionally between salted and sweet.
 * 		Formula: maximize (X% * Number of salted + Y% * Number of sweet
 * 
 * Solution genotype: two chromosomes, one for sweet and one for salted receipts.
 * 		Every gene is a part of receipt, and hosts one allele as an ingredient from refrigerator, 
 * 		in predefined measure.
 * 		Ingredients may be suitable for salted, sweet or both receipts.
 * 		When ingredient is suitable for both, should be splitted proportionally between
 * 		sweet and salted chromosomes.
 * 
 * TODOA: remove redundancy with other applications
 * @author red
 *
 */
public class MefApplication {
	
	// Application name
	public static final String APP_NAME = "algorifero";
	
	// Target
	// Not set: as much as possible filled receipts!	

	// Custom inputs
	public static final String TARGET_SALTED_PROPORTION 	= "TARGET_SALTED_PROPORTION";
	public static final String TARGET_SWEET_PROPORTION 		= "TARGET_SWEET_PROPORTION";

}
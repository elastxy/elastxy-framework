package it.red.algen.metasudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.PredefinedGenoma;
import it.red.algen.domain.genetics.genotype.Allele;


/**
 * 
 * Positions in sudoku matrix are: 
 * 	first row: 	0 to 8 (incl.)
 *  second row: 9 to 17 (incl.)
 *  ..
 *  ninth row: 72 to 80 (incl.)
 *  
 *  The genoma is made up of F free cells out of the 81 total, so that the 
 *  possible values are the zero valued positions of the goal matrix.
 *  In other words, the set of numbers of a finished matrix, minus the non-zero positions.
 *  
 *  Set of alleles is F in number, and limited when creating a new solution
 *  
 * TODOM: cache!
 * @author red
 *
 */
public class MesGenomaProvider implements GenomaProvider {
	public static final String ALLELE_CELL = "cell";

	private AlgorithmContext context;

	@Override
	public void setup(AlgorithmContext context) {
		this.context = context;
	}
	
	public static final List<Integer> COMPLETE_NUMBERS = new ArrayList<Integer>();
	static {
		// Filled numbers
		for(int n=1; n<10; n++){
			for(int i=0; i<9; i++){
				COMPLETE_NUMBERS.add(n);
			}
		}
	}
	

	/**
	 * Genoma is intially void: only when target is set can be set up by reduce()
	 */
	@Override
	public Genoma getGenoma(){
		return null;
//		throw new UnsupportedOperationException("Cannot get a new Genoma: it's completely based on target. Use reduce() and maintain the reference for all execution instead.");
	}

	
	/**
	 * Genoma is intially void: only when target is set can be set up by reduce()
	 */
	@Override
	public void collect() {
	}

	
	/**
	 * Create a new restricted Genoma for single execution context,
	 * with only free cells number
	 */
	@Override
	public Genoma shrink(Target<?, ?> target) {

		PredefinedGenoma genoma = new PredefinedGenoma();
		genoma.setLimitedAllelesStrategy(true); // after determined, alleles are always the same set for every solution
		
		int[][] matrix = (int[][])target.getGoal();

		// Count free cells and missing numbers
		List<Integer> missingNumbers = new ArrayList<Integer>(COMPLETE_NUMBERS);
//		List<Integer> filledNumbers = new ArrayList<Integer>();
		for(int r=0; r < matrix.length; r++){
        	for(int c=0; c < matrix.length; c++){
        		if(matrix[r][c]!=0) missingNumbers.remove(new Integer(matrix[r][c]));
        	}    		
    	}
		
		// Create restricted genoma
		Map<String, List<Allele>> alleles = new HashMap<String, List<Allele>>();
		List<Allele> predefinedAlleles = new ArrayList<Allele>();
		for(int i=0; i < missingNumbers.size(); i++){
			Allele<Integer> allele = new Allele<Integer>();
			allele.value = missingNumbers.get(i);
			predefinedAlleles.add(allele);
		}
		for(int i=0; i < missingNumbers.size(); i++) {
			alleles.put(String.valueOf(i), predefinedAlleles);
		}
		
		genoma.initialize(alleles);
		
		return genoma;
	}

	
	
}

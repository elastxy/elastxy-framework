package it.red.algen.metasudoku;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.red.algen.dataaccess.GenomaProvider;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.PredefinedGenoma;


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
 * TODOA: cache!
 * @author red
 *
 */
@Component
public class MesGenomaProvider implements GenomaProvider {
	public static final String ALLELE_CELL = "cell";
	
	public static final List<Integer> COMPLETE_NUMBERS = new ArrayList<Integer>();
	static {
		// Filled numbers
		for(int n=1; n<10; n++){
			for(int i=0; i<9; i++){
				COMPLETE_NUMBERS.add(n);
			}
		}
	}
	
	private PredefinedGenoma cachedGenoma;
	
	@Override
	public Genoma getGenoma(){
		if(cachedGenoma==null){
			collect();
		}
		return cachedGenoma;
	}

	
	/**
	 * Genoma is intially void: only when target is set can be set up
	 */
	@Override
	public Genoma collect() {
		cachedGenoma = new PredefinedGenoma();
		cachedGenoma.setLimitedAllelesStrategy(true); // after determined, alleles are always the same set for every solution
		return cachedGenoma;
		
//		cachedGenoma.genesMetadataByCode = new HashMap<String, GeneMetadata>();
//
//		// Free to modify positions
//		GeneMetadata gene = new GeneMetadata();
//		gene.code = ALLELE_CELL;
//		gene.name = ALLELE_CELL;
//		gene.type = GeneMetadataType.INTEGER;
//		gene.blocked = false;
//		gene.values = IntStream.rangeClosed(0, 9).boxed().map(i -> i).collect(Collectors.toList());
//		cachedGenoma.genesMetadataByCode.put(gene.code, gene);
//
//    	int cell=0;
//    	for(int r=0; r < matrix.length; r++){
//        	for(int c=0; c < matrix.length; c++){
//        		GeneMetadata m = matrix[r][c]==0 ? gene : gene; // TODOA: blocked
//        		cachedGenoma.genesMetadataByPos.put(String.valueOf(cell++), m);
//        	}    		
//    	}
//		
//		return cachedGenoma;
	}

	/**
	 * Create a new restricted Genoma for single execution,
	 * with only free cells number
	 * 
	 * TODOA: next executions will found alleles already reduced 
	 * based on this target execution!!!
	 * Must separate runtime from initial genoma setup
	 */
	@Override
	public void reduce(Target<?, ?> target) {
		
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
		List<Allele> predefinedAlleles = new ArrayList<Allele>();
		for(int i=0; i < missingNumbers.size(); i++){
			Allele<Integer> allele = new Allele<Integer>();
			allele.value = missingNumbers.get(i);
			predefinedAlleles.add(allele);
		}
		for(int i=0; i < missingNumbers.size(); i++) {
			cachedGenoma.alleles.put(String.valueOf(i), predefinedAlleles);
		}
	}

	
	
}

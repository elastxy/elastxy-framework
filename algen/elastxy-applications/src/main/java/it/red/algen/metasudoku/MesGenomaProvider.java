package it.red.algen.metasudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataprovider.GenomaProvider;
import it.red.algen.dataprovider.WorkingDataset;
import it.red.algen.domain.experiment.Target;
import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.engine.genetics.PredefinedGenoma;
import it.red.algen.engine.genetics.PredefinedGenomaBuilder;


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
 * TODOM-2: cache!
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

	@Override
	public void setWorkingDataset(WorkingDataset workingDataset) {
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
		SudokuShrinkCommand cmd = new SudokuShrinkCommand((int[][])target.getGoal());
		cmd.execute();
		PredefinedGenoma genoma = PredefinedGenomaBuilder.build(cmd.getMissingNumbers().size(), cmd.getPredefinedAlleles(), true);
		return genoma;
	}

	
}

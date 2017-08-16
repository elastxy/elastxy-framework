package it.red.algen.metasudoku;

import java.util.Arrays;
import java.util.List;

import it.red.algen.domain.genetics.Gene;
import it.red.algen.domain.genetics.NumberPhenotype;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.engine.Incubator;

public class MesIncubator implements Incubator<SequenceGenotype, NumberPhenotype>{
	private static final int[] COMPLETED_VALUES = new int[]{1,2,3,4,5,6,7,8,9};
	
	/**
	 * Solution grows to a complete Sudoku matrix: total number of fitting rows/square must be 27
	 */	
	@Override
	public NumberPhenotype grow(SequenceGenotype genotype) {
		NumberPhenotype result = new NumberPhenotype();
		int[][] matrix = convertToMatrix(genotype.genes);
		result.value = countCompleteRowsSquares(matrix);
		return result;
	}
	
	public static int[][] convertToMatrix(List<Gene> genes){
		int size = 9;
		int[][] matrix = new int[size][size];
		for (int i = 0; i < genes.size(); i++) {
			matrix[i / size][i % size] = (int)genes.get(i).allele.value;
		}
		return matrix;
	}
	
	
	private int countCompleteRowsSquares(int[][] matrix){
		int totalSuccessful = 0;
		
		// Rows
		for(int c=0; c < matrix.length; c++){
			int[] column = new int[matrix.length];
			for(int r=0; r < matrix.length; r++){
				column[r] = matrix[r][c];
			}
			Arrays.sort(column);
			if(Arrays.equals(column, COMPLETED_VALUES)) totalSuccessful++;
		}
		
		// Columns
		for(int r=0; r < matrix.length; r++){
			int[] row = matrix[r].clone();
			Arrays.sort(row);
			if(Arrays.equals(row, COMPLETED_VALUES)) totalSuccessful++;
		}
		
		
		// Squares
		for(int s=0; s < 3; s++){
			int[] values = new int[9];
			int i=0;
			for(int r=s*3; r < s*3+3; r++){
				for(int c=s*3; c < s*3+3; c++){
					values[i++] = matrix[r][c];
				}
			}
			Arrays.sort(values);
			if(Arrays.equals(values, COMPLETED_VALUES)) totalSuccessful++;
		}
		
		return totalSuccessful;
	}

}

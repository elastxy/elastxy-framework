package it.red.algen.metasudoku;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	
	
	/**
	 * Fitness raw for every row/column/square:
	 *  - 1.0: if completed
	 *  OR
	 * 	- 0.05: for every distinct value beyond 1
	 * 	OR
	 *  TODOM- 0.01: if sum is 44,45,46
	 * 
	 * @param matrix
	 * @return
	 */
	private double countCompleteRowsSquares(int[][] matrix){
		double totalPoints = 0;
		
		// Rows
		for(int c=0; c < matrix.length; c++){
			int[] column = new int[matrix.length];
			for(int r=0; r < matrix.length; r++){
				column[r] = matrix[r][c];
			}
			Arrays.sort(column);
			totalPoints = calculateElementPoints(totalPoints, column);
		}
		
		// Columns
		for(int r=0; r < matrix.length; r++){
			int[] row = matrix[r].clone();
			Arrays.sort(row);
			totalPoints = calculateElementPoints(totalPoints, row);
		}
		
		
		// Squares
		
		// maxi row
		for(int sr=0; sr < 3; sr++){
			
			// maxi column
			for(int sc=0; sc < 3; sc++){

				// 3x3 square
				int[] values = new int[9];
				int i=0;
				for(int r=sr*3; r < sr*3+3; r++){
					for(int c=sc*3; c < sc*3+3; c++){
						values[i++] = matrix[r][c];
					}
				}
				Arrays.sort(values);
				totalPoints = calculateElementPoints(totalPoints, values);
			}
		}
		
		return totalPoints;
	}

	private double calculateElementPoints(double totalPoints, int[] values) {
		if(Arrays.equals(values, COMPLETED_VALUES)) totalPoints++;
		else {
			Set<Integer> ints = Arrays.stream(values).boxed().collect(Collectors.toSet());
			int distinct = ints.size();
			totalPoints += distinct * 0.1;
		}
		return totalPoints;
	}

}

package it.red.algen.metasudoku;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.red.algen.domain.experiment.Env;
import it.red.algen.domain.genetics.ComplexPhenotype;
import it.red.algen.domain.genetics.Gene;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.engine.Incubator;

public class MesIncubator implements Incubator<SequenceGenotype, ComplexPhenotype>{
	private static final int[] COMPLETED_VALUES = new int[]{1,2,3,4,5,6,7,8,9};
	
	/**
	 * Solution grows to a complete Sudoku matrix filled with numbers,
	 * where total number of fitting rows/square could be 27.
	 * 
	 * Incubator starts from the genotype representing missing values,
	 * and fills the original matrix
	 */	
	@Override
	public ComplexPhenotype grow(SequenceGenotype genotype, Env env) {
		ComplexPhenotype result = new ComplexPhenotype();
		int[][] matrix = fillMatrix((int[][])env.target.getGoal(), genotype.genes);
		double completeness = countCompleteRowsSquares(matrix);
		result.value.put(MesApplication.PHENOTYPE_MATRIX, matrix);
		result.value.put(MesApplication.PHENOTYPE_COMPLETENESS, completeness);
		return result;
	}
	
	public static int[][] fillMatrix(int[][] targetMatrix, List<Gene> genes){
		int size = targetMatrix.length;
		int[][] matrix = new int[size][size];
		int i=0;
		for(int r=0; r < size; r++){
			for(int c=0; c < size; c++){
				int targetValue = targetMatrix[r][c];
				matrix[r][c] = targetValue==0 ? (int)genes.get(i++).allele.value : targetValue;
			}
		}
//		for (int i = 0; i < genes.size(); i++) {
//			matrix[i / size][i % size] = (int)genes.get(i).allele.value;
//		}
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

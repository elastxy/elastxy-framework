package org.elastxy.app.metasudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.elastxy.core.domain.experiment.Env;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.genotype.Gene;
import org.elastxy.core.domain.genetics.phenotype.ComplexPhenotype;
import org.elastxy.core.engine.core.IllegalSolutionException;
import org.elastxy.core.engine.fitness.Incubator;
import org.elastxy.core.engine.operators.MutatorLogics;

public class MesIncubator implements Incubator<Chromosome, ComplexPhenotype>{
	private static Logger logger = Logger.getLogger(MesIncubator.class);
	
	private static final int[] COMPLETED_VALUES = new int[]{1,2,3,4,5,6,7,8,9};
	
	/**
	 * Solution grows to a complete Sudoku matrix filled with numbers,
	 * where total number of fitting rows/square could be 27.
	 * 
	 * Incubator starts from the genotype representing missing values,
	 * and fills the original matrix.
	 * 
	 * TODOA-2: bug. Check consistecy of genotype: sometimes duplicate values! 
	 */	
	@Override
	public ComplexPhenotype grow(Chromosome genotype, Env env) {
		int[][] goal = (int[][])env.target.getGoal();

		// TODOM-2: create a ConsistencyChecker for Incubator and other components
//		if(logger.isDebugEnabled()){
//		checkGoal(goal, genotype.genes);
//		}
		
		ComplexPhenotype result = new ComplexPhenotype();
		int[][] matrix = fillMatrix(goal, genotype.genes);
	
		double completeness = countCompleteRowsSquares(matrix);
		result.value.put(MesConstants.PHENOTYPE_MATRIX, matrix);
		result.value.put(MesConstants.PHENOTYPE_COMPLETENESS, completeness);
		return result;
	}
	
	public static int[][] fillMatrix(int[][] targetMatrix, List<Gene> genesInput){
		
		List<Gene> genes = new ArrayList<Gene>();
		for(Gene g : genesInput){ genes.add(g.copy()); }
		
		int size = targetMatrix.length;
		int[][] matrix = new int[size][size];
		int i=0;
		for(int r=0; r < size; r++){
			for(int c=0; c < size; c++){
				int targetValue = targetMatrix[r][c];
				matrix[r][c] = targetValue==0 ? (int)genes.get(i++).allele.value : targetValue;
			}
		}

//		checkConsistency(matrix);
		
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
	
	
//	private static void checkConsistency(int[][] matrix){
//		List<Integer> actual = new ArrayList<Integer>();
//		for(int i=0; i < matrix.length; i++) {
//			for(int j=0; j < matrix.length; j++){
//				actual.add(matrix[i][j]);
//			}
//		}
//		
//		// Removes all first occurrences of legal numbers, and leaves duplicates, if any
//		for(int i=0; i < COMPLETED_VALUES.length; i++) {
//			for(int j=0; j < COMPLETED_VALUES.length; j++){
//				actual.remove(new Integer(COMPLETED_VALUES[j]));
//			}
//		}
//		
//		if(!actual.isEmpty()){
//			throw new IllegalSolutionException("Error while growing solution.", "A number of duplicates was found in the resulting phenotype:"+actual);
//		}
//	}
//	
//	private static void checkGoal(int[][] goal, List<Gene> genes){
//		int[][] matrix = new int[9][9];
//		List<Integer> actual = new ArrayList<Integer>();
//		int count = 0;
//		for(int i=0; i < matrix.length; i++) {
//			for(int j=0; j < matrix.length; j++){
//				if(goal[i][j]==0) matrix[i][j] = ((Integer)genes.get(count++).allele.value);
//				else matrix[i][j] = goal[i][j];
//			}
//		}
//		
//		// Removes all first occurrences of legal numbers, and leaves duplicates, if any
//		for(int i=0; i < COMPLETED_VALUES.length; i++) {
//			for(int j=0; j < COMPLETED_VALUES.length; j++){
//				if(j==8) actual.remove(new Integer(0));
//			}
//		}
//		
//		if(!actual.isEmpty()){
//			throw new IllegalSolutionException("Error while growing solution.", "Wrong goal matrix:"+actual);
//		}
//	}

}

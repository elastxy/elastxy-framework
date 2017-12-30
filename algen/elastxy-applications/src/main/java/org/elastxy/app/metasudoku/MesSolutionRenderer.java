package org.elastxy.app.metasudoku;

import org.apache.log4j.Logger;
import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.phenotype.ComplexPhenotype;
import org.elastxy.core.tracking.SolutionRenderer;

public class MesSolutionRenderer implements SolutionRenderer<String> {
	public static Logger logger = Logger.getLogger(MesSolutionRenderer.class);
	
			
	@Override
	public String render(Solution solution){
		StringBuffer sb = new StringBuffer(81);
		ComplexPhenotype phenotype = (ComplexPhenotype)solution.getPhenotype();
		if(phenotype==null){
			logger.error("Cannot render solution. Phenotype null for solution: "+solution);
			return "No phenotype to visualize.";
		}
		
//		TODO3-2: mes: show changed values in square brackets ((SequenceGenotype)solution.getGenotype()).genes.get(index);
		int[][] matrix = (int[][])phenotype.getValue().get(MesConstants.PHENOTYPE_MATRIX);
		
		int[] columnTotals = new int[9];
		for (int i=0; i < 9; i++) {
			int rowTotal = 0;
			for (int j=0; j < 9; j++) {
				String cell = null;
				if(matrix[i][j]==0){
					cell = "   ";
				}
				else {
					cell = String.format(" %d ", matrix[i][j]);
				}
				sb.append(cell);
				rowTotal += matrix[i][j];
				columnTotals[j] += matrix[i][j];
			}
			sb.append(String.format("__%2d%n", rowTotal));
		}
		for (int c=0; c < 9; c++) {
			sb.append(" | ");
		}
		sb.append("\n");
		for (int c=0; c < 9; c++) {
			sb.append(String.format(" %2d", columnTotals[c]));
		}
		sb.append("\n\n");
		double completeness = (double)phenotype.getValue().get(MesConstants.PHENOTYPE_COMPLETENESS);
		sb.append(String.format("-> Sudoku completeness: %.3f%n%n", completeness));
		sb.append("SOLUTION: "+solution+"\n");
		return sb.toString();
	}
	
}

package it.red.algen.metasudoku;

import it.red.algen.domain.experiment.Solution;
import it.red.algen.domain.genetics.SequenceGenotype;
import it.red.algen.tracking.SolutionRenderer;

public class MesSolutionRenderer implements SolutionRenderer<String> {

	@Override
	public String render(Solution solution){
		StringBuffer sb = new StringBuffer(81);
		SequenceGenotype genotype = (SequenceGenotype)solution.getGenotype();
		int[][] matrix = MesIncubator.convertToMatrix(genotype.genes);
		
		int[] columnTotals = new int[9];
		for (int i=0; i < 9; i++) {
			int rowTotal = 0;
			for (int j=0; j < 9; j++) {
				sb.append(matrix[i][j]==0 ? "   " : String.format(" %d ", matrix[i][j]));
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
		sb.append(solution+"\n");
		return sb.toString();
	}
	
}

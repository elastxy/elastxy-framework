package org.elastxy.app.metasudoku;

import org.elastxy.core.domain.experiment.Solution;
import org.elastxy.core.domain.genetics.genotype.Chromosome;
import org.elastxy.core.domain.genetics.phenotype.ComplexPhenotype;
import org.elastxy.core.domain.genetics.phenotype.NumberPhenotype;
import org.elastxy.core.tracking.DefaultResultsRenderer;
import org.elastxy.core.tracking.SolutionRenderer;

/**
 * Simple: HTML Table for client-side representation
 * 
 * @author red
 *
 */
public class MesResultsRenderer extends DefaultResultsRenderer {

	@Override
	public void setSolutionRenderer(SolutionRenderer solutionRenderer) {
		super.setSolutionRenderer(new HTMLSolutionRenderer());
	}
	
	private static class HTMLSolutionRenderer implements SolutionRenderer<String> {


		@Override
		public String render(Solution solution){
			StringBuffer sb = new StringBuffer(324);
			ComplexPhenotype phenotype = (ComplexPhenotype)solution.getPhenotype();
			
			int[][] matrix = (int[][])phenotype.getValue().get(MesConstants.PHENOTYPE_MATRIX);
			
			int[] columnTotals = new int[9];
			// Start table
			sb.append("<table class='sudoku-table'>");
			// Columns
			for (int i=0; i < 9; i++) {
				sb.append("<tr>");
				int rowTotal = 0;
				for (int j=0; j < 9; j++) {
					String cell = matrix[i][j]==0 ? "" : ""+matrix[i][j];
					sb.append("<td>"+cell+"</td>");
					rowTotal += matrix[i][j];
					columnTotals[j] += matrix[i][j];
				}
				String style = "sudoku-hint-"+(rowTotal==45?"ok":"ko");
				sb.append("<td class='"+style+"'>"+rowTotal+"</td>");
				sb.append("</tr>");
			}
			sb.append("<tr>");
			for (int c=0; c < 9; c++) {
				String style = "sudoku-hint-"+(columnTotals[c]==45?"ok":"ko");
				sb.append("<td class='"+style+"'>"+columnTotals[c]+"</td>");
			}
			sb.append("</tr>");
			sb.append("</table>");
			return sb.toString();
		}
			
		}
}

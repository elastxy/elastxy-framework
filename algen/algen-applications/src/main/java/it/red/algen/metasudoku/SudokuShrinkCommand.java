package it.red.algen.metasudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.red.algen.domain.genetics.genotype.Allele;

public class SudokuShrinkCommand {
	public static final List<Integer> COMPLETE_NUMBERS = new ArrayList<Integer>();
	static {
		// Filled numbers
		for(int n=1; n<10; n++){
			for(int i=0; i<9; i++){
				COMPLETE_NUMBERS.add(n);
			}
		}
	}
	
	
	// INPUT
	private int[][] matrix;
	
	// OUTPUT
	private List<Allele> predefinedAlleles = new ArrayList<Allele>();
	private List<Integer> missingNumbers = new ArrayList<Integer>(COMPLETE_NUMBERS);
	
	
	public SudokuShrinkCommand(int[][] matrix){
		this.matrix = matrix;
	}
	
	
	public List<Allele> getPredefinedAlleles(){
		return predefinedAlleles;
	}
	
	public List<Integer> getMissingNumbers(){
		return missingNumbers;
	}
	
	public void execute(){

		// Count free cells and missing numbers
		for(int r=0; r < matrix.length; r++){
        	for(int c=0; c < matrix.length; c++){
        		if(matrix[r][c]!=0) missingNumbers.remove(new Integer(matrix[r][c]));
        	}    		
    	}
		
		// Create restricted genoma
		for(int i=0; i < missingNumbers.size(); i++){
			Allele<Integer> allele = new Allele<Integer>();
			allele.value = missingNumbers.get(i);
			predefinedAlleles.add(allele);
		}
	}
}

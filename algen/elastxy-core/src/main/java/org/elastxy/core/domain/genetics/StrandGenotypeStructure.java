package org.elastxy.core.domain.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

import org.elastxy.core.engine.metadata.GeneMetadata;


/**
 * A multiple chromosomes genotype structure.
 * @author red
 *
 */
public class StrandGenotypeStructure extends GenotypeStructureImpl {
	
	
	/**
	 * number of genes per chromosome: a list of one value for a sequence
	 */
	private List<Integer> numberOfGenes = null;

//	/**
//	 * number of strands: 0 is a sequence
//	 */
//	private int numberOfStrands = 0;
	
	
	/**
	 * Builds the structure of the strand, given metadata by position.
	 * 
	 * @param genesMetadataByPos
	 */
	public void build(SortedMap<String,GeneMetadata> genesMetadataByPos){
		positionsSize = genesMetadataByPos.size();
		positions = new ArrayList<String>(genesMetadataByPos.keySet());
		countElements();
	}

	/**
	 * Counts elements of all positions
	 * 
	 * 
	 */
	private void countElements(){
		List<String> positions = getPositions();
		for(String pos : positions){
			String[] splitted = pos.split("\\.");
			
			// Sequence
			if(splitted.length==1){
//				numberOfStrands = 0; // no strands
				numberOfChromosomes = 1; // one chromosome
				numberOfGenes = Arrays.asList(positions.size()); // one gene per position
				break;
			}
			
			// Chromosome single strand
			else if(splitted.length==2){
//				numberOfStrands = 1; // one strand
				numberOfChromosomes = new Integer(splitted[0])+1; // at the end will be the higher
				
				if(numberOfGenes==null) numberOfGenes = new ArrayList<Integer>(); // first gene of first chromosome
				if(numberOfGenes.size() < numberOfChromosomes) numberOfGenes.add(new Integer(1));
				numberOfGenes.set(numberOfChromosomes-1, new Integer(splitted[1])+1);
			}

			// TODO3-8: Double strand implementation
			else if(splitted.length==3){
				throw new UnsupportedOperationException("NYI: MultiStrand genotype not yet supported!");
			}
		}
	}

	@Override
	public int getPositionsSize(){
		return positionsSize;
	}
	

	@Override
	public List<String> getPositions() {
		return positions;
	}


	@Override
	public int getNumberOfChromosomes() {
		return numberOfChromosomes;
	}

	@Override
	public int getNumberOfGenes(int chromosome) {
		return numberOfGenes.get(chromosome);
	}


//	@Override
//	public int getNumberOfStrands() {
//		return numberOfStrands;
//	}
}

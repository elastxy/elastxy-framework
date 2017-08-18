package it.red.algen.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.MetadataGenoma;
import it.red.algen.engine.AlleleGenerator;


/**
 * TODOA: common alleles in a specific strategy (now all genes share the same 1000 values!)
 * TODOA: reorder and clean methods and responsibility
 * @author red
 */
public class StandardMetadataGenoma implements MetadataGenoma {

	
	/**
	 * Metadata of all genes type, indexed by code
	 */
	private Map<String,GeneMetadata> genesMetadataByCode = new HashMap<String, GeneMetadata>();
	

	/**
	 * Metadata of all genes type, indexed by position
	 * 
	 * Used if order is important
	 * 
	 * NOTE: hashmap keys are not ordered
	 */
	private Map<String,GeneMetadata> genesMetadataByPos = new HashMap<String, GeneMetadata>();


	/**
	 * Generator for building a new Allele based on Gene metadata characteristics
	 * or optionally specifying a Allele value
	 */
	private AlleleGenerator alleleGenerator;

	private boolean limitedAllelesStrategy;

	/**
	 * Inject an allele generator implementation
	 * @param generator
	 */
	@Override
	public void setupAlleleGenerator(AlleleGenerator generator){
		alleleGenerator = generator;
	}

	@Override
	public void initialize(Map<String,GeneMetadata> genesMetadataByCode, Map<String,GeneMetadata> genesMetadataByPos){
		this.genesMetadataByCode = genesMetadataByCode;
		this.genesMetadataByPos = genesMetadataByPos;
	}

	public boolean isLimitedAllelesStrategy() {
		return limitedAllelesStrategy;
	}

	public void setLimitedAllelesStrategy(boolean limitedAllelesStrategy) {
		this.limitedAllelesStrategy = limitedAllelesStrategy;
	}
	
	
	@Override
	public int getPositionsSize(){
		return genesMetadataByPos.size();
	}



	/**
	 * Get the metadata by code
	 * @param metadataCode
	 * @return
	 */
	public GeneMetadata getMetadataByCode(String metadataCode){
		return genesMetadataByCode.get(metadataCode);
	}
	
	
	/**
	 * Get the metadata by a given position
	 * @param metadataCode
	 * @return
	 */
	public GeneMetadata getMetadataByPosition(String position){
		return genesMetadataByPos.get(position);
	}
	

	/**
	 * Some methods are not allowed when limited alleles strategy is on
	 */
	private void forbidLimitedAllelesStrategy(){
		if(limitedAllelesStrategy){
			throw new IllegalStateException("Cannot generate Allele in limited context: you must use aggregate methods.");
		}
	}

	
//	/**
//	 * Some methods are not available when limited alleles strategy is on because not yet implemented
//	 */
//	private void nyiLimitedAllelesStrategy(){
//		if(limitedAllelesStrategy){
//			throw new IllegalStateException("NYI");
//		}
//	}

	
	
	
	
	// ***********************************************************************
	// TODOA: separate Alleles Factory methods from Genoma

	
	/**
	 * Creates a new random allele given the position in the sequence
	 * TODOM: if not ordered, metadata is random
	 * 
	 * IMPORTANT: in case of limited resources, client must swap alleles of two different positions
	 */
	@Override
	public Allele getRandomAllele(String position) {
		return alleleGenerator.generate(getMetadataByPosition(position));
	}
	
	
	
	/**
	 * Generate a new set of random Alleles based on positions
	 * 
	 * If alleles are limited, allele generator is given the list
	 * of already generated allele at each creation of a new allele
	 * for restricting the possible values
	 * 
	 * @param positions
	 * @return
	 */
	@Override
	public List<Allele> getRandomAlleles(List<String> positions){
		List<Allele> result = null;
		if(!limitedAllelesStrategy){
			result = getRandomAlleles(positions);
		}
		else {
			result = new ArrayList<Allele>();
			List<Object> alreadyUsedAlleles = new ArrayList<Object>();
			for(String pos : positions){
				Allele newAllele = alleleGenerator.generateExclusive(getMetadataByPosition(pos), alreadyUsedAlleles);
				alreadyUsedAlleles.add(newAllele.value);
				result.add(newAllele);
			}
		}
		return result;
	}

	
	
	/**
	 * Generate a new list of random Alleles for every position
	 * 
	 * If alleles are limited, allele generator is given the list
	 * of already generated allele at each creation of a new allele
	 * for restricting the possible values
	 * 
	 * @param metadataCodes
	 * @return
	 */
	@Override
	public List<Allele> getRandomAlleles(){
		List<String> positions = IntStream.range(0, genesMetadataByPos.size()).boxed().map(i -> i.toString()).collect(Collectors.toList());
		return getRandomAlleles(positions);
	}

	
	/**
	 * Generate new Allele list based on given metadata
	 * 
	 * It cannot be performed if allele are limited, because it's not position based
	 * and can be arbitrary called N times
	 * 
	 * @param metadataCode
	 * @return
	 */
	public List<Allele> createRandomAllelesByCode(List<String> metadataCodes){
		forbidLimitedAllelesStrategy();
		return metadataCodes.stream().map(s -> createRandomAlleleByCode(s)).collect(Collectors.toList());
	}
	

	
	/**
	 * Generate a new random Allele based on a metadata
	 * 
	 * It cannot be performed if allele are limited, because it's not position based
	 * and can be arbitrary called N times
	 * 
	 * @param metadataCode
	 * @return
	 */
	public Allele createRandomAlleleByCode(String metadataCode){
		forbidLimitedAllelesStrategy();
		return alleleGenerator.generate(getMetadataByCode(metadataCode));
	}
	

	/**
	 * Generates one Allele for every possible values of the metadataCode
	 * 
	 *TODOA: one list of values shared for all
	 * @return
	 */
	public List<Allele> createRandomAllelesByCode(String metadataCode){
		GeneMetadata geneMetadata = getMetadataByCode(metadataCode);
		List<Allele> result = (List<Allele>)geneMetadata.values.stream().map(v -> alleleGenerator.generate(geneMetadata, v)).collect(Collectors.toList());
		return result;
	}
	
	

	/**
	 * Generates a new Allele based on specific value
	 * 
	 * It cannot be performed if allele are limited, because it can be arbitrary called
	 * 
	 * An exception is raise if value is not present between metadata available values
	 */
	public Allele createAlleleByValue(String metadataCode, Object value){
		forbidLimitedAllelesStrategy();
		return alleleGenerator.generate(getMetadataByCode(metadataCode), value);
	}
	
	
	public String toString(){
		return String.format("MetadataGenoma: %d metadata, limited alleles %b", genesMetadataByCode.size(), limitedAllelesStrategy);
	}
}

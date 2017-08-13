package it.red.algen.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.red.algen.domain.genetics.Allele;
import it.red.algen.domain.genetics.Gene;
import it.red.algen.engine.AlleleGenerator;

public class MetadataBasedGenoma implements Genoma {

	
	/**
	 * If FALSE
	 * 
	 * Any number of Alleles can be created of the same type
	 * 
	 * If TRUE
	 * 
	 * Limits the number of total Alleles to those predefined at the beginning.
	 * When generating a set of Alleles for a number of genes, takes care of excluding 
	 * those already selected
	 */
	public boolean limitedAllelesStrategy = false;
	
	public boolean isLimitedAllelesStrategy() {
		return limitedAllelesStrategy;
	}


	public void setLimitedAllelesStrategy(boolean limitedAllelesStrategy) {
		this.limitedAllelesStrategy = limitedAllelesStrategy;
	}

	/**
	 * Metadata of all genes type, indexed by code
	 */
	public Map<String,GeneMetadata> genesMetadataByCode = new HashMap<String, GeneMetadata>();
	

	/**
	 * Metadata of all genes type, indexed by position, if order is important
	 * NOTE: hashmap keys are not ordered
	 */
	public Map<String,GeneMetadata> genesMetadataByPos = new HashMap<String, GeneMetadata>();


	private AlleleGenerator alleleGenerator;

	@Override
	public void setupAlleleGenerator(AlleleGenerator generator){
		alleleGenerator = generator;
	}

	
	private void forbidLimitedAllelesStrategy(){
		if(limitedAllelesStrategy){
			throw new IllegalStateException("Cannot generate Allele in limited context: you must use aggregate methods.");
		}
	}

	private void nyiLimitedAllelesStrategy(){
		if(limitedAllelesStrategy){
			throw new IllegalStateException("Cannot generate Allele in limited context: you must use aggregate methods.");
		}
	}

	
	
	
	/**
	 * Creates a new random allele given the position in the sequence
	 * TODO: if not ordered, metadata is random
	 * 
	 * IMPORTANT: in case of limited resources, client must swap alleles of two different positions
	 */
	@Override
	public Allele createRandomAllele(String position) {
		return alleleGenerator.generate(getMetadataByPosition(position));
	}
	
	
	
	/**
	 * Generate a new set of random Alleles based on positions
	 * @param positions
	 * @return
	 */
	@Override
	public List<Allele> createRandomAlleles(List<String> positions){
		nyiLimitedAllelesStrategy();
		return positions.stream().map(s -> createRandomAllele(s)).collect(Collectors.toList());
	}

	
	
	/**
	 * Generate a new list of random Alleles for every position
	 * @param metadataCodes
	 * @return
	 */
	@Override
	public List<Allele> createRandomAlleles(){
		
		List<String> positions = IntStream.range(0, genesMetadataByPos.size()).boxed().map(i -> i.toString()).collect(Collectors.toList());
		List<Allele> result = null;
		if(!limitedAllelesStrategy){
			result = createRandomAlleles(positions);
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
	 * Generate new Allele list based on some metadata
	 * @param metadataCode
	 * @return
	 */
	public List<Allele> createRandomAllelesByCode(List<String> metadataCodes){
		forbidLimitedAllelesStrategy();
		return metadataCodes.stream().map(s -> createRandomAlleleByCode(s)).collect(Collectors.toList());
	}

	
	/**
	 * Generate a new Allele based on a metadata
	 * @param metadataCode
	 * @return
	 */
	public Allele createRandomAlleleByCode(String metadataCode){
		forbidLimitedAllelesStrategy();
		return alleleGenerator.generate(getMetadataByCode(metadataCode));
	}
	
	/**
	 * Generates a new Allele based on specific value
	 * 
	 * An exception is raise if value is not present between metadata available values
	 */
	public Allele createPredefinedAlleleByValue(String metadataCode, Object value){
		forbidLimitedAllelesStrategy();
		return alleleGenerator.generate(getMetadataByCode(metadataCode), value);
	}
	
	
	
	

	/**
	 * Create a new Gene structure without Allele from metadata
	 * @param metadataCode
	 * @param position
	 * @return
	 */
	public Gene createGeneByCode(String metadataCode, String position){
		GeneMetadata metadata =  getMetadataByCode(metadataCode);
		return createGene(metadataCode, position, metadata);
	}


	/**
	 * Create a new Gene structure without Allele from position
	 * @param metadataCode
	 * @param position
	 * @return
	 */
	public Gene createGeneByPosition(String position){
		GeneMetadata metadata =  getMetadataByPosition(position);
		return createGene(metadata.code, position, metadata);
	}

	
	private Gene createGene(String metadataCode, String position, GeneMetadata metadata) {
		Gene gene = new Gene();
		gene.metadataCode = metadataCode;
		gene.pos = position;
		gene.locationProperties = new HashMap<String, Object>(metadata.userProperties);
		return gene;
	}
	

	/**
	 * Create a list of Genes from a list of positions
	 * @param positions
	 * @return
	 */
	public List<Gene> createSequenceByPositions(List<String> positions){
		return positions.stream().map(p -> createGeneByPosition(p)).collect(Collectors.toList());
	}
	

	/**
	 * Create a list of Genes for all positions
	 * @param positions
	 * @return
	 */
	public List<Gene> createSequence(){
		List<Gene> result = new ArrayList<Gene>();
		for(int pos=0; pos < this.genesMetadataByPos.size(); pos++){
			result.add(createGeneByPosition(String.valueOf(pos)));
		}
		return result;
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
	
	
}

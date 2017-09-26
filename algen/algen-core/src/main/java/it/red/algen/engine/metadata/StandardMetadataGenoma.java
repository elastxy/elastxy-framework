package it.red.algen.engine.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import it.red.algen.domain.genetics.GenotypeStructureType;
import it.red.algen.domain.genetics.StrandGenotypeStructure;
import it.red.algen.domain.genetics.genotype.Allele;
import it.red.algen.engine.genetics.AbstractGenoma;


/**
 * A Genoma based on metadata, representing type of gene allocated in the postions.
 * 
 * Multiple Chromosomes Genoma.
 * 
 * TODOA: add access to list of common alleles in a specific strategy
 * (now all genes share the same 1000 values and must be retrieved with get(0)!)
 * @author red
 */
public class StandardMetadataGenoma extends AbstractGenoma implements MetadataGenoma {
	
	
	/**
	 * Metadata of all genes type, indexed by code
	 */
	Map<String,GeneMetadata> genesMetadataByCode = new HashMap<String, GeneMetadata>();
	

	/**
	 * Metadata of all genes type, indexed and ordered by position
	 */
	SortedMap<String,GeneMetadata> genesMetadataByPos = new TreeMap<String, GeneMetadata>(POSITIONS_COMPARATOR);


	/**
	 * Generator for building a new Allele based on Gene metadata characteristics
	 * or optionally specifying a Allele value
	 */
	private AlleleGenerator alleleGenerator;

	
	/**
	 * Inject an allele generator implementation
	 * @param generator
	 */
	@Override
	public void setupAlleleGenerator(AlleleGenerator generator){
		alleleGenerator = generator;
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
	 * Get alleles always in the same order, picking default values from each.
	 * 
	 * @return
	 */
	@Override
	public List<Allele> getOrderedAlleles() {
		List<Allele> result = new ArrayList<Allele>();
		result = genesMetadataByPos.values().stream().
				map(m -> ((MetadataAlleleGenerator)alleleGenerator).generateFirst(m)).
				collect(Collectors.toList());
		return result;
	}


//	@Override
//	public List<Allele> getFirstAlleles(){
//		List<String> positions = new ArrayList<String>(genesMetadataByPos.keySet());
//		List<Allele> result = positions.stream().map(s -> getFirstAllele(s)).collect(Collectors.toList());
//		return result;
//	}
//	
//	public Allele getFirstAllele(String position) {
//		return alleleGenerator.generateRandom(getMetadataByPosition(position));
//	}
	
	
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
		List<String> positions = new ArrayList<String>(genesMetadataByPos.keySet());
		return getRandomAlleles(positions);
	}


	@Override
	public SortedMap<String, Allele> getRandomAllelesAsMap(){
		List<String> positions = new ArrayList<String>(genesMetadataByPos.keySet());
		List<Allele> allelesList = getRandomAlleles(positions);
		SortedMap<String, Allele> result = new TreeMap<String, Allele>(POSITIONS_COMPARATOR);
		for(int a=0; a < allelesList.size(); a++){
			result.put(positions.get(a), allelesList.get(a));
		}
		return result;
	}
	
	
	/**
	 * Creates a new random allele given the position in the sequence
	 * TODOM: if not ordered, metadata is random
	 * 
	 * IMPORTANT: in case of limited resources, client must swap alleles of two different positions
	 */
	@Override
	public Allele getRandomAllele(String position) {
		return alleleGenerator.generateRandom(getMetadataByPosition(position));
	}
	
	
	/**
	 * Generate a new set of random Alleles based on positions.
	 * 
	 * If alleles are limited, for restricting the possible values,
	 * allele generator is given the list of already generated allele 
	 * at each creation of a new allele.
	 * 
	 * Else, completely random alleles with repetitions are created.
	 * 
	 * @param positions
	 * @return
	 */
	@Override
	public List<Allele> getRandomAlleles(List<String> positions){
		List<Allele> result = null;
		if(!limitedAllelesStrategy){
			result = positions.stream().map(s -> getRandomAllele(s)).collect(Collectors.toList());
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
	



	
	

	
//	/**
//	 * Generates a new Allele based on specific value
//	 * 
//	 * It cannot be performed if allele are limited, because it can be arbitrary called
//	 * 
//	 * An exception is raise if value is not present between metadata available values
//	 */
//	@Override
//	public Allele createAlleleByValue(String metadataCode, Object value){
//		forbidLimitedAllelesStrategy();
//		GeneMetadata metadata = getMetadataByCode(metadataCode);
//		if(!metadata.values.contains(value)){
//			throw new AlgorithmException("Cannot create an Allele with given value: it's not included in those of metadata.");
//		}
//		return alleleGenerator.generateFromValue(value);
//	}
	
	
	/**
	 * Generates one Allele for every possible values of the metadataCode
	 * 
	 *TODOA: add access to list of common alleles
	 * @return
	 */
	@Override
	public List<Allele> createRandomAllelesByCode(String metadataCode){
		GeneMetadata geneMetadata = getMetadataByCode(metadataCode);
		List<Allele> result = (List<Allele>)geneMetadata.values.stream().map(v -> alleleGenerator.generateFromValue(v)).collect(Collectors.toList());
		return result;
	}
	
//	/**
//	 * Generate new Allele list based on given metadata
//	 * 
//	 * It cannot be performed if allele are limited, because it's not position based
//	 * and can be arbitrary called N times
//	 * 
//	 * @param metadataCode
//	 * @return
//	 */
//	@Override
//	public List<Allele> createRandomAllelesByCodes(List<String> metadataCodes){
//		forbidLimitedAllelesStrategy();
//		return metadataCodes.stream().map(s -> createRandomAlleleByCode(s)).collect(Collectors.toList());
//	}
//	
//	/**
//	 * Generate a new random Allele based on a metadata
//	 * 
//	 * It cannot be performed if allele are limited, because it's not position based
//	 * and can be arbitrary called N times
//	 * 
//	 * @param metadataCode
//	 * @return
//	 */
//	private Allele createRandomAlleleByCode(String metadataCode){
//		//forbidLimitedAllelesStrategy();
//		return alleleGenerator.generateRandom(getMetadataByCode(metadataCode));
//	}
	

	
	

	
	public String toString(){
		return String.format("MetadataGenoma: %d metadata, limited alleles %b", genesMetadataByCode.size(), limitedAllelesStrategy);
	}
}

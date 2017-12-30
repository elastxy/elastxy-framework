package org.elastxy.core.engine.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.elastxy.core.domain.genetics.AbstractGenoma;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.engine.core.AlgorithmException;
import org.elastxy.core.engine.core.Randomizer;


/**
 * A Genoma based on metadata, representing type of gene allocated in the postions.
 * 
 * Multiple Chromosomes Genoma.
 * 
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
	 * or optionally specifying a Allele value.
	 */
	private AlleleGenerator alleleGenerator;

	
	/**
	 * Inject an allele generator implementation
	 * @param generator
	 */
	@Override
	public void setupAlleleGenerator(AlleleGenerator generator){
		alleleGenerator = generator;
		alleleGenerator.setup(this);
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
				map(m -> getFirst(m)).
				collect(Collectors.toList());
		return result;
	}
	
	
	// TODO2-2: alleleValuesProvider: move getFirst method on provider
	private Allele getFirst(GeneMetadata metadata){
		Allele result = metadata.valuesProvider==null ? 
				((MetadataAlleleGenerator)alleleGenerator).generateFirst(metadata) :
					alleleValuesProvider.getAlleles(metadata.valuesProvider).get(0);
				;
		return result;
	}
	

	// TODO1-4: for big provider, pass mandatory number of alleles to retrieve
	@Override
	public List<Allele> getAlleles(GeneMetadata metadata) {
		return alleleValuesProvider.getAlleles(metadata.valuesProvider);
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
	 * TODO2-2: if not ordered, metadata is random
	 * 
	 * IMPORTANT: in case of limited resources, client must swap alleles of two different positions
	 */
	@Override
	public Allele getRandomAllele(String position) {
		GeneMetadata metadata = getMetadataByPosition(position);
		return metadata.valuesProvider==null ? 
				alleleGenerator.generateRandom(metadata) :
					getRandomProvided(metadata.valuesProvider);
	}
	

	@Override
	public Allele getRandomAllele(GeneMetadata metadata) {
		return getRandomProvided(metadata.valuesProvider);
	}
	
	
	// TODO2-2: alleleValuesProvider: move get random value to Provider
	private Allele getRandomProvided(String valuesProvider){
		List<Allele> alleles = alleleValuesProvider.getAlleles(valuesProvider);
		return alleles.get(Randomizer.nextInt(alleles.size()));
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
				GeneMetadata metadata = getMetadataByPosition(pos);
				Allele newAllele = metadata.valuesProvider==null ? 
						alleleGenerator.generateExclusive(metadata, alreadyUsedAlleles) :
							generateExclusiveFromProvided(metadata.valuesProvider, alreadyUsedAlleles);
				alreadyUsedAlleles.add(newAllele.value);
				result.add(newAllele);
			}
		}
		return result;
	}
	
	public <T> Allele<T> generateExclusiveFromProvided(String valuesProvider, List<T> exclusions) {
		Allele<T> result = new Allele<T>();
		
		// TODO2-4: alleleValuesProvider: move filter logics on already used Alleles into provider?
		List<T> subtracted = (List<T>)alleleValuesProvider.getAlleles(valuesProvider).stream().filter(t -> !exclusions.contains(t)).collect(Collectors.toList());
		if(subtracted.isEmpty()){
			throw new AlgorithmException("Remaining values for generating alleles cannot be empty! Maybe allele possibile values are not enough for this gene?");
		}
		result.value = (T)subtracted.get(Randomizer.nextInt(subtracted.size()));
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
	
	
//	/**
//	 * Generates one Allele for every possible values for the metadata.
//	 * 
//	 * TODO2-2: cache!
//	 * 
//	 * @return
//	 */
//	@Override
//	public List<Allele> getRandomAllelesByCode(String metadataCode){
//		GeneMetadata geneMetadata = getMetadataByCode(metadataCode);
//		List<Allele> result = geneMetadata.valuesProvider==null ? 
//				(List<Allele>)geneMetadata.values.stream().map(v -> alleleGenerator.generateFromValue(v)).collect(Collectors.toList()) :
//				new ArrayList<Allele>(provider.getAlleles(geneMetadata.valuesProvider));
//		Collections.shuffle(result);
//		return result;
//	}
	
	
	
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

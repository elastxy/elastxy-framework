package it.red.algen.domain.genetics;

import java.util.List;
import java.util.Map;

import it.red.algen.engine.AlleleGenerator;
import it.red.algen.metadata.GeneMetadata;

/**
 * A Genoma based on Metadata: every Gene has properties for generating
 * new Alleles, within a Gene specific set, range or totally random based
 * on its characteristics.
 * 
 * 
 * @author red
 *
 */
public interface MetadataGenoma extends Genoma {

	/**
	 * Inject an allele generator implementation
	 * @param generator
	 */
	public void setupAlleleGenerator(AlleleGenerator generator);
	
	/**
	 * Setup initial data
	 * @param genesMetadataByCode
	 * @param genesMetadataByPos
	 */
	public void initialize(Map<String,GeneMetadata> genesMetadataByCode, Map<String,GeneMetadata> genesMetadataByPos);

	/**
	 * Get the metadata by code
	 * @param metadataCode
	 * @return
	 */
	public GeneMetadata getMetadataByCode(String metadataCode);
	

	/**
	 * Get the metadata by a given position
	 * @param metadataCode
	 * @return
	 */
	public GeneMetadata getMetadataByPosition(String position);
	

	/**
	 * Creates a new random allele given the position in the sequence
	 * TODOM: if not ordered, metadata is random
	 * 
	 * IMPORTANT: in case of limited resources, client must swap alleles of two different positions
	 */
	@Override
	public Allele getRandomAllele(String position);
	
	
	
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
	public List<Allele> getRandomAlleles(List<String> positions);

	
	
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
	public List<Allele> getRandomAlleles();

	
	/**
	 * Generate new Allele list based on given metadata
	 * 
	 * It cannot be performed if allele are limited, because it's not position based
	 * and can be arbitrary called N times
	 * 
	 * @param metadataCode
	 * @return
	 */
	public List<Allele> createRandomAllelesByCode(List<String> metadataCodes);

	
	/**
	 * Generate a new random Allele based on a metadata
	 * 
	 * It cannot be performed if allele are limited, because it's not position based
	 * and can be arbitrary called N times
	 * 
	 * @param metadataCode
	 * @return
	 */
	public Allele createRandomAlleleByCode(String metadataCode);
	

	/**
	 * Generates a new Allele based on specific value
	 * 
	 * It cannot be performed if allele are limited, because it can be arbitrary called
	 * 
	 * An exception is raise if value is not present between metadata available values
	 */
	public Allele createAlleleByValue(String metadataCode, Object value);
	
}

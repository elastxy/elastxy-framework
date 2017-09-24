package it.red.algen.engine.metadata;

import java.util.List;
import java.util.Map;

import it.red.algen.domain.genetics.Genoma;
import it.red.algen.domain.genetics.genotype.Allele;

/**
 * A Genoma based on Metadata: every Gene has properties for generating
 * new Alleles, within a Gene specific set, range or totally random based
 * on its characteristics.
 * 
 * Position codification depends entirely on genotype type.
 * 
 * E.g. when multiple chromosomes are involved, positions are in the form of "x.y" instead of "x".
 * See also: {@link SequenceGenotype}, {@link ChromosomeGenotype}, {@link DoubleStrandGenotype}
 * 
 * TODOM: evaluate if it's useful to create a specific MetadataGenoma type "ChromosomeMetadataGenoma"
 * composing multiple "StandardMetadataGenoma", one for each chromosome...
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
	 * Load genes metadata
	 * @param genes
	 */
	public void initialize(GenesMetadataConfiguration genes);
	
	
	
	/**
	 * Get the metadata by code
	 * TODOA: move to Structure
	 * @param metadataCode
	 * @return
	 */
	public GeneMetadata getMetadataByCode(String metadataCode);
	

	/**
	 * Get the metadata by a given position
	 * TODOA: move to Structure
	 * @param metadataCode
	 * @return
	 */
	public GeneMetadata getMetadataByPosition(String position);
	
	
	
	

	/**
	 * Generates one Allele for every possible values of the metadataCode.
	 * 
	 *TODOA: add access to list of common alleles
	 * @return
	 */
	public List<Allele> createRandomAllelesByCode(String metadataCode);
	
//	/**
//	 * Generate new Allele list based on given metadata.
//	 * 
//	 * It cannot be performed if allele are limited, because it's not position based
//	 * and can be arbitrary called N times.
//	 * 
//	 * @param metadataCode
//	 * @return
//	 */
//	public List<Allele> createRandomAllelesByCodes(List<String> metadataCodes);

//	/**
//	 * Generates a new Allele based on specific value.
//	 * 
//	 * It cannot be performed if allele are limited, because it can be arbitrary called.
//	 * 
//	 * An exception is raise if value is not present between metadata available values.
//	 */
//	public Allele createAlleleByValue(String metadataCode, Object value);
	
	
}

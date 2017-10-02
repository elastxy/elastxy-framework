package it.red.algen.engine.metadata;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.AlleleValuesProvider;
import it.red.algen.domain.genetics.ChromosomeGenotypeStructure;
import it.red.algen.domain.genetics.StrandGenotypeStructure;
import it.red.algen.engine.AlgorithmException;

/**
 * Helper class for building a Genoma based on Metadata.
 * 
 * - build() method is self-contained
 * - create(), addGene()[*], finalize() are to be called in sequence
 * 
 * @author red
 *
 */
public class MetadataGenomaBuilder {

	
	/**
	 * Creates a new instance of Genoma.
	 * 
	 * limitedAllelesStrategy is false by default.
	 * 
	 * @param context
	 * @return
	 */
	public static StandardMetadataGenoma create(AlgorithmContext context){
		return create(context, false);
	}
	
	
	/**
	 * Creates a new instance of Genoma, specifying limited allele strategy.
	 * 
	 * @param context
	 * @param limitedAllelesStrategy
	 * @return
	 */
	public static StandardMetadataGenoma create(AlgorithmContext context, boolean limitedAllelesStrategy){
		StandardMetadataGenoma result = new StandardMetadataGenoma();
		if(context!=null) result.setupAlleleGenerator(context.application.alleleGenerator);
		result.setLimitedAllelesStrategy(limitedAllelesStrategy);
		return result;
	}
	

	/**
	 * Creates and finalize
	 * @param context
	 * @param genes
	 * @return
	 */
	public static StandardMetadataGenoma build(AlgorithmContext context, GenesMetadataConfiguration genes){
		StandardMetadataGenoma result = create(context, false);
		addGenes(result, genes);
		finalize(result);
		return result;
	}
	

	/**
	 * Add all genes given their metadata configuration.
	 * @param genoma
	 * @param genes
	 */
	public static void addGenes(StandardMetadataGenoma genoma, GenesMetadataConfiguration genes){
		Iterator<Entry<String, GeneMetadata>> it = genes.metadata.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, GeneMetadata> entry = it.next();
			genoma.genesMetadataByCode.put(entry.getKey(), entry.getValue()); // yes, may be overwritten
			List<String> positions = genes.positions.get(entry.getKey());
			for(int p=0; p < positions.size(); p++){
				genoma.genesMetadataByPos.put(String.valueOf(positions.get(p)), entry.getValue());
			}
		}
	}
	
	
	
	/**
	 * Add new Gene to the Genoma in a specified position.
	 * @param genoma
	 * @param pos
	 * @param metadata
	 */
	public static void addGene(StandardMetadataGenoma genoma, String pos, GeneMetadata metadata){
		genoma.genesMetadataByCode.put(metadata.code, metadata);
		genoma.genesMetadataByPos.put(pos, metadata);
	}
	

//	/**
//	 * Add the list of possible Alleles to the Genoma for a specified provider
//	 * found in metadata.
//	 * 
//	 * Name is specified in valuesProvider property of GeneMetadata.
//	 * 
//	 * @param genoma
//	 * @param pos
//	 * @param metadata
//	 */
//	public static void addAlleleValues(StandardMetadataGenoma genoma, String provider, List<Allele> alleles){
//		genoma.insertAlleles(provider, alleles);
//	}
	

	/**
	 * Optionally, adds new AlleleProvider to the Genoma.
	 * 
	 * Name is specified in valuesProvider property of GeneMetadata.
	 * 
	 * @param genoma
	 * @param pos
	 * @param metadata
	 */
	public static void setupAlleleValuesProvider(StandardMetadataGenoma genoma, AlleleValuesProvider provider){
		genoma.setAlleleValuesProvider(provider);;
	}
	
	
	
	
	/**
	 * Complete build creating appropriate Structure.
	 */
	public static void finalize(StandardMetadataGenoma genoma){
		if(genoma.genesMetadataByCode.isEmpty()){
			throw new AlgorithmException("Cannot finalize the structure of a empty Genoma.");
		}
		
		// Chromosome
		if(genoma.genesMetadataByPos.firstKey().equals("0")){
			ChromosomeGenotypeStructure structure = new ChromosomeGenotypeStructure();
			structure.build(genoma.genesMetadataByPos.keySet().size()); // number of positions
			genoma.setGenotypeStructure(structure);
		}
		
		// Strand
		else if(genoma.genesMetadataByPos.firstKey().equals("0.0")){
			StrandGenotypeStructure structure = new StrandGenotypeStructure();
			structure.build(genoma.genesMetadataByPos);
			genoma.setGenotypeStructure(structure);
		}
		
		// Multistrand
		else if(genoma.genesMetadataByPos.firstKey().equals("0.0.0")){
			throw new UnsupportedOperationException("NYI");
		}
		
		else {
			throw new AlgorithmException("Cannot finalize the structure of Genoma: position type unknown. First position: "+genoma.genesMetadataByPos.firstKey());
		}
	}
	
}

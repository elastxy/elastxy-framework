package org.elastxy.distributed.dataprovider;

import java.util.List;

import org.elastxy.core.dataprovider.GenomaProvider;
import org.elastxy.core.domain.genetics.genotype.Allele;

/**
 * Transforms raw RDD<datatype> data into RDD<Allele> alleles.
 * 
 * RDD<Allele> cardinality should be enough to cover 
 * a single initial population: solutionsNumber + 50%
 * 
 * @author red
 *
 */
public interface DistributedGenomaProvider extends GenomaProvider {

	/**
	 * Collects strict number of Alleles for performing mutations 
	 * in subsequent executions.
	 * 
	 * In distributed context alleles are collected for a single Era on all colonies,
	 * and are translated by Driver in a Broadcast variable to spread
	 * them across nodes.
	 * 
	 * Number of alleles is based on algorithm parameters:
	 * era generations, solutions in a generation, reshuffle every tot eras...
	 * 
	 * @return
	 */
	public List<Allele> collectForMutation();
	
	
	/**
	 * Spreads Genoma to all colonies for allowing all genetic material
	 * to be shared within an Era.
	 * 
	 * In distributed context it means a repartition/coalesce of
	 * original data and subsequence genoma redefinition.
	 * 
	 * TODOA-8: maintain best matches over eras
	 */
	public void spread();

}
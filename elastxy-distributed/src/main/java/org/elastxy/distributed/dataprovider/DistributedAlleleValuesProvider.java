package org.elastxy.distributed.dataprovider;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.elastxy.core.dataprovider.AlleleValuesProvider;
import org.elastxy.core.domain.genetics.genotype.Allele;

/**
 * Maintains cached RDD of Alleles.
 * 
 * IMPORTANT: Alleles RDD is already partitioned based 
 * on original partitions of working dataset RDD.
 * 
 * @author red
 *
 */
public class DistributedAlleleValuesProvider implements AlleleValuesProvider {
	public final static String NAME = "rddProvider";
//	private static Logger logger = Logger.getLogger(DistributedAlleleValuesProvider.class);

	private JavaRDD<Allele> alleles;
	
	public DistributedAlleleValuesProvider(JavaRDD<Allele> alleles){
		this.alleles = alleles;
	}
	
	public JavaRDD<Allele> rdd(){
		return alleles;
	}

	@Override
	public int countProviders() {
		return 1;
	}

	/**
	 * Collects alleles, executing a Spark action on RDD.
	 * TODO2-2: method getAlleles(number)? document and contextualize all these getAllele methods!
	 */
	@Override
	public List<Allele> getAlleles() {
		return alleles.collect();
	}
	
	@Override
	public List<Allele> getAlleles(String provider) {
		return getAlleles();
	}
	
	
	// TODO2-1: alleleValuesProvider: move to a "writable" alleleValuesProvider or implements a "read only" alleleVP
	@Override
	public void insertAlleles(List<Allele> alleles) {
		if(true) throw new UnsupportedOperationException("Not supported: alleles are retrieved by read-only RDD");
	}
	
	@Override
	public void insertAlleles(String provider, List<Allele> alleles) {
		if(true) throw new UnsupportedOperationException("Not supported: alleles are retrieved by read-only RDD");
	}

}

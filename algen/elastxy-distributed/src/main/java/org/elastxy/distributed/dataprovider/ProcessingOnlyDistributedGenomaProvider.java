package org.elastxy.distributed.dataprovider;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.elastxy.core.context.AlgorithmContext;
import org.elastxy.core.dataprovider.AlleleValuesProvider;
import org.elastxy.core.dataprovider.WorkingDataset;
import org.elastxy.core.domain.experiment.Target;
import org.elastxy.core.domain.genetics.Genoma;
import org.elastxy.core.domain.genetics.genotype.Allele;
import org.elastxy.core.engine.genetics.PredefinedGenoma;
import org.elastxy.core.engine.genetics.PredefinedGenomaBuilder;


/**
 * For building the first population for a single solution,
 * we don't need any distributed Alleles: they will be locally
 * determined by the same Target and same environment data everywhere,
 * plus eventually broadcasted data.
 * 
 * Also mutation involves a number of swaps between numbers
 * already present in the nodes: no BigData this game!
 * 
 * E.g. Sudoku is determined by locally created target list of
 * integer to fille the matrix, plus the matrix itself,
 * already present on every node.
 * 
 * @author red
 */
public class ProcessingOnlyDistributedGenomaProvider implements DistributedGenomaProvider {
//	private static Logger logger = Logger.getLogger(MesdDistributedGenomaProvider.class);

	private AlgorithmContext context;

	private RDDDistributedWorkingDataset<Integer> workingDataset;
	private PredefinedGenoma genoma;
	
	@Override
	public void setup(AlgorithmContext context) {
		this.context = context;
	}
	
	@Override
	public void setWorkingDataset(WorkingDataset workingDataset) {
		this.workingDataset = (RDDDistributedWorkingDataset<Integer>)workingDataset;
	}

	@Override
	public Genoma getGenoma(){
		return genoma;
	}
	
	@Override
	public void collect() {
	    JavaRDD<Allele> alleles = workingDataset.rdd.map(IntegerToAllele::toAllele);
		AlleleValuesProvider allelesProvider = new DistributedAlleleValuesProvider(alleles);
		genoma = PredefinedGenomaBuilder.build((int)alleles.count(), allelesProvider, true);
	}

	
	@Override
	public List<Allele> collectForMutation() {
		return null;
	}

	
	// TODOM-2: evaluate a specific target builder in distributed environment to set overall goals?
	@Override
	public Genoma shrink(Target<?, ?> target) {
		return genoma;
	}
	
	@Override
	public void spread() {
	}


}

package it.red.algen.d.metaexpressions;

import org.apache.spark.api.java.JavaRDD;

import it.red.algen.dataprovider.WorkingDataset;

public class MexdWorkingDataset implements WorkingDataset {
	public JavaRDD<Long> numbers;
}

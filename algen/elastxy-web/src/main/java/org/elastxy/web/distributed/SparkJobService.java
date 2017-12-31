package org.elastxy.web.distributed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SparkJobService {
	private static Logger logger = LoggerFactory.getLogger(SparkJobService.class);

	@Autowired private ApplicationsSparkConfig applicationsSparkConfig;

	public String checkJobStatus(String jobId) throws Exception {
		SparkTaskExecutor executor = new SparkTaskExecutor();
    	SparkTaskConfig config = applicationsSparkConfig.getTaskConfig();
    	String result = executor.checkJobStatus(config, jobId);
		return result;
	}

	public boolean killJob(String jobId) throws Exception {
		SparkTaskExecutor executor = new SparkTaskExecutor();
    	SparkTaskConfig config = applicationsSparkConfig.getTaskConfig();
    	boolean result = executor.killJob(config, jobId);
		return result;
	}
}

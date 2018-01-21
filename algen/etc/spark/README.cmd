# Spark Development Configuration

Here below some useful files to make the access to Spark easy for ElastXY users.

These are only sample files: please change configurations according to your environment.

## WINDOWS OS

### Configuration

Spark example configurations are provided here into "conf" directory.

For using them, simply point to <SPARK_HOME>/conf directory, backup two file below renaming them to ".orig", then copy "conf" directory from here to <SPARK_HOME>.
  - log4j.properties
  - spark-defaults.conf
  
### Execution

Spark executables scripts are provided here into "bin" directory as Windows commands to run a Spark Standalone Cluster.

For using them, simply copy "bin" directory to <SPARK_HOME>: no file will be override starting from a fresh Spark installation
  - run-master.cmd: launch Master
  - run-worker.cmd: launch single Worker
  - run-history-server.cmd: launch Server History

Then issue these command directly from <SPARK_HOME>/bin directory.

For other Spark REST Client parameters, please look an example at SparkTaskExecutor:runDistributed() method

## UNIX BASED OS

TODO2-1: unix scripts for spark

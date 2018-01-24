# <img src="docs/images/logo.png" width="40" align="bottom" height="40"> ElastXY

ElastXY is an open, scalable, configurable **Distributed Genetic Algorithm Framework** for solving optimization and research problems.

Problems are addressed by creating an `Application`, suggesting a `Target` goal and  asking ElastXY heuristic via simple REST APIs to find a suitable `Solution` using all available devices (cores, CPUs and even computing aaS).

ElastXY can be natively:
- deployed locally as a **standalone Spring Boot** application or as an **Apache Spark cluster**
- distributed on multi-server environments via **Docker containers** managed by **Rancher Console**
- or even executed in serverless cloud computing services supporting Apache Spark, like **Google Dataproc** or **Amazon EMR**.

ElastXY is entirely written in **Java 8 language** and adopts **OAuth 2.0 security** standard flow.

*DISCLAIMER*: the project is at an early stage but growing and consolidating fast, so contributions and ideas are very welcome here! :)

> Curious why the butterfly? Discover the note at the end...

### Plug-ins

Optionally, following **SMACK stack** components can be enabled for:
- enable robust cluster management through **Apache Mesos**
- ingest big quantity of data via **Apache Cassandra** distributed database connector
- exchange async messaging for tracking execution and sharing results thanks to **Apache Kafka**
- plug-in another library as the algorithm Engine, via excellent **Jenetics Library**

### Roadmap
Roadmap is huge:
- showcase website with running applications, possibly written with Play, Scala and Akka
- PaaS for offering ElastXY services and registering own apps easily, without configuring a bit of infrastructure
- developer portal for easily integrate with ElastXY
- partial/complete porting to Scala language for simplifying and ease coding
- cover other OAuth 2.0 / OpenID Connect security flows and security concerns, like token signature and verification

and many other points.

## Stay Tuned

You can follow the project on @elastxy on Twitter or on its blog on GitHub.

## Quick Start
These instructions will get you a copy of the project up and running on your local machine for showcasing basic features, development and testing purposes.

See Deployment section for notes on how to deploy the project on a live and distributed system.

### Prerequisites
* JDK 8/9
* Maven 3.x

### 5' Installation
1. Clone or download the latest version of ElastXY, bundling sample applications, to a directory of your choice:

`git clone https://github.com/elastxy-projects/elastxy.git <ELASTXY_HOME>`

2. Change directory to \<ELASTXY_HOME\> and issue a Maven build:

`maven clean install`

Now you should have all ElastXY artifacts in your local Maven repo. Congratulations! :)

(And maybe you already spotted an ASCII representations of system evolution as long as tests were executed..)

### First example: build your own local StartApp
*Approx time: 10-15'*
 
The example will show you a simple showcase `Application`, and how to create your own project for a local (non distributed) env.

This simple showcase project is a variation of a well known Genetic Algorithm problem (and the first the author did, BTW), where the goal is to find an arithmetic expression resulting in a given target number.
> Example: 500=250 * 2, or 1000 / 2, or -500 + 1000, etc.

Here `Target` is a 64bit integer belonging to [-1000000;+1000000] interval, so with a brute-force approach you could explore potentially 4000000000000 (4*10^4) solutions! Let's have a look on how ElastXY can help here.

#### Step 1: Create the `Application`

The core concept of ElastXY is `Application`.
You can start defining your own by creating a Maven project from a blueprint by running following command in an empty directory of your choice:

`mvn archetype:generate -DarchetypeGroupId=org.elastxy -DarchetypeArtifactId=elastxy-app-archetype -DarchetypeVersion=1.0.0-SNAPSHOT`

When prompted, insert "com.acme" as groupId and "StartApp" as artifactId, leaving default package and version.

Now you should see a simple Maven project structure, bundling an initial example `Application`.

#### Step 2: Change default `Target` value

Every `Application` comes with a `Benchmark` configuration for testing algorithm performance after any parameters change. Think of it as our default values, for now.

Default `Target` for this `Application` is 235000, as you can see in configuration file here:
`src/main/resources/benchmark.json`

For the sake of curiosity, you can also set your own magic number, opening configuration file and putting your number (say 777) near target parameter:

```json
"applicationSpecifics": {
	"target" : {
		"TARGET_EXPRESSION_RESULT" : 235000
	},
...
```

And, of course you can start playing with other parameters around, if you please ;)


#### Step 3: Execute the `Experiment`

The `Experiment` is where the execution `Environment` resides, controls program flow until ended, and returns outcomes as `Results` and `ExperimentStats`.

It's time to build and test an execution: let's look it at work! Type:

```mvn test```

Application is bootstrapped and launched:
```
23:45:50.200 [main] INFO ... .AppBootstrap - Applications found: [StartApp]
23:45:50.200 [main] INFO ... .AppBootstrap - >> Bootstrapping application 'StartApp'
23:45:50.200 [main] INFO ... .AppBootstrap -    Building components..
23:45:50.225 [main] INFO ... .AppBootstrap -    Wiring components..
23:45:50.225 [main] INFO ... .AppBootstrap -    Initializing components..
23:45:50.226 [main] INFO ... .AppBootstrap -    Welcome to 'StartApp' application! <!!!>o
23:45:50.226 [main] INFO ... .AppBootstrap - Bootstrap ElastXY DONE.
```

#### Step 4: The results

After about 3" execution, you will notice an output similar to this one, much depending on how much you were lucky:
```
[1] elastxy>         |0---10---20---30---40---50---60---70---80---90---100
[1] elastxy>        1|--------------------------------------------------- | 0.9999999945 |SOL:Ge[[(P:0,M:operand)-158441, (P:1,M:operator)+, (P:2,M:operand)387971]] > Ph[(NumberPhenotype) 229530] > F[Value: 0.99999999453000128545, Check: true]
[1] elastxy>        2|--------------------------------------------------- | 0.9999999945 |SOL:Ge[[(P:0,M:operand)-158441, (P:1,M:operator)+, (P:2,M:operand)387971]] > Ph[(NumberPhenotype) 229530] > F[Value: 0.99999999453000128545, Check: true]
...
[1] elastxy>     3000|--------------------------------------------------- | 0.9999999986 |SOL:Ge[[(P:0,M:operand)-112897, (P:1,M:operator)-, (P:2,M:operand)-347896]] > Ph[(NumberPhenotype) 234999] > F[Value: 0.99999999999900000023, Check: true]
[1] elastxy>     4000|--------------------------------------------------- | 0.9999999986 |SOL:Ge[[(P:0,M:operand)-112897, (P:1,M:operator)-, (P:2,M:operand)-347896]] > Ph[(NumberPhenotype) 234999] > F[Value: 0.99999999999900000023, Check: true]
[1] elastxy>
[1] elastxy> ******* SUCCESS *******
[1] elastxy>
##################### STATS #####################
[1] elastxy> Best match:
[1] elastxy> SOL:Ge[[(P:0,M:operand)582896, (P:1,M:operator)+, (P:2,M:operand)-347896]] > Ph[(NumberPhenotype) 235000] > F[Value: 1.00000000000000000000, Check: true]
[1] elastxy> Number of generations: 4637
[1] elastxy> Total execution time (ms): 2327

```
Basically, ElastXY is saying:
> After having run 4637 generations, I found an optimal solution:
> 
> 582896 + (-346896) = 235000

This is your first ElastXY `Application`! Good job! :sparkles:

#### Bonus Step 5: Play with `Genes` metadata
If you had a look to following file, you may have seen an interesting feature:

`src/main/resources/genes.json`

Representation of this problem schemata* is entirely based on metadata: for example, you can see definition of genes composing the chromosomes as two user-defined type: "operand" and "operator".

If you reduce available operators `Gene` to "*" and "\" and relaunch the execution, you may notice how it's more difficult the job, even converging.

`mvn test`

**: schemata are minimal evolving genetic material strings the algorithm is based on, typically group of genes, like chromosomes*

### Second example: Build your first distributed ElastXY app
*Approx time: 20-30'*

The example will show you a little more complex showcase `Application`, and how to run it on a local **Apache Spark** cluster.

As a prerequisite, we assume you have cloned ElastXY and compiled modules to a `ELASTXY_HOME` directory as a part of first example.

Please note that to keep example as simple as possible, we will run Apache Spark cluster with a minimal setup in a Standalone mode cluster with all production settings turned off (e.g. security, cluster managemet or asynch messaging). For a meaningful setup, please look at Documentation, "Setting up a Cluster". 

#### Step 1: Install Apache Spark

- Download Apache Spark version "spark-2.2.0-bin-hadoop2.7" from [download site](https://spark.apache.org/downloads.html)
- Unzip in a directory of your choice, our `<SPARK_HOME>`
- Copy Spark configurations `spark-defaults.conf` from `elastxy-web/conf` to `<SPARK_HOME>/conf`
- Open `spark-defaults.conf` and change local paths inside file according to your system

#### Step 2: Run Apache Spark

- Get into ElastXY modules project root (directory where you cloned and compiled ElastXY modules) 
- From `elastxy-web/conf` Copy scripts `run-master.cmd` and `run-master.cmd` to `<SPARK_HOME>/bin` directory.
- `run-master.cmd`: open the file and change the parameter `--webui-port` if you have processes that already use that port.
- `run-worker.cmd`: open the file and set local hostname or IP address (127.0.0.1 on localhost), and review available cores (-c) and memory (-m) parameters based on your machine capacity
- Run master script one time and worker two times: you should have an Apache Spark Driver up and running, with a Driver listening to default port 7077, and two Agents working for you accepting jobs!

#### Step 3: Run ElastXY in distributed mode

Two words explanation: ElastXY web application API will run a simple benchmark application requesting execution to Spark Driver, which in turn will delegate Workers the hard job until an end condition or a best match `Solution` are found.
Then, results is collected by Spark Driver from Workers and made it available to web application to be returned to User.
(We haven't configured messaging system for this example, so we communicate by means of temp messages on shared local directory).

- Open file `elastxy-web/src/main/resources/distributed.properties`
- Change properties accordingly to your environment settings. Please note that for results to be returned by APIs, the `webapp.inbound.path` must be identical to `driver.outbound.path`, as explained above
- Execute following Maven command:

`mvn exec:java -Dexec.mainClass="org.elastxy.web.application.ElastXYWebApplication"`

You should see SpringBoot web application bootstrapped with all showcase applications running on port 8080:
```
... : Tomcat started on port(s): 8080 (http)
... : Started ElastXYWebApplication in 19.14 seconds (JVM running for 20.543)
```

- Call following APIs to run your local cluster, from a web browser address bar or your favourite HTTP client:

`http://localhost:8080/distributed/local/experiment/expressions_d`

Note: you can also import ready-to-go ElastXY Postman collection from `<ELASTXY_HOME>/src/main/resources`.

Now, you should see the raw `Solution` result as a reponse, like this:
```
...
"phenotype": {
	"value": 235000
},
"fitness": {
	"value": 1,
...
```

There are other means to get results and resulting response could be more friendly, but, ehy!, this is just a simple example.

Go on reading for a further description of how ElastXY works, capabilities, and so on...


## Project Features

### Main goal
ElastXY is an open, scalable, configurable **Distributed Genetic Algorithm Framework** for solving optimization and research problems.

Given a problem to solve, ElastXY allows users to define their own specific goal as a `Target`, choose or implement `Application` components to achieve it and their preferred execution environment (local, distributed, cloud) to horizontally scale, if needed.

ElastXY natively adopts **Apache Spark** as a distributed computing framework, wrapped behind a simple API interface which can be easily integrated on client envs.

At runtime, user is allowed to call simple APIs to run his own `Application` with specific execution parameters, monitoring `Experiment` execution, and finally receiving feedback as an optimal or sub-optimal `Solution`, with `ExperimentStats` to show results.

Anything having a representation can be a `Target` or a `Solution`, and almost all `Application` components can be extended (Genetic Engine included).

### Advanced features
Optionally, a more advanced configuration could include:
- clustering managed by **Apache Mesos** (default: Standalone mode)
- asynchronous messaging system for collecting runtime events and results via `EnvObserver` based on **Apache Kafka** (default: shared file-system)
- distributed database connector to **Apache Cassandra** (default: depending on `DatasetProvider` implementation)

[Note: it can be noticed that these technologies form the **SMACK Framework** without A(kka), but don't worry: Actor model with a Play frontend showcase application is on roadmap... ;) ]

### How it works

First of all, as any other Genetic Algorithm library or framework, ElastXY can elaborate generations of Solutions until reaching an end condition based on configurable runtime parameters set by user, either:
- the optimal Solution is found
- a sub-optimal one is found
- another end condition is found, such as a fitness threshold, a maximum execution time or number of generations, a minimum/maximum/exact fitness values, etc.

ElastXY execution operates through following components:
* a basic set of genetic operators (selection, mutation, crossover) with default implementation, but fully configurable and customizable
* elitism policy: proportion of elite with respect to population
* customizable genoma provider with straightforward domain (Gene, Allele, Chromosome, Strand)
* metadata based Genes user definition
* customizable solution renderer
* environment observer to track execution

### REST APIs
Once Application is bootstrapped, ElastXY **REST APIs** are available for:
* requesting a fully parametrized algorithm execution
* executing a fixed benchmark for checking fine parameters tuning
* testing algorithm performance comparing to a "total random" execution
* do some analytics, by requesting a (potentially big) number of executions
* some monitoring and health-checks


### Other features
* four distinct example applications to showcase common use cases
* application templates via Maven archetype

## Framework Components

### The `Application`

As explained, the core concept is the `Application`. Here happens all the magics.

But, fortunately, all `Application` components can be configured via some Java coding and a simple JSON metadata file, to be injected by an internal lightweight injection module at application Bootstrap phase (no Spring/Guice/whatever dependencies here, only simple raw Java code).

All framework components are interface and default implementations *open to extension* by design, so behaviour can be completely redefined.

But generally they prefer conventions over configurations and at least one default implementation is provided. So, ehy, don't be scared to play with them! :)

### Main framework abstractions
To be more specific, an ElastXY `Application` is written by the User, who defines a `Target` and all components needed for it to be achieved.

Most of the 

Engine components:
- the crucial `FitnessCalculator` function to establish who's best between `Solution` of a given `Population`
- `Genotype`, optionally defined via `Genes` metadata, as the genetic material algorithm works on
- `Incubator` grow function to raise it to a `Phenotype` representation, against which fitness is calculated, exactly as giraffe neck influences her chance to survival, not the DNA strand 
- genetic operators like `Selector`, `Recombinator` and `Mutator`

Infrastructural components:
- `DatasetProvider` to ingest input (big)data
- `EnvObserver` to receive and eventually redistributed execution events of interest

Rendering/reporting components:
- `SolutionRenderer` to "see" `Solution` in a friendly way, if needed
- `ResultsRenderer` to export resulting `Solution` and `ExperimentStats` in a readable format

Advanced Engine components:
- `AlleleGenerator` to define how to build `Gene` values composing `Solutions` (usually the `MetadataAlleleGenerator` should be enough)
- factory methods for creating execution context, such as: `PopulationFactory`, `EnvFactory`, `GenomaProvider`

Distributed context components

Many of the above have a counterpart for distributed computing of Spark Dataset, Cassandra tables, and so on.


## Framework Runtime Contexts

#### Single Colony Execution [local]
In a `SingleColonyExperiment`, ElastXY tries to solve the problem by evolving generations of candidate `Solutions` over a single `Era`, until `EndCondition` is found.

`EndCondition` includes:
* maximum, minimum or exact value of a `Fitness` function typically in [0.0;1.0] interval
* `Fitness` minimum threshold
* max number of generations
* max execution time in ms

### Multiple Colony Execution [distributed]
This is the distributed context, where Solution population is equally divided (partitioned) to be further executed on separate Spark node workers, runned through an `Era`, and finally ended.
Partitioning is a key concept here: a `DistributedDatasetProvider` helps creating such partitions and distributed database if needed, and a `MultiColonyEvolver` distributes processing around, collecting results back to Client.

Optionally, a specific `SolutionRenderer` can be implemented for returning a specific representation of the `Solution` other than raw data to the Client.
For example, one can need an html template representation for a web site.

Also, `EnvObserver` can be instructed to send asynchronous messages for coarse or fine tracking of executions.


## Documentation

### Architecture
A detailed architecture diagram is shown in technical reference documentation [TBD]

### Modules
The main components of the framework, from a deployment perspective, are:
* **elastxy-core**: module containing the **Genetics Algorithm Engine** with logics, domain and metadata, plus application components and configurations, interfaces and standard implementations, tools for rendering, collecting statistics, etc. 
* **elastxy-web**: the web application publishes all APIS and controls local and distributed `Experiment` executions, running cluster Driver and returning results.
* **elastxy-distributed**: the module containing the distributed algorithm parts, to be executed on the cluster driver and executors.
* **elastxy-applications**: module with a number of sample working applications covering main standard use cases, which can be run both locally and distributed.
* **elastxy-maven-archetype**: sample Maven archetype for start coding in a minute with basic local application

### Runtime
Locally, a Spring Boot **Web Application** exposes APIs for running ElastXY applications. Examples are available as Postman Collections and documented via Swagger API Documentation.

Distributed processing is also started by Web Application APIs, runned inside as a Driver, then delivered to **Apache Spark Cluster** coordinated by Apache Mesos, either on-premise or optionally by connecting cloud computing services, like Amazon EMR or Google Dataproc.

Optionally, ElastXY is able to ingest and analyse distributed database via **Apache Cassandra** based `DatasetProvider`, and send asynchronous events with an `EnvObserver` implemented via Apache Kafka.

## Deployment
ElastXY works either locally (Single Colony) or in a clustered environment (Multi Colony), deployed either:
* as a standalone Spring Boot application
* distributed in an on-premise Apache Spark cluster datacenter
* distributed as a cloud application via containerized Docker images and controlled by its Rancher Catalog
* or even, it can produce an uberjar to be published to Google Cloud Dataproc or Amazon AMS EMR Spark processing providers

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring](https://spring.io/) - adopted for building the web application via Spring Boot and APIs via Spring MVC 
* [Postman](https://www.getpostman.com/apps) - collection of sample calls for interacting with ElastXY
* [Apache Spark](https://spark.apache.org/) - The Distributed Computing Framework natively adopted

Optional:
* [Apache Mesos](https://mesos.apache.org/) - The Cluster Manager used for governing both Spark cluster and containers
* [Apache Cassandra](http://cassandra.apache.org/) - The locality aware Distributed Database holding data under the hood
* [Apache Kafka](https://kafka.apache.org/) - The Messaging System for collecting events during execution
* [Docker](https://www.docker.com/) - Used to containerize deliverables
* [Rancher](https://rancher.com/) - The Container Management console

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Gabriele Rossi** - *Initial work* - [ElastXY](https://github.com/ElastXY)
* **Annalisa Garlaschi** - *Design* - [ElastXY](https://github.com/ElastXY)

See also the list of [contributors](https://github.com/ElastXY/contributors) who participated in this project.

## License

This project is licensed under the Apache 2.0 - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

* Thanks to Apache Math3 library for CX (Cycle Crossover) operator implementation, from which I derived a CXD implementation also working with duplicates 
* My wife as an intelligent fount of inspiration and brilliant ElastXY logo creator
* All people liking, using, citing, criticizing and contributing to project in every form.

## And finally...

Why "ElastXY"?
> Nothing special, X and Y being the two most famous chromosomes grouping genes of human genetic material.

Why the butterfly?

> At the heart of Genetic Algorithms lies Probability, basically being them modeled as a convolution of statistical distributions over time (see Markov chains) with possibly convergent results under certain circumstances, or non-convergent results in others.

>The idea of Markov chains reminded me Statistics course at University and the Butterfly Effect, which is not something I could foresee in my framework, nonetheless imagination wanders on how a slight ripple on big sea of data and tiny schemata could grow after Eras and Eons... to a definite, useful, Solution. Or nothing interesting.

> Gabriele (author of ElastXY Framework)

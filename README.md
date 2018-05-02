# <img src="docs/images/logo.png" width="40" align="bottom" height="40"> ElastXY

This is the home of the ElastXY Framework, the foundation for all
ElastXY projects.

ElastXY is an open, scalable, configurable **Distributed Genetic Algorithm Framework** for solving optimization and research problems, powered by **Apache Spark**.

Please read the [Official Documentation](http://elastxy.io/documentation) for a more complete introduction, or visit [Project Blog and Official Web Site](http://elastxy.io).

## Quick start

Try out the [10' Single Colony Tutorial](http://elastxy.io/tutorials/#single-colony-tutorial) for building your own app for local execution, or the [20' Multi Colony Tutorial](http://elastxy.io/tutorials/#multi-colony-tutorial) for building a sample distributed application.
**IMPORTANT**: Multi Colony Tutorial requires at least an application to work, which will be added in days.

## Documentation

The ElastXY Framework currently maintains a single page reference [Documentation](http://elastxy.io/documentation) on its website and a simple [Changelog](docs/CHANGELOG.md) here for non trivial changes.

A [Swagger UI](http://localhost:8080/swagger-ui.html#/) is available when running locally (to be further polished).

In a near futures also Github wiki pages and an API reference will be made available through the web site, near guides and tutorials across ElastXY projects for all interesting use cases.

You may follow project by its Twitter account [@elastxy](https://twitter.com/elastxy) to be notified.

## Contributing and Code of conduct

Contributions are very welcome! You can contribute following project [Contribution](docs/CONTRIBUTING.md) guidelines.

This project is governed by the project [Code of Conduct](docs/CODE_OF_CONDUCT.md).
By participating you are expected to uphold this code.
Please report unacceptable behavior to [elastxy@mail.com](mailto:elastxy@mail.com?subject=Conduit).

## Access to binaries

You can obtain current version of ElastXY Framework and related subprojects by building them locally (see [Build from source](http://elastxy.io/tutorials/#quick-start) section).

ElastXY binaries are not yet uploaded to Maven Central: when uploaded, you will be notified through this page or via [@elastxy](https://twitter.com/elastxy) Twitter account.

## Build from source

See the [Build from source](http://elastxy.io/tutorials/#quick-start) section of documentation page for building locally all necessary artifacts.

## Stay in touch

You can follow the project on [@elastxy](https://twitter.com/elastxy) on Twitter, or on its [Blog](http://elastxy.io).
Releases or major progresses are announced via those channels.

## Questions?

[Open an Issue](https://github.com/elastxy/elastxy-framework/issues/new) and let's chat!

## License

The ElastXY Framework is released under version 2.0 of the
[Apache License](http://www.apache.org/licenses/LICENSE-2.0).

You also may obtain a copy here: see [LICENSE](LICENSE) file.

### Authors

* **Gabriele Rossi** - *Initial work* - See [Contact](http://elastxy.io/aboutme) page on project web site.

See also the list of [contributors](https://github.com/elastxy/elastxy-framework/contributors) who participated in this project.

### Credits

* Thanks to Apache Math3 library for CX (Cycle Crossover) operator implementation, from which I derived a CXD implementation also working with duplicates (opened [issue](https://issues.apache.org/jira/browse/MATH-1451)).
* My wife as an intelligent fount of inspiration, ElastXY logo creator and for the first [user story](http://elastxy.io/2018-02-18-elastxy-taking-off).
* All people liking, using, citing, criticizing and contributing to the project in every form.

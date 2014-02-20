MetaModelExample
================

Example of using the Apache Incubator [MetaModel API](http://metamodel.incubator.apache.org/) in TestNG DataProvider.   Uses a SQL query API supplied by MetaModel to build the 2D array returned by the DataProvider method.

Three examples that include:

1. A DataProvider that reads data from a Excel .XLS or .XLSX file.
2. A DataProvider that reads data from a CSV file.
3. A DataProvider that reads data from a XML file.



##### Maven dependency

Requires the following dependency in your pom.xml file.
```
<dependency>
    <groupId>org.eobjects.metamodel</groupId>
    <artifactId>MetaModel</artifactId>
    <version>3.4.9</version>
</dependency>
```



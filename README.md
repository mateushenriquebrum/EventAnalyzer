# Goal

The goal of this command line application is to merge events, that comes in parties from a file and save into a database.

The application use clean architecture, **multi-thread** and support files over **1GB**.

# How to run
```
$> git clone https://github.com/mateushenriquebrum/EventAnalyzer
$> cd EventAnalyzer
$> chmod +x gradlew
$> ./gradlew build
$> java -jar build/libs/EventAnalyzer-1.0.0.jar $(pwd)/events.txt
```

You will see the import in progress, after all you can access the data by **jdbc:hsqldb:file:$(pwd)\hsqldb\events** uri

# Architecture
The architecture consiste in a version of infamous [MapReduce Architecture](https://www.geeksforgeeks.org/mapreduce-architecture/) but in this case using threads as processing unit.

All the caution were toke to keep infrastructure code away from domain code, the pattern used was [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).

![Alt text](diagram.jpg?raw=true "Architecture Diagram")

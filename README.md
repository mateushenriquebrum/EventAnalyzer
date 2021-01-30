#Goal
The goal of this command line application is to merge events from a file.
The application use clean architecture and support files over 1GB.

#How to run

> git clone https://github.com/mateushenriquebrum/EventAnalyzer

> cd EventAnalyzer

> ./gradlew build

> java -jar build/libs/EventAnalyzer-1.0.0.jar (<full-path-of-your-text-file> | $(pwd)/events.txt)

You will see the import in progress, after all you can access the data by **jdbc:hsqldb:file:$(pwd)\hsqldb\events** uri

#Architecture

:TODO
# Money transfer project for interview
## Development stage
To run tests project while development execute
```
mvn clean test
```
## Run option 1
To run project with maven execute
```
mvn clean compile exec:java
```
## Run option 2
To assembly project with dependencies execute
```
mvn clean package
```
Then run it with
```
java -jar target/money-transfer-1.0-SNAPSHOT.jar
```
## Run option 3
To assembly as standalone jar execute
```
mvn clean compile assembly:single
```
Then run it with (not working yet - problem with Jersey MessageBodyWriter)
```
java -jar money-transfer-1.0-SNAPSHOT-jar-with-dependencies.jar
```
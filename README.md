### Money transfer project for interview

To run tests project while development execute
```
mvn clean test
```

To run project with maven execute
```
mvn clean compile exec:java
```

To compile and run project as standalone jar execute
```
mvn clean compile assembly:single
java -jar money-transfer-1.0-SNAPSHOT-jar-with-dependencies.jar
```
### Money transfer project for interview

To test project while development execute
```
mvn clean test
```

To run with maven execute
```
mvn exec:java
```

To compile and run as standalone jar execute
```
mvn clean compile assembly:single
java -jar money-transfer-1.0-SNAPSHOT-jar-with-dependencies.jar
```
# Money transfer project for interview
## Development stage
To run tests project while development execute
```
mvn clean test
```
## Run
### Run option 1
To run project with maven execute
```
mvn clean compile exec:java
```
### Run option 2
To assembly project with dependencies execute
```
mvn clean package
```
Then run it with
```
java -jar target/money-transfer-2.0-SNAPSHOT.jar
```
### Run option 3
To assembly as standalone jar execute
```
mvn clean compile assembly:single
```
Then run it with (not working yet - problem with Jersey MessageBodyWriter)
```
java -jar target/money-transfer-2.0-SNAPSHOT-jar-with-dependencies.jar
```
## Testing with curl XML
create new accounts
```
curl -i -X POST -H "Content-Type:application/xml" http://localhost:8080/revolut/api/account/create -d ""
curl -i -X POST -H "Content-Type:application/xml" http://localhost:8080/revolut/api/account/create -d ""
```
check ballance
```
curl -i http://localhost:8080/revolut/api/account/1/balance
curl -i http://localhost:8080/revolut/api/account/2/balance
```
deposit on account id=1 10 dollars
```
curl -i -X PUT http://localhost:8080/revolut/api/account/1/deposit -H "Content-Type: application/xml" -d "<money><amount>10</amount></money>"
```
withdraw from account id=1 4 dollars and 25 cents
```
curl -i -X PUT http://localhost:8080/revolut/api/account/1/withdraw -H "Content-Type: application/xml" -d "<money><amount>4.25</amount></money>"
```
transfer from account id=1 to account id=2 2 dollars and 53 cents
```
curl -i -X PUT http://localhost:8080/revolut/api/transfer -H "Content-Type: application/xml" -d "<transferData><amount>2.53</amount><from>1</from><to>2</to></transferData>"
```
## Testing with curl JSON
create new accounts
```
curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/revolut/api/account/create -d "{}"
curl -i -X POST -H "Content-Type:application/json" http://localhost:8080/revolut/api/account/create -d "{}"
```
check ballance
```
curl -i http://localhost:8080/revolut/api/account/1/balance
curl -i http://localhost:8080/revolut/api/account/2/balance
```
deposit on account id=1 10 dollars
```
curl -i -X PUT http://localhost:8080/revolut/api/account/1/deposit -H "Content-Type: application/json" -d "{\"amount\":\"10\"}"
```
withdraw from account id=1 4 dollars and 25 cents
```
curl -i -X PUT http://localhost:8080/revolut/api/account/1/withdraw -H "Content-Type: application/json" -d "{\"amount\":\"4.25\"}"
```
transfer from account id=1 to account id=2 2 dollars and 53 cents
```
curl -i -X PUT http://localhost:8080/revolut/api/transfer -H "Content-Type: application/json" -d "{\"amount\":\"2.53\",\"from\":\"1\",\"to\":\"2\"}"
```
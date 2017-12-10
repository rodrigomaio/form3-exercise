##FORM3 Coding Exercise – Back-end Developer
Rodrigo Souza

http://github.com/rodrigomaio/form3-exercise

The project consists in a REST API to manage payment resources, based on a sample list of payments response provided.

The API was built using Java 8, Spring Boot and Gradle. The persistence is done by a in-memory hsqldb.

The list of endpoints:
- GET /payments/{id} : get the Payment with the specified id.
- PUT /payments/{id} : update the Payment with the specified id.
- DELETE /payments/{id} : delete the Payment with the specified id.
- POST /payments : create a new Payment.
- GET /payments : get all Payments.


To run:
```
./gradlew bootRun
```

To test:
```
./gradlew test
```


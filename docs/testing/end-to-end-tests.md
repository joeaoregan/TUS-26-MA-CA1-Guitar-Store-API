# End-to-End (E2E) / API Tests

End-to-end tests validate the full application flow from an external client perspective, including HTTP routing, validation, service logic, persistence, and JSON serialization. These tests are fewer in number because they are slower and more environment-dependent, but they provide high confidence in real behaviour.

## Implemented E2E Test Suites
- `src/test/java/edu/tus/guitarstore/e2e/GuitarApiE2ETest.java`  
  Starts the Spring Boot application (`@SpringBootTest(webEnvironment = RANDOM_PORT)`) and uses `TestRestTemplate` to exercise REST endpoints over HTTP.
- `src/test/java/edu/tus/guitarstore/karate/BrandKarateTest.java` + `src/test/java/edu/tus/guitarstore/karate/brands.feature`  
  Karate feature tests executed against a locally running instance (`http://localhost:8080`). Scenarios include happy path (create + fetch), validation failure (400), not found (404), and a create→delete→verify lifecycle flow.
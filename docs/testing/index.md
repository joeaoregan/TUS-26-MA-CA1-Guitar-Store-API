# Overview 
## Test Pyramid Overview

This project follows the **Test Pyramid** approach: the majority of tests are fast, isolated **unit tests**, supported by a smaller number of **integration tests**, and a small set of high-value **end-to-end (E2E) / API tests**. This keeps feedback fast while still giving confidence that the full application works correctly when deployed and accessed via HTTP.

![Test Pyramid](../images/test-pyramid.jpg)

### Unit Tests (base of the pyramid)
Unit tests focus on individual classes (typically service-layer logic) in isolation using **JUnit 5** and **Mockito**. Dependencies such as repositories are mocked so that tests execute quickly and fail only when the unit’s business logic is incorrect. These tests provide the bulk of coverage and fast feedback during development.

**Unit test suite(s) in this project:**
- `src/test/java/edu/tus/guitarstore/service/GuitarServiceTest.java`  
  Service-layer unit tests using Mockito to mock repository dependencies and verify service behaviour in isolation.

[Go To Unit Tests](unit-tests.md)  

---

### Integration Tests (middle of the pyramid)
Integration tests verify that multiple components work together, typically including the **database layer** (e.g., JPA repositories and entity mappings) using a real H2 test database. These tests ensure persistence, mappings, constraints, and repository queries behave as expected in a realistic environment.

**Integration test suite(s) in this project:**
- `src/test/java/edu/tus/guitarstore/repository/GuitarRepositoryIntegrationTest.java`  
  Repository-level integration tests that verify Spring Data JPA queries and persistence against the test database.
- `src/test/java/edu/tus/guitarstore/repository/GuitarRepositoryTest.java`  
  Additional repository tests that validate CRUD/query behaviour and entity mapping assumptions.
- `src/test/java/edu/tus/guitarstore/config/TestJpaAuditingConfig.java`  
  Test-only configuration to supply auditing context (e.g., `AuditorAware`) required by JPA auditing fields during integration/E2E tests.

[Go To Integration Tests](integration-tests.md)

---

### End-to-End / API Tests (top of the pyramid)
A small number of E2E tests validate the system from an external client perspective by calling the REST API over HTTP. Two complementary approaches are used:
- **JUnit + TestRestTemplate** (`@SpringBootTest(webEnvironment = RANDOM_PORT)`) to start the application in the test runtime and verify real controller/service/repository/DB behaviour.
- **Karate feature tests** against `http://localhost:8080` for readable, scenario-based API checks (happy path, validation failures, not-found responses, and simple lifecycle flows such as create→delete→verify).
These tests provide high confidence in the complete request/response flow but are intentionally fewer in number because they are slower and more environment-dependent.

**End-to-end test suite(s) in this project:**
- `src/test/java/edu/tus/guitarstore/e2e/GuitarApiE2ETest.java`  
  JUnit-based E2E tests that start the full Spring Boot application and exercise REST endpoints via HTTP.
- `src/test/java/edu/tus/guitarstore/karate/BrandKarateTest.java` and `src/test/java/edu/tus/guitarstore/karate/brands.feature`  
  Karate-based black-box API tests for the Brand endpoints, executed against a locally running app on `http://localhost:8080`.
  
[Go To E2E Tests](end-to-end-tests.md)  
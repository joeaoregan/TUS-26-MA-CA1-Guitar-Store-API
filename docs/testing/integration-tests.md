# Integration Tests

Integration tests verify that the persistence layer works correctly with Spring Boot and Spring Data JPA, including entity mappings and repository queries executed against the test database. These tests are slower than unit tests but provide confidence that the repository layer behaves correctly in a realistic environment.

## Implemented Integration Test Suites
- `src/test/java/edu/tus/guitarstore/repository/GuitarRepositoryIntegrationTest.java`  
  Validates repository queries and persistence behaviour using Spring’s test support and the configured test database.
- `src/test/java/edu/tus/guitarstore/repository/GuitarRepositoryTest.java`  
  Additional repository tests covering CRUD/query behaviour and mapping assumptions.
- `src/test/java/edu/tus/guitarstore/config/TestJpaAuditingConfig.java`  
  Test-only auditing configuration required for entities that use auditing fields (e.g., created/updated metadata).
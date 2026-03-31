# Unit Tests

Unit tests validate service-layer behaviour in isolation using **JUnit 5** and **Mockito**. Repository dependencies are mocked so tests run quickly and focus on business logic rather than persistence or HTTP concerns.

## Implemented Unit Test Suites
- `src/test/java/edu/tus/guitarstore/service/GuitarServiceTest.java`  
  Tests service logic by mocking repository calls and verifying returned DTOs and repository interactions.

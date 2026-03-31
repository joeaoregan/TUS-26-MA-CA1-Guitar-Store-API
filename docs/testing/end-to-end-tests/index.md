# End-to-End (E2E) / API Tests

End-to-end tests validate the full application flow from an external client perspective, including HTTP routing, validation, service logic, persistence, and JSON serialization. These tests are fewer in number because they are slower and more environment-dependent, but they provide high confidence in real behaviour.

## Implemented E2E Test Suites
- `src/test/java/edu/tus/guitarstore/e2e/GuitarApiE2ETest.java`  
  Starts the Spring Boot application (`@SpringBootTest(webEnvironment = RANDOM_PORT)`) and uses `TestRestTemplate` to exercise REST endpoints over HTTP.
- `src/test/java/edu/tus/guitarstore/karate/BrandKarateTest.java` + `src/test/java/edu/tus/guitarstore/karate/brands.feature`  
  Karate feature tests executed against a locally running instance (`http://localhost:8080`). Scenarios include happy path (create + fetch), validation failure (400), not found (404), and a create→delete→verify lifecycle flow.

---

## GuitarApiE2ETest.java

- [createBrand_thenCreateGuitar_thenFetchByModelName()](api-end-to-end.md#createbrand_thencreateguitar_thenfetchbymodelname)
- [creatingDuplicateGuitarModelNameShouldReturnClientError()](api-end-to-end.md#creatingduplicateguitarmodelnameshouldreturnclienterror)
- [creatingGuitarWithoutManufactureDateShouldReturn400()](api-end-to-end.md#creatingguitarwithoutmanufacturedateshouldreturn400)

---

## brands.feature

- [Scenario: Happy path - create a brand then fetch it](karate.md#scenario-happy-path-create-a-brand-then-fetch-it)
- [Scenario: Validation failure - missing country should return 400](karate.md#scenario-validation-failure-missing-country-should-return-400)
- [Scenario: Not found - fetching unknown brand should return 404](karate.md#scenario-not-found-fetching-unknown-brand-should-return-404)

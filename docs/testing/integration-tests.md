# Integration Tests

Integration tests verify that the persistence layer works correctly with Spring Boot and Spring Data JPA, including entity mappings and repository queries executed against the test database. These tests are slower than unit tests but provide confidence that the repository layer behaves correctly in a realistic environment.

## Implemented Integration Test Suites
- `src/test/java/edu/tus/guitarstore/repository/GuitarRepositoryIntegrationTest.java`  
  Validates repository queries and persistence behaviour using Spring’s test support and the configured test database.
- `src/test/java/edu/tus/guitarstore/repository/GuitarRepositoryTest.java`  
  Additional repository tests covering CRUD/query behaviour and mapping assumptions.
- `src/test/java/edu/tus/guitarstore/config/TestJpaAuditingConfig.java`  
  Test-only auditing configuration required for entities that use auditing fields (e.g., created/updated metadata).

---

## GuitarRepositoryIntegrationTests.java (Integration Tests)

1. [**Basic query behaviour** (happy path + not found)](#testfindbymodelnameexactmatch)
2. [**Query correctness with multiple records** (precision / false positives)](#testfindbymodelnameamongmultiplerecords)
3. [**Data integrity / constraints** (database rules)](#testmodelnameuniqueconstraint)
4. [**Mapping completeness** (all columns / attributes)](#testfindbymodelnameretrievesallattributes)
5. [**Relationships** (Brand association correctness)](#testfindbymodelnamepreservesbrandrelationship)
6. [**Optional semantics / repository contract behaviour**](#testfindbymodelnamereturnsindependentoptionals)
7. [**Edge cases in querying** (case sensitivity, special characters)](#testfindbymodelnamecasesensitivity)
8. [**JPA fetch strategy / lazy loading behaviour**](#testfindbymodelnamebrandlazyloading)

## 1. **Basic query behaviour** (happy path + not found)

### testFindByModelNameExactMatch()
Verifies the repository returns a Guitar when modelName matches exactly. Confirms persisted fields (modelName, price, manufactureDate) and the Brand relationship are retrieved correctly.

### testFindByModelNameNotFound()
Verifies the repository returns Optional.empty() when a model name does not exist. Confirms the query does not return unexpected data.

---

## 2. **Query correctness with multiple records** (precision / false positives)

### testFindByModelNameAmongMultipleRecords()
Verifies the repository returns the correct guitar when multiple guitars exist in the database. Confirms the query targets the correct record and not a different row.

### testFindByModelNameDoesNotReturnSimilarNames()
Adds a “similar but not equal” model name (e.g., StratocasterPlus) and verifies the exact name still resolves to the correct row. Confirms the query is exact-match and does not return partial matches.

---

## 3. **Data integrity / constraints** (database rules)

### testModelNameUniqueConstraint()
Attempts to persist a second guitar with an existing modelName and asserts a DataIntegrityViolationException. Confirms the UNIQUE constraint is enforced at the database level.

---

## 4. **Mapping completeness** (all columns / attributes)

### testFindByModelNameRetrievesAllAttributes()
Verifies that all guitar attributes are correctly persisted and retrieved from the database. Confirms entity mapping correctness (id, modelName, price, manufactureDate, brand id).

---

## 5. **Relationships** (Brand association correctness)

### testFindByModelNamePreservesBrandRelationship()
Verifies that when a guitar is retrieved, its associated Brand is also correctly mapped and accessible. Confirms relationship integrity (brand id/name/country).

---

## 6. **Optional semantics / repository contract behaviour**

### testFindByModelNameReturnsIndependentOptionals()
Runs the same repository query twice and ensures both results contain consistent data. Confirms repeated queries behave deterministically and don’t mutate shared state.

---

## 7. **Edge cases in querying** (case sensitivity, special characters)

### testFindByModelNameCaseSensitivity()
Verifies the query behaviour is case-sensitive by checking that wrong-case values do not match. Confirms only the exact-cased model name returns a result.

### testFindByModelNameWithSpecialCharacters()
Persists a guitar with special characters in the model name and verifies it can be retrieved correctly. Confirms the database + JPA mapping handle special characters in query parameters.

---

## 8. **JPA fetch strategy / lazy loading behaviour**

### testFindByModelNameBrandLazyLoading()
Retrieves a guitar and then accesses the brand fields to verify the relationship can be loaded through JPA. Confirms the entity association remains usable after the initial query (lazy loading behaviour).

--- 

## GuitarRepositoryTest.java (Mock-based “Unit-Style” Repository Tests)

> Note: Although this class lives under the `repository` package, it uses **Mockito** to mock `GuitarRepository`. This means the tests validate *expected repository interactions / return shapes* in isolation, and **do not** hit a real database. The real persistence behaviour is covered by `GuitarRepositoryIntegrationTest` (`@DataJpaTest`).

1. [**Basic query behaviour** (happy path + not found)](#testfindbymodelnamesuccess)
2. [**Multiple calls / different inputs** (repository contract behaviour)](#testfindbymodelnamemultiplecalls)
3. [**Entity integrity** (returned object contains expected fields)](#testfindbymodelnameentityintegrity)

---

## 1. **Basic query behaviour** (happy path + not found)

### testFindByModelNameSuccess()
Mocks `findByModelName()` to return a populated `Guitar` entity and verifies the returned `Optional` is present. Confirms that key fields (modelName, price, manufactureDate, brand) match the expected test entity.

### testFindByModelNameNotFound()
Mocks `findByModelName()` to return `Optional.empty()` and verifies the result is empty. Confirms the “not found” path behaves as expected and does not return an unexpected entity.

---

## 2. **Multiple calls / different inputs** (repository contract behaviour)

### testFindByModelNameMultipleCalls()
Mocks two different model name lookups to return two different `Guitar` entities. Verifies both calls return the correct results, demonstrating predictable behaviour across multiple invocations with different inputs.

---

## 3. **Entity integrity** (returned object contains expected fields)

### testFindByModelNameEntityIntegrity()
Mocks `findByModelName()` to return a `Guitar` entity and asserts that all expected attributes are present and correct (id, modelName, price, manufactureDate, brand). Confirms the service/test fixtures treat the returned entity as fully populated.
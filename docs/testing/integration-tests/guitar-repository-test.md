# GuitarRepositoryTest.java 
## (Mock-based “Unit-Style” Repository Tests)

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
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
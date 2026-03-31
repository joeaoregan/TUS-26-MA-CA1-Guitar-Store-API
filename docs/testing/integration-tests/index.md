# Overview

Integration tests verify that the persistence layer works correctly with Spring Boot and Spring Data JPA, including entity mappings and repository queries executed against the test database. These tests are slower than unit tests but provide confidence that the repository layer behaves correctly in a realistic environment.

## Implemented Integration Test Suites
- `src/test/java/edu/tus/guitarstore/repository/GuitarRepositoryIntegrationTest.java`  
  Validates repository queries and persistence behaviour using Spring’s test support and the configured test database.
- `src/test/java/edu/tus/guitarstore/repository/GuitarRepositoryTest.java`  
  Additional repository tests covering CRUD/query behaviour and mapping assumptions.
- `src/test/java/edu/tus/guitarstore/config/TestJpaAuditingConfig.java`  
  Test-only auditing configuration required for entities that use auditing fields (e.g., created/updated metadata).

## GuitarRepositoryIntegrationTests.java (Integration Tests)

1. **Basic query behaviour** (happy path + not found)
    - [testFindByModelNameExactMatch()](guitar-repository-integration-test.md#testfindbymodelnameexactmatch)
    - [testFindByModelNameNotFound()](guitar-repository-integration-test.md#testfindbymodelnamenotfound)
2. **Query correctness with multiple records** (precision / false positives)
    - [testFindByModelNameAmongMultipleRecords()](guitar-repository-integration-test.md#testfindbymodelnameamongmultiplerecords)
    - [testFindByModelNameDoesNotReturnSimilarNames()](guitar-repository-integration-test.md#testfindbymodelnamedoesnotreturnsimilarnames)
3. **Data integrity / constraints** (database rules)
    - [testModelNameUniqueConstraint()](guitar-repository-integration-test.md#testmodelnameuniqueconstraint)
4. **Mapping completeness** (all columns / attributes)
    - [testFindByModelNameRetrievesAllAttributes()](guitar-repository-integration-test.md#testfindbymodelnameretrievesallattributes)
5. **Relationships** (Brand association correctness)
    - [testFindByModelNamePreservesBrandRelationship()](guitar-repository-integration-test.md#testfindbymodelnamepreservesbrandrelationship)
6. **Optional semantics / repository contract behaviour**
    - [testFindByModelNameReturnsIndependentOptionals()](guitar-repository-integration-test.md#testfindbymodelnamereturnsindependentoptionals)
7. **Edge cases in querying** (case sensitivity, special characters)
    - [testFindByModelNameCaseSensitivity()](guitar-repository-integration-test.md#testfindbymodelnamecasesensitivity)
    - [testFindByModelNameWithSpecialCharacters()](guitar-repository-integration-test.md#testfindbymodelnamewithspecialcharacters)
8. **JPA fetch strategy / lazy loading behaviour**
    - [testFindByModelNameBrandLazyLoading()](guitar-repository-integration-test.md#testfindbymodelnamebrandlazyloading)

## GuitarRepositoryTest.java (Mock-based “Unit-Style” Repository Tests)


1. **Basic query behaviour** (happy path + not found)
    - [testFindByModelNameSuccess()](guitar-repository-test.md#testfindbymodelnamesuccess)
    - [testFindByModelNameNotFound()](guitar-repository-test.md#testfindbymodelnamenotfound)
2. **Multiple calls / different inputs** (repository contract behaviour)
    - [testFindByModelNameMultipleCalls()](guitar-repository-test.md#testfindbymodelnamemultiplecalls)
3. **Entity integrity** (returned object contains expected fields)
    - [testFindByModelNameEntityIntegrity()](guitar-repository-test.md#testfindbymodelnameentityintegrity)
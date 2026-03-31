# Overview

Unit tests validate service-layer behaviour in isolation using **JUnit 5** and **Mockito**. Repository dependencies are mocked so tests run quickly and focus on business logic rather than persistence or HTTP concerns.

## Implemented Unit Test Suites
- `src/test/java/edu/tus/guitarstore/service/GuitarServiceTest.java`  
  Tests service logic by mocking repository calls and verifying returned DTOs and repository interactions.
- `src/test/java/edu/tus/guitarstore/GuitarAppTests.java`  
  Spring Boot context **smoke test** (`contextLoads()`) that verifies the application starts successfully under `@SpringBootTest` (no HTTP assertions).

---

## GuitarAppTests.java (Smoke Test)

### contextLoads()
A minimal Spring Boot smoke test that verifies the application context starts successfully with `@SpringBootTest`. It does not perform HTTP/API assertions (those are covered in the E2E test suite).

---

## GuitarServiceTest.java (Unit Tests)

1. **Fetch / Read behaviour** (happy path + not found + mapping)
    - [testFetchGuitarSuccess()](#testfetchguitarsuccess)
    - [testFetchGuitarNotFound()](#testfetchguitarnotfound)
    - [testFetchGuitarMappingAllFields()](#testfetchguitarmappingallfields)
    - [testFetchGuitarEmptyOptional()](#testfetchguitaremptyoptional)
2. **Create / Write behaviour** (core create workflow)
    - [testCreateGuitarSuccess()](#testcreateguitarsuccess)
3. **Create guards:** duplicates and missing dependencies
    - [testCreateGuitarAlreadyExists()](#testcreateguitaralreadyexists)
    - [testCreateGuitarBrandNotFound()](#testcreateguitarbrandnotfound)
    - [testCreateGuitarDuplicateCheckTiming()](#testcreateguitarduplicatechecktiming)
4. **Input validation / defensive programming** (nulls, blanks, invalid values)
    - [testCreateGuitarWithNullDto()](#testcreateguitarwithnulldto)
    - [testCreateGuitarWithNullModelName()](#testcreateguitarwithnullmodelname)
    - [testCreateGuitarWithNullBrandName()](#testcreateguitarwithnullbrandname)
    - [testCreateGuitarWithNegativePrice()](#testcreateguitarwithnegativeprice)
    - [testCreateGuitarWithZeroPrice()](#testcreateguitarwithzeroprice)
    - [testCreateGuitarWithFutureManufactureDate()](#testcreateguitarwithfuturemanufacturedate)
    - [testCreateGuitarWithEmptyModelName()](#testcreateguitarwithemptymodelname)
    - [testCreateGuitarWithWhitespaceModelName()](#testcreateguitarwithwhitespacemodelname)
    - [testCreateGuitarWithEmptyBrandName()](#testcreateguitarwithemptybrandname)
5. **Mapper / DTO** conversion edge cases
    - [testMapperBrandNullHandling()](#testmapperbrandnullhandling)

## 1. **Fetch / Read behaviour** (happy path + not found + mapping)

### testFetchGuitarSuccess()
Verifies that when the repository returns a `Guitar` entity, the service returns a populated `GuitarDto` for the requested model name. Confirms the repository method is called as expected and a normal “happy path” fetch works.

### testFetchGuitarNotFound()
Verifies that when the repository returns no match, the service throws the expected “not found” exception. Confirms the service enforces correct error behaviour for missing guitars.

### testFetchGuitarMappingAllFields()
Ensures the service returns a DTO with all relevant fields correctly mapped from the entity (i.e., mapping consistency across the main attributes). Confirms the mapper/service does not drop or mis-map data.

### testFetchGuitarEmptyOptional()
Validates behaviour when the repository returns `Optional.empty()` for a lookup. Confirms the service handles the empty Optional path correctly (error/exception path) instead of returning null or invalid DTOs.

---

## 2. **Create / Write behaviour** (core create workflow)

### testCreateGuitarSuccess()
Verifies that a valid `GuitarDto` results in a successful create operation, including mapping DTO → entity and persisting via the repository. Confirms repository `save()` is called and the service returns the expected success outcome.

---

## 3. **Create guards:** duplicates and missing dependencies

### testCreateGuitarAlreadyExists()
Verifies that attempting to create a guitar with a model name that already exists triggers the correct exception/guard behaviour. Confirms duplicates are prevented at the service layer.

### testCreateGuitarBrandNotFound()
Verifies that creating a guitar fails when the brand referenced in the DTO does not exist. Confirms the service validates foreign-key/business dependency rules before saving.

### testCreateGuitarDuplicateCheckTiming()
Verifies that the duplicate check (existence query) happens as part of the create flow and prevents saving duplicates. Confirms correct ordering of operations (check before save).

---

## 4. **Input validation / defensive programming** (nulls, blanks, invalid values)

### testCreateGuitarWithNullDto()
Verifies that passing `null` into the create method fails fast (throws) rather than producing a NullPointerException later or persisting invalid data. Confirms input validation at method boundary.

### testCreateGuitarWithNullModelName()
Verifies that a DTO with `modelName = null` is rejected. Confirms required fields are enforced consistently in the service.

### testCreateGuitarWithNullBrandName()
Verifies that a DTO with `brandName = null` is rejected. Confirms the service requires a brand association for creation.

### testCreateGuitarWithNegativePrice()
Verifies that a negative price is rejected and no repository save occurs. Confirms business validation rules around pricing.

### testCreateGuitarWithZeroPrice()
Verifies that a zero price is rejected and no repository save occurs. Confirms business rules require a positive price.

### testCreateGuitarWithFutureManufactureDate()
Verifies that a manufacture date in the future is rejected. Confirms date validation/business rules are applied before persistence.

### testCreateGuitarWithEmptyModelName()
Verifies that an empty string model name is rejected. Confirms string validation goes beyond null checks.

### testCreateGuitarWithWhitespaceModelName()
Verifies that a whitespace-only model name is rejected. Confirms trimming/blank validation is applied.

### testCreateGuitarWithEmptyBrandName()
Verifies that an empty string brand name is rejected. Confirms brand name must be meaningful/non-blank.

---

## 5. **Mapper / DTO** conversion edge cases

### testMapperBrandNullHandling()
Verifies mapping behaviour when brand information is missing/null (e.g., entity has null brand). Confirms the mapper/service handles null nested objects safely and consistently.
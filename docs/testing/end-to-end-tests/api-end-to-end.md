# API End-to-End Tests
## GuitarApiE2ETest.java

### createBrand_thenCreateGuitar_thenFetchByModelName
End-to-end “happy path” flow that creates a `Brand`, creates a `Guitar` linked to that brand, and then fetches the guitar by `modelName`. Validates the full HTTP → controller → service → repository → database → JSON response pipeline.

### creatingDuplicateGuitarModelNameShouldReturnClientError
Attempts to create a second guitar using an existing `modelName` and asserts the API returns a client error response. Confirms duplicate prevention works correctly at the API level and that the error is surfaced to the client.

### creatingGuitarWithoutManufactureDateShouldReturn400
Attempts to create a guitar with a missing required field (`manufactureDate`) and asserts a **400 Bad Request** response. Confirms request validation is enforced and invalid payloads are rejected before persistence.
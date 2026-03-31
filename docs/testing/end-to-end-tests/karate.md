# Karate Testss
## brands.feature

### Scenario: Happy path - create a brand then fetch it
Creates a new `Brand` via `POST /api/guitarstore/v1/brands` and then retrieves it via `GET /api/guitarstore/v1/brands/{name}`. Confirms the API returns the expected 201/200 statuses and that the returned JSON matches the created brand details.

### Scenario: Validation failure - missing country should return 400
Attempts to create a brand without the required `country` field and asserts a **400 Bad Request** response. Confirms request validation is enforced and the response contains a clear field-level validation message.

### Scenario: Not found - fetching unknown brand should return 404
Requests a brand name that does not exist and asserts a **404 Not Found** response. Confirms the delete endpoint works and the resource is no longer retrievable after deletion.
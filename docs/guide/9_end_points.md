
# 9. API Endpoints
  
## API-Quick-Start (Postman Ready)

The API is accessible at `http://localhost:8080/api/guitarstore/v1`. Below are the primary endpoints and sample payloads for testing.

**Resource:** Guitars


| Action    | Method | Endpoint                                          | Description                               |
| --------- | ------ | ------------------------------------------------- | ----------------------------------------- |
| Fetch All | GET    | `/guitars`                                        | Fetch all guitars                         |
| Search    | GET    | `/guitars/{modelName}`                            | Search for guitar by model name           |
| Paginated | GET    | `/guitars/paginated?page=0&size=5`                | Server-side pagination                    |
| Filter    | GET    | `/guitars/filter`                                 | Filter by date range (?start=...&end=...) |
| Filter    | GET    | `/guitars/filter?start=2020-01-01&end=2025-12-31` | Filter by manufacture date example        |
| Create    | POST   | `/guitars`                                        | Create a new guitar (include body)        |
| Update    | PUT    | `/guitars`                                        | Update a guitar (include body)            |

Sample POST Body (Create Guitar):

```
{
  "modelName": "Player Telecaster",
  "price": 569.00,
  "manufactureDate": "2022-01-15",
  "brandName": "Fender"
}
```
**Note:** The `manufactureDate` must follow the ISO format YYYY-MM-DD


**Resource:** Brands

| Action        | Method | URL                   | Description                           |
| ------------- | ------ | --------------------- | ------------------------------------- |
| Fetch All     | POST   | `/brands`             | Retrieve all brands (include body)    |
| Fetch Details | GET    | `/brands/{brandName}` | Get brand details with nested guitars |
| Fetch         | GET    | `/brands/Fender`      | Example fetching Brand details        |

Sample POST Body (Create Brand):

```
{
  "name": "Gibson",
  "country": "USA"
}
```

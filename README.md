# Guitar Store REST API
## Microservices Architecture - CA1: REST API Assignment
### Technological University of the Shannon (Athlone)

#### Branches

- **Microservices Architecture CA1:** [ma-ca1](https://github.com/joeaoregan/TUS-26-MA-CA1-Guitar-Store-API/tree/ma-ca1)

---

#### Guitar Store REST API

**Student Name:** Joe O'Regan\
**Student Number:** A00258304


<a href="https://youtu.be/VXFKyfw5zmo" target="_blank"><img src="src\main\resources\static\images\api_guitars.png" title="Guitars API Demo" width="200"/></a>

###### Guitars API Demo Video


<a href="https://youtu.be/z5adidLxAFg" target="_blank"><img src="src\main\resources\static\images\api_brands.png" title="Brands API Demo" width="200"/></a>

###### Brands API Demo Video



(Click to Expand...)

---

<details open>
  <summary>1. How to Run</summary>

1. **Clone:** `git clone https://github.com/joeaoregan/TUS-26-MA-CA1-Guitar-Store-API.git`
2. **Run:** `./mvnw spring-boot:run` (requires Maven)
3. **Home Page:** http://localhost:8080
4. **Test Page:** http://localhost:8080/hello (Test project running OK)
5. **Data Initialisation:** The database is automatically pre-populated using `data.sql` for repeatable testing.
6. **H2 Console:** http://localhost:8080/h2-console (Check in-memory database)

</details>

---

<details>
  <summary>2. Project Overview</summary>

- **Domain:** Guitar Store Inventory Management.
- **Standards:** Compliant with Atlassian RESTful Design Specification.
- **Base URL:** http://localhost:8080/api/guitarstore/v1.
- **Test URL:** http://localhost:8080/api/guitarstore/v1/guitars (display guitars in database).

</details>

---

<details>
  <summary>3. Core Technology Requirements</summary>

1. **Entity Relationships**

    One-to-many / many-to-many relationship between entities.
    Operations:

    - Retrieving all child entities for a given parent.
    - Creating, updating and deleting child entities associated with a parent. 
    - Implementing a cascading delete.

2. **Use of Date and Time Objects**

    Handling date-related data.

    - Accept and validate date inputs.
    - Using a consistent format.
    - An endpoint that filters by date range / sorts results based on date fields.

3. **Incorporating DTOs (Data Transfer Objects)**

    Use DTOs to control data exchanged between the client and server.

    - Separation of concerns by decoupling the internal data model from the API response format.
    - Clean and minimal responses that expose only the required data fields.

4. **Error Handling and Validation**

    Implement structured and meaningful error handling.

    - Validating client input.
    - Appropriate HTTP status codes and error messages.
    - Clear, consistent error responses.

    Examples:
    - Example: `@Positive` on price ensures inventory values are realistic.
    - Example: `@PastOrPresent` on manufacture dates prevents future entries.\
    Jakarta Validation is used at the DTO level.

5. **Pagination**
    The API supports pagination for endpoints returning collections.

- **Validation Error (400):** Triggered by invalid inputs like negative prices.
- **Resource Not Found (404):** Triggered when a model name does not exist in the database.
</details>

---

<details>
  <summary>4. Technology Stack</summary>

- **Java Version:** Java 17+ (Tested with).
- **Framework:** Spring Boot 3.x.\
  Framework for the RESTful API.
- **Database:** H2 In-Memory Database (with JPA/Hibernate).\
  For database persistence and relationship mapping and rapid demonstation.
- **Validation:** Jakarta Bean Validation.
- **Utilities:** Lombok and MapStruct\
  For reducing boilerplate and custom Mappers.
</details>

---

<details>
  <summary>5. API Demonstration</summary>

| Method | Endpoint              | Description                             | Status Code |
| ------ | --------------------- | --------------------------------------- | ----------- |
| GET    | /guitars              | Fetch all guitars (supports pagination) | 200 OK      |
| GET    | /guitars/model/{name} | Fetch a specific guitar                 | 200 / 404   |
| POST   | /guitars              | Create a new guitar (validated)         | 201 Created |
| PUT    | /guitars              | Update guitar details                   | 200 OK      |
| DELETE | /guitars/{name}       | Remove a guitar                         | 200 / 204   |

</details>

---

<details>
  <summary>6. Diagrams</summary>

![Class Diagram](src\main\resources\static\images\class-diagram.png)

### System Architecture & Data Flow

This class diagram illustrates the API’s adherence to the Single Responsibility Principle through a strictly layered architecture.
* **Controller Layer:** Handles RESTful request mapping and URI routing.
* **Service Layer:** Encapsulates business logic, managing the Brand and Guitar domains through specialized interfaces to ensure modularity.
* **Mapping Layer:** Utilizes BrandMapper and GuitarMapper to decouple internal JPA entities from external Data Transfer Objects (DTOs), preventing internal database details from leaking to the consumer.
* **Persistence Layer:** Leverages Spring Data JPA repositories to manage data access and maintain referential integrity with the H2 database.

![Sequence Diagram](src\main\resources\static\images\sequence-diagram.png)

### API Request-Response Lifecycle

This sequence diagram illustrates the end-to-end flow of a RESTful request within the system:

* **Request Interception:** The Controller receives the HTTP request and utilizes @PathVariable or @RequestBody to extract data.
* **Business Logic Delegation:** The request is passed to the specialized Service layer (e.g., BrandService or GuitarService), ensuring a strict separation of concerns.
* **Persistence & Mapping:** The service interacts with the JPA Repository for data access and uses Mappers to convert internal entities into DTOs, maintaining a secure API contract.
* **Global Exception Handling:** If a resource is missing or data is invalid, the service layer throws a custom exception (e.g., ResourceNotFoundException).
* **Structured Response:** The GlobalExceptionHandler intercepts the error and transforms it into a standardized ErrorResponseDto, ensuring the client receives consistent JSON feedback instead of a raw stack trace
</details>

---

<details>
  <summary>7. Database and Audit</summary>

- **Access H2 Console:** https://localhost:8080/h2-console (using the JDBC URL specified in `application.yml`)
- **Auditing:** Automated metadata tracking via `BaseEntity` and `JpaAuditAware`
- **Relationship:** Demonstrates a One-to-Many relationship between `Brands` and `Guitars` with Cascading Deletes.

![Entity Relationship Diagram](src\main\resources\static\images\erd.png)

</details>

---

<details>
  <summary>8. Status Codes implemented</summary>

| Status Code            | Description                                                      | Guitar Store API Scenario                                              |
| ---------------------- | ---------------------------------------------------------------- | ---------------------------------------------------------------------- |
| 200 OK                 | The request was successful.                                      | Successfully retrieve guitar data.                                     |
| 201 Created            | A new resouce was created.                                       | Successfully added guitar data to the database inventory.              |
| 202 Accepted           |                                                                  | Indicate the request was accepted for processing but is not completed. |
| 204 No Content         | There is no content to send for this request except for headers  | No content because it has deleted it.                                  |
| 400 Bad Request        | The server could not understand the request. Maybe a bad syntax? | |
| 404 Not Found          |                                                                  | |
| 405 Method Not Allowed |                                                                  | Won't be able to reconcile it.                                         |
| 409 Conflict           |                                                                  | In some cases it's not possible to update an existing resource.        |

</details>

---

<details open>
  <summary>9. API Endpoints</summary>
  
### API-Quick-Start (Postman Ready)

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

</details>





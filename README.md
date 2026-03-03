# Guitar Store REST API
## Microservices Architecture - CA1: REST API Assignment
### Technological University of the Shannon (Athlone)

---

#### Guitar Store REST API

**Student Name:** Joe O'Regan\
**Student Number:** A00258304

(Click to Expand...)

---
<details open>
  <summary>1. Project Overview</summary>

### Project Overview

- **Domain:** Guitar Store Inventory Management.
- **Standards:** Compliant with Atlassian RESTful Design Specification.
- **Base URL:** http://localhost:8080/api/guitarstore/v1.
</details>

---
<details>
  <summary>2. Core Technology Requirements</summary>

### Core Technology Requirements
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
  <summary>3. Technology Stack</summary>

### Technology Stack

- **Java Version (Tested with)?** Java 17+.
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
  <summary>4. API Demonstration</summary>

### API Demonstration

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
  <summary>5. How to Run</summary>

### How to Run

1. **Clone:** `git clone https://github.com/joeaoregan/TUS-26-MA-CA1-Guitar-Store-API.git`
2. **Run:** `./mvnw spring-boot:run` (requires Maven)
3. **Data Initialisation:** The database is automatically pre-populated using `data.sql` for repeatable testing.
</details>

---
<details>
  <summary>6. Database and Audit</summary>

### Database and Audit

- **Access H2 Console:** `https://localhost:8080/h2-console` (using the JDBC URL specified in `application.yml`)
- **Auditing:** Automated metadata tracking via `BaseEntity` and `JpaAuditAware`
- **Relationship:** Demonstrates a One-to-Many relationship between `Brands` and `Guitars` with Cascading Deletes.
</details>

---
<details>
  <summary>7. Status Codes implemented</summary>

### Status Codes implemented

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






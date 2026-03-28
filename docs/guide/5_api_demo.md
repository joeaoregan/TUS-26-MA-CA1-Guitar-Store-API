# 5. API Demonstration

| Method | Endpoint              | Description                             | Status Code |
| ------ | --------------------- | --------------------------------------- | ----------- |
| GET    | /guitars              | Fetch all guitars (supports pagination) | 200 OK      |
| GET    | /guitars/model/{name} | Fetch a specific guitar                 | 200 / 404   |
| POST   | /guitars              | Create a new guitar (validated)         | 201 Created |
| PUT    | /guitars              | Update guitar details                   | 200 OK      |
| DELETE | /guitars/{name}       | Remove a guitar                         | 200 / 204   |

## YouTube Demos

<a href="https://youtu.be/VXFKyfw5zmo" target="_blank">
    <img src="../../images/api_guitars.png" title="Guitars API Demo" width="200"/>
</a>

###### Guitars API Demo Video

<a href="https://youtu.be/z5adidLxAFg" target="_blank">
    <img src="../../images/api_brands.png" title="Brands API Demo" width="200"/>
</a>

###### Brands API Demo Video

## OpenAPI Documentation

[View Swagger UI API Docs](https://tus-26-ma-ca1-guitar-store-api.onrender.com/swagger-ui/index.html)
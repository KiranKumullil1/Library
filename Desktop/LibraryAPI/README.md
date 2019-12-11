# DigiPR Spring Boot API Documentation Example

This example illustrates how an API can be documented including Swagger/OpenAPI.

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Deploy to Heroku](https://img.shields.io/badge/deploy%20to-Heroku-6762a6.svg?longCache=true)](https://heroku.com/deploy)

#### Contents:
- [Analysis](#analysis)
  - [Scenario](#scenario)
  - [Use Case](#use-case)
- [Design](#design)
  - [Endpoint Prototype](#endpoint-prototype)
  - [Data Access / Persistence Layer](#data-access--persistence-layer)
  - [Business Layer](#business-layer)
  - [Service Layer / API](#service-layer--api)
- [Deployment](#deployment)

## Analysis

### Scenario

A-CRM (Agency Customer-Relationship-Management) is the smallest possible and lightweight demonstration tool that allows agents to manage their viewer data. Agents have an own access to their viewer data.

### Use Case
![](images/CRM-Use_Case.png)
- UC-1 [Login on A-CRM]: Agents can log-in by entering an email address and password. As an extension, new agents my register first.
- UC-2 [Register on A-CRM]: Agents can register to get an account (profile) to access the A-CRM system.
- UC-3 [Edit a viewer]: Agents can create, update and delete viewers.
- UC-4 [Show a viewer list]: Agents can get an overview over their viewers based on a viewer list. As an extension they can create, update and delete viewers (UC-3).

## Design

### Endpoint Prototype
**Path**: [`/api/book`](/api/viewer) 

**Method:** `POST`

**Sample Request**  • *Header:* `Content-Type: application/json` • *Body:*

```JSON
{
    "title": "string",
    "genre": "string",
    "pages": "string",
    "author": "string",
  }
```

• *Optional:* `...`
  
**Success Response**  • *Code:* `200 OK` • *Sample Body:*

```JSON
{
    "boodId": 1,
    "title": "string",
    "genre": "string",
    "pages": "string",
    "author": "string",
}
```

**Error Response** • *Code:* `404 NOT FOUND`

**Path**: [`/api/librarian`](/api/viewer) 

**Method:** `POST`

**Sample Request**  • *Header:* `Content-Type: application/json` • *Body:*

```JSON
{
    "name": "string",
    "email": "string",
    "password": "string",
    "remember": "string",
  }
```

• *Optional:* `...`
  
**Success Response**  • *Code:* `200 OK` • *Sample Body:*

```JSON
{
    "librarianId": 1,
    "name": "string",
    "email": "string",
    "password": "string",
    "remember": "string",
}
```

**Error Response** • *Code:* `406`

```JSON
{
  "status": 406,
  "error": "Not Acceptable",
  "message": "Please provide a valid e-mail.",
  "path": "/api/keeper"
}
```
**Error Response** • *Code:* `404 NOT FOUND`

### Data Access / Persistence Layer

The `rocks.process.acrm.data.domain` package contains the following domain objects / entities including getters and setters:

![](images/Library_Domain_Model.png)

This would be the ERD representation of the domain model:

![](images/ERD.png)

### Business Layer

The `rocks.process.acrm.business.service` package contains classes of the following business services:

![](images/business-service.png)


### Service Layer / API

On the service layer, the API for viewer management has been realised using the REST style as depicted in the following:

![](images/api-endpoint-vp.png)

Further can be seen using the Swagger-UI.

## Deployment

This spring boot application can be deployed to Heroku by adding the following `Procfile` to the project root:
```console
web: java -Dserver.port=$PORT $JAVA_OPTS -jar /target/*.jar
```

Finally the Swagger-UI can be access using the Heroku app specific address such as: `https://***.herokuapp.com/swagger-ui.html`

# Hotel Management System

#### Contributers:  <a href="https://github.com/dana-akesh"> Dana Akesh - 1201112 </a> &  <a href="https://github.com/BakerDwaikat"> Baker Al-Sdeeq Dwaikat - 1192772 </a>

## Table Of Contents:

- [Description](#description)
- [Core Resources](#core-resources)
- [ER Diagram](#er-diagram)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Docker Image on DockerHub](#docker-image-on-dockerhub)
- [Postman File](#postman-file)
- [Swaggerhub](#swaggerhub)
- [Learned Lessons](#learned-lessons)

## Description:

The Hotel Management System API provides endpoints for managing hotel-related operations within a business or
organization. With the use of OpenAi 3.1.0 it allows the user to add, update, and delete the core reasources which are the user, customer, employee, reservation, task, and billing.

## Features:
- Authentication and Authorization for different roles using JWT.
- Versioning the API.
- Pagination.
- Refresh token.
- Dockerize the application.


## Core Resources:

<ol>
  <li>
    <b>User: </b> 
    Resource for the general user which is the main resource for the system. 
    It only applies to the customer and admin. 
    It has the userId, username, password, tokens, and role.
  </li>
  <li>
    <b>Customer:</b>
    Resource for the customer to give him the ability to manage his account, add, update, and delete the core resources.
    It has the inherited attributes from the user, additionally to reservations, and billings.
  </li>
  <li>
    <b>Employee: </b>
    Resource for the employee to give him the ability to manage his account, add, update, and delete the core resources.
    It has the attributes employeeId, name, phone, dob, and tasks.
  </li>
  <li>
    <b>Reservation: </b>
    Resource for the reservation to give the customer/admin the ability to reserve a room.
    It has reservationId, customer, reservationRooms, date, status, and billings.
  </li>
<li>
    <b>Task: </b>
    Resource for the task to give the admin the ability to manage his tasks.
    it has taskId, taskName, taskDescription, taskStatus, and employee.
  </li>
<li>
    <b>Billing: </b>
    Resource for the billing to give the customer the ability to manage his bills.
    it has billingId, date, reservation, amount, customer, and reservation.
  </li>
</ol>

## ER Diagram:

## Prerequisites:

- IntelliJ IDEA (or any Java IDE)
- Postman (or any API testing tool)
- Maven
- Java Development Kit (JDK) 19 or higher
- MySQL Workbench 
- Docker 
- Git

## Installation:
ℹ️ note: the application should be running on Port 8080 -> http://localhost:8080.

#### Clone The Repository
```bash
git clone https://github.com/dana-akesh/Inventory_managment_system_api
cd  Inventory_managment_system_api
```

#### Create a Database
```sql
CREATE DATABASE hotelmanagementsystemdb;
```

#### Check the application.properties file
link to application.properties file: [application.properties](https://github.com/dana-akesh/hotel-management-system/blob/master/hotel-management-system/src/main/resources/application.properties)

#### Build & Run the project
```bash
mvn clean install
mvn spring-boot:run
  ```

## Docker:
#### Build the Docker Image
```bash
git clone https://github.com/dana-akesh/hotel-management-system
cd hotel-management-system
#Compiles the project, runs test cases, and packages the application as a JAR file located into the target directory.
.\mvnw package

```

## Postman File:
after running the application, you can access the API documentation through the following link:
#### API Documentation
```http 
http://localhost:8080/v3/api-docs
```
or you can download the postman file from the following link: [Postman File](


## Swaggerhub:
after running the application, you can access the API documentation through the following link:
#### Open API Specification (OAS) 3.1.0
```http 
http://localhost:8080/swagger-ui/swagger-ui/index.html#/
```

## Learned Lessons:
- Authentication and Authorization for different roles using JWT.
- The usage of OpenAi 3.1.0 to document the API from the source code.
- How to use Docker to containerize the application, and make it run on different devices.
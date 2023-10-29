# Products Microservice

This is a Spring Boot microservice for managing product prices. It provides a RESTful API to retrieve product prices
based on various parameters.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Features

- Retrieve product prices by application date, product ID, and brand ID.
- Use H2 in-memory database for data storage.
- Integration with Flyway for database migrations.
- Input parameter validation using Spring's validation framework.
- Unit tests and integration tests.
- Built with Spring Boot, Spring Data JPA, and other modern Java technologies.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 21
- Maven
- Your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse)
- Git (optional)
- Docker (optional for running H2 database in a container)

## Installation

Clone the repository and navigate to the project's root directory:

```shell
git clone https://github.com/Gescof/products-microservice.git
cd products-microservice
```

Build the project using Maven:

```shell
mvn clean install
```

## Configuration

The project uses the following dependencies:

- Spring Boot Starter Web
- Spring Boot Starter Validation
- Spring Boot Starter Data JPA
- Lombok
- Mapstruct
- Flyway Core
- H2 Database (runtime scope)
- Spring Boot Starter Test (test scope)
- The configuration for these dependencies is managed in the [pom.xml](pom.xml) file.

## Usage

To run the microservice, use the following command:

```shell
mvn spring-boot:run
```

The application will start, and you can access it at http://localhost:8080.

## Endpoints

The microservice provides the following RESTful endpoints:

- GET /api/v1/prices: Retrieve product prices based on request parameters.

    - Request Parameters:

        - applicationDate: The date of application (format: yyyy-MM-dd-HH.mm.ss).
        - productId: The product identifier.
        - brandId: The brand identifier.

## Testing

The project includes unit tests and integration tests. You can run them using the following command:

```shell
mvn test
```

### API Testing with Postman

You can use Postman to test the API endpoints. A Postman Collection that includes pre-configured requests and test
scripts is provided.

#### Instructions

1. [Download the Postman Collection](products-microservice.postman_collection.json) and save it to your local
   environment.

2. Open Postman and import the collection:

- Click "Import" in Postman.
- Choose "File" and select the downloaded collection.

3. Run the collection to execute API tests. You can use the collection runner to run the tests in batches.

4. Review the test results and make sure the API endpoints are working as expected.

In the collection there are already examples of done tests.

### Automated Testing (Optional)

Postman Monitors is also useful to automate testing. To set up automated testing, follow these steps:

1. Create a Postman Monitor in your Postman workspace.

2. Schedule the monitor to run tests at specific intervals.

3. Monitor test results and receive notifications for any failures.

Feel free to use the provided Postman Collection to test our API endpoints and ensure everything is working correctly.

## Contributing

If you'd like to contribute to this project, please fork the repository and create a pull request. You can also open
issues for bug reports or feature requests.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

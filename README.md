# Core Lending Provisioning Risk Service

## Overview
The Core Lending Provisioning Risk Service is a microservice component of the Firefly platform that manages loan provisioning and risk assessment. It provides functionality for calculating Expected Credit Loss (ECL) amounts, managing provisioning cases, and tracking risk assessments for loan portfolios, likely in compliance with IFRS 9 standards.

## Architecture

### Module Structure
The service is organized into the following modules:

1. **core-lending-provisioning-risk-interfaces**
   - Contains DTOs, enums, and interfaces that define the API contract
   - Provides the public interface for other services to interact with

2. **core-lending-provisioning-risk-models**
   - Contains database entities and repositories
   - Manages data persistence and database interactions
   - Uses R2DBC for reactive database access

3. **core-lending-provisioning-risk-core**
   - Contains business logic and service implementations
   - Implements the core functionality of the service
   - Includes mappers for converting between DTOs and entities

4. **core-lending-provisioning-risk-web**
   - Contains REST controllers and web configuration
   - Exposes the API endpoints
   - Handles HTTP requests and responses

### Technology Stack
- **Java 21** with Virtual Threads support
- **Spring Boot** framework
- **Spring WebFlux** for reactive programming
- **R2DBC** for reactive database access
- **PostgreSQL** as the database
- **Flyway** for database migrations
- **OpenAPI/Swagger** for API documentation
- **Maven** for dependency management and build
- **Docker** for containerization

## Setup and Installation

### Prerequisites
- Java 21 or later
- Maven 3.8 or later
- Docker (for containerized deployment)
- PostgreSQL database

### Environment Variables
The following environment variables need to be configured:

```
DB_HOST=localhost
DB_PORT=5432
DB_NAME=provisioning_risk
DB_USERNAME=postgres
DB_PASSWORD=postgres
DB_SSL_MODE=disable
```

### Building the Application
```bash
# Clone the repository
git clone https://github.com/firefly-oss/core-lending-provisioning-risk.git
cd core-lending-provisioning-risk

# Build with Maven
mvn clean package
```

### Running Locally
```bash
# Run with Maven
mvn spring-boot:run -pl core-lending-provisioning-risk-web

# Or run the JAR directly
java -jar core-lending-provisioning-risk-web/target/core-lending-provisioning-risk.jar
```

### Running with Docker
```bash
# Build the Docker image
docker build -t core-lending-provisioning-risk .

# Run the container
docker run -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e DB_PORT=5432 \
  -e DB_NAME=provisioning_risk \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=postgres \
  -e DB_SSL_MODE=disable \
  core-lending-provisioning-risk
```

## API Documentation

The service provides a RESTful API for managing provisioning cases, risk assessments, and related entities. The API is versioned (v1) and follows standard REST conventions.

### API Endpoints

When the application is running, the Swagger UI is available at:
```
http://localhost:8080/swagger-ui.html
```

The OpenAPI specification is available at:
```
http://localhost:8080/v3/api-docs
```

### Main Resources

#### Provisioning Cases
- `GET /api/v1/provisioning-cases` - List or search provisioning cases
- `POST /api/v1/provisioning-cases` - Create a new provisioning case
- `GET /api/v1/provisioning-cases/{id}` - Get a provisioning case by ID
- `PUT /api/v1/provisioning-cases/{id}` - Update a provisioning case
- `DELETE /api/v1/provisioning-cases/{id}` - Delete a provisioning case

#### Risk Assessments
- `GET /api/v1/risk-assessments` - List or search risk assessments
- `POST /api/v1/risk-assessments` - Create a new risk assessment
- `GET /api/v1/risk-assessments/{id}` - Get a risk assessment by ID
- `PUT /api/v1/risk-assessments/{id}` - Update a risk assessment
- `DELETE /api/v1/risk-assessments/{id}` - Delete a risk assessment

## Configuration

The application uses Spring profiles for different environments:

- **dev** - Development environment with detailed logging
- **testing** - Testing environment with API documentation enabled
- **prod** - Production environment with minimal logging and API documentation disabled

Configuration is managed through the `application.yaml` file and environment variables.

## Development Guidelines

### Code Style
- Follow standard Java coding conventions
- Use Lombok annotations to reduce boilerplate code
- Use reactive programming patterns with WebFlux

### Testing
- Write unit tests for service implementations
- Write integration tests for repositories and controllers
- Use reactive testing utilities for testing Mono and Flux return types

### Branching Strategy
- `main` branch for production releases
- `develop` branch for development work
- Feature branches for new features and bug fixes

## Deployment

The service is deployed as a Docker container to Azure Container Registry. The CI/CD pipeline is managed through GitHub Actions and includes:

1. Building the application with Maven
2. Running tests
3. Building a Docker image
4. Publishing the image to Azure Container Registry

### Deployment Environments
- **Development**: http://core.catalis.vc/loan-origination
- **Local**: http://localhost:8080

## Monitoring and Health Checks

The service exposes health and metrics endpoints for monitoring:

- `GET /actuator/health` - Health check endpoint
- `GET /actuator/info` - Application information
- `GET /actuator/prometheus` - Prometheus metrics

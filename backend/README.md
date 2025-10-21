# Backend - QA Automation Service Hub

Spring Boot WebFlux backend providing REST APIs for QA automation utilities.

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+

### Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### API Documentation
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI Spec: `http://localhost:8080/v3/api-docs`

### Health Check
```bash
curl http://localhost:8080/actuator/health
```

## 📋 Available APIs

### Test Data Generation
- `GET /api/v1/testdata/generate/{type}?count=10`
  - Generates test data of specified type (user, product, order, address, payment)
  - Returns streaming JSON response

- `GET /api/v1/testdata/templates`
  - Returns available test data templates

### Examples
```bash
# Generate 5 user records
curl "http://localhost:8080/api/v1/testdata/generate/user?count=5"

# Generate 10 product records  
curl "http://localhost:8080/api/v1/testdata/generate/product?count=10"

# Get available templates
curl "http://localhost:8080/api/v1/testdata/templates"
```

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Run Integration Tests
```bash
mvn verify
```

### Load Testing with Gatling
```bash
mvn gatling:test
```

## 📊 Monitoring

### Actuator Endpoints
- Health: `/actuator/health`
- Metrics: `/actuator/metrics`
- Info: `/actuator/info`

### Spring Boot Admin
Configure Spring Boot Admin server on port 8081 to monitor this application.

## 🔧 Configuration

Key configuration in `application.properties`:
- Server port: 8080
- CORS enabled for frontend (ports 3000, 4200)
- Actuator endpoints exposed
- Logging configuration

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/maheshgaire/qaautomation/
│   │   ├── controller/     # REST controllers
│   │   ├── service/        # Business logic
│   │   ├── model/          # Data models
│   │   └── QaAutomationServiceHubApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/maheshgaire/qaautomation/
        └── # Test classes
```

## 🚀 Next Steps

1. Add more API endpoints (Jenkins integration, report parsing)
2. Implement authentication/authorization
3. Add database persistence
4. Create Docker image
5. Add comprehensive test coverage
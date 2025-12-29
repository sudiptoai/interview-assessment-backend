# User Module with Authentication

## Overview
This project implements a complete User authentication module with JWT-based security following SOLID principles.

## Features
- User registration with role-based access (NORMAL_USER, ADMIN)
- JWT-based authentication
- Password encryption with BCrypt
- PostgreSQL database integration
- Comprehensive error handling
- Input validation

## Architecture

### SOLID Principles Applied
1. **Single Responsibility Principle (SRP)**
   - `UserService`: Handles user management operations
   - `AuthService`: Handles authentication operations
   - `JwtUtil`: Handles JWT token generation and validation

2. **Open/Closed Principle (OCP)**
   - Service interfaces allow extension without modification
   - Custom exception hierarchy allows new exception types

3. **Liskov Substitution Principle (LSP)**
   - `BaseModel` provides common functionality that all entities can use

4. **Interface Segregation Principle (ISP)**
   - Focused interfaces with specific responsibilities
   - Controllers depend on service interfaces, not implementations

5. **Dependency Inversion Principle (DIP)**
   - High-level modules depend on abstractions (interfaces)
   - Low-level modules implement these interfaces

### Project Structure
```
src/main/java/com/code_editor/interview_assesement/
├── config/              # Configuration classes
│   ├── PasswordEncoderConfig.java
│   └── SecurityConfig.java
├── controllers/         # REST API endpoints
│   ├── AuthController.java
│   └── UserController.java
├── dto/                # Data Transfer Objects
│   ├── AuthResponseDTO.java
│   ├── UserLoginDTO.java
│   ├── UserRegistrationDTO.java
│   └── UserResponseDTO.java
├── exceptions/         # Custom exceptions and error handling
│   ├── ErrorResponse.java
│   ├── GlobalExceptionHandler.java
│   ├── InvalidCredentialsException.java
│   ├── UserAlreadyExistsException.java
│   └── UserNotFoundException.java
├── models/            # JPA entities
│   ├── BaseModel.java
│   ├── User.java
│   └── UserRole.java
├── repositories/      # Data access layer
│   └── UserRepository.java
├── security/         # Security components
│   └── JwtAuthenticationFilter.java
├── services/         # Business logic
│   ├── AuthService.java
│   ├── AuthServiceImpl.java
│   ├── UserService.java
│   └── UserServiceImpl.java
└── util/            # Utility classes
    └── JwtUtil.java
```

## API Endpoints

### User Registration
```bash
POST /api/users/register
Content-Type: application/json

{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123",
  "role": "NORMAL_USER"  # or "ADMIN"
}

Response (201 Created):
{
  "id": 1,
  "username": "testuser",
  "email": "test@example.com",
  "role": "NORMAL_USER",
  "enabled": true,
  "createdAt": "2025-12-29T08:12:07.802Z",
  "updatedAt": "2025-12-29T08:12:07.802Z"
}
```

### User Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "emailOrUsername": "test@example.com",  # Can use email or username
  "password": "password123"
}

Response (200 OK):
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "user": {
    "id": 1,
    "username": "testuser",
    "email": "test@example.com",
    "role": "NORMAL_USER",
    "enabled": true,
    "createdAt": "2025-12-29T08:12:07.802Z",
    "updatedAt": "2025-12-29T08:12:07.802Z"
  }
}
```

### User Signin (Alias for Login)
```bash
POST /api/auth/signin
# Same request/response as login
```

## Error Responses

### Validation Errors (400 Bad Request)
```json
{
  "timestamp": "2025-12-29T08:12:56.552932081",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input parameters",
  "validationErrors": {
    "password": "Password must be at least 6 characters",
    "email": "Email should be valid",
    "username": "Username must be between 3 and 50 characters"
  }
}
```

### User Already Exists (409 Conflict)
```json
{
  "timestamp": "2025-12-29T08:12:41.047127475",
  "status": 409,
  "error": "Conflict",
  "message": "Email already registered: admin@example.com"
}
```

### Invalid Credentials (401 Unauthorized)
```json
{
  "timestamp": "2025-12-29T08:12:48.743765816",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email/username or password"
}
```

## Setup and Running

### Prerequisites
- Java 17
- Maven 3.9+
- Docker (for PostgreSQL)

### Database Setup
```bash
# Start PostgreSQL using docker-compose
docker compose up -d postgres
```

### Build and Run
```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### Running Tests
```bash
./mvnw test
```

## Security Considerations

### Production Deployment
1. **JWT Secret**: Change the JWT secret and store it in environment variables
   ```
   export JWT_SECRET=your-secure-random-secret-key
   ```

2. **Database Credentials**: Externalize database credentials
   ```
   export DB_URL=jdbc:postgresql://your-db-host:5432/dbname
   export DB_USERNAME=your-username
   export DB_PASSWORD=your-password
   ```

3. **HTTPS**: Always use HTTPS in production

4. **Rate Limiting**: Implement rate limiting for authentication endpoints

5. **Token Expiration**: Adjust JWT expiration time based on security requirements

### JWT Token Usage
Include the JWT token in the Authorization header for protected endpoints:
```bash
curl -H "Authorization: Bearer <your-jwt-token>" http://localhost:8080/api/protected-endpoint
```

## Technology Stack
- **Spring Boot 4.0.1**: Application framework
- **Spring Security**: Security and authentication
- **Spring Data JPA**: Database access
- **PostgreSQL**: Production database
- **H2**: In-memory database for testing
- **JWT (JJWT 0.12.5)**: JSON Web Token implementation
- **Lombok**: Reduce boilerplate code
- **Jakarta Validation**: Input validation

## Design Patterns Used
1. **Repository Pattern**: Data access abstraction
2. **Service Layer Pattern**: Business logic separation
3. **DTO Pattern**: Data transfer between layers
4. **Builder Pattern**: Object construction (Lombok @Builder)
5. **Strategy Pattern**: Different authentication strategies
6. **Filter Pattern**: JWT authentication filter

## Testing
- Unit tests with JUnit 5
- Integration tests with Spring Boot Test
- H2 in-memory database for testing
- Mocked dependencies for service layer tests

## Future Enhancements
- Refresh token mechanism
- Email verification for registration
- Password reset functionality
- User profile management endpoints
- OAuth2 integration
- Two-factor authentication
- User activity logging

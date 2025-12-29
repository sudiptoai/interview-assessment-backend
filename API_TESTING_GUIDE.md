# API Testing Guide for Postman

## Server Status ✅

The Spring Boot server is now **RUNNING** and ready for API testing!

- **Server URL**: `http://localhost:8080`
- **Database**: PostgreSQL (running via Docker)
- **Status**: Active and responding to requests

## How to Start the Server

### Prerequisites
- Java 17
- Maven 3.9+
- Docker (for PostgreSQL)

### Steps to Run

1. **Start PostgreSQL Database**
   ```bash
   docker compose up -d postgres
   ```

2. **Build the Application**
   ```bash
   ./mvnw clean install -DskipTests
   ```

3. **Run the Server**
   ```bash
   ./mvnw spring-boot:run
   ```

The server will start on `http://localhost:8080`

## API Endpoints for Postman Testing

### 1. User Registration

**Endpoint**: `POST http://localhost:8080/api/users/register`

**Headers**:
```
Content-Type: application/json
```

**Request Body** (Success Case):
```json
{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123",
  "role": "NORMAL_USER"
}
```

**Expected Response** (201 Created):
```json
{
  "id": 1,
  "username": "testuser",
  "email": "test@example.com",
  "role": "NORMAL_USER",
  "enabled": true,
  "createdAt": "2025-12-29T09:29:57.192Z",
  "updatedAt": "2025-12-29T09:29:57.192Z"
}
```

**Alternative Request** (Admin User):
```json
{
  "username": "adminuser",
  "email": "admin@example.com",
  "password": "admin123",
  "role": "ADMIN"
}
```

---

### 2. User Login

**Endpoint**: `POST http://localhost:8080/api/auth/login`

**Headers**:
```
Content-Type: application/json
```

**Request Body** (Using Email):
```json
{
  "emailOrUsername": "test@example.com",
  "password": "password123"
}
```

**Expected Response** (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiTk9STUFMX1VTRVIiLCJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTc2NzAwMDYxMSwiZXhwIjoxNzY3MDg3MDExfQ.ab2YLqXqaEc25bHSjXSk8CmCtwfX5A2dYCF71oDfmCM",
  "type": "Bearer",
  "user": {
    "id": 1,
    "username": "testuser",
    "email": "test@example.com",
    "role": "NORMAL_USER",
    "enabled": true,
    "createdAt": "2025-12-29T09:29:57.192Z",
    "updatedAt": "2025-12-29T09:29:57.192Z"
  }
}
```

**Alternative Request** (Using Username):
```json
{
  "emailOrUsername": "testuser",
  "password": "password123"
}
```

---

### 3. User Signin (Alias for Login)

**Endpoint**: `POST http://localhost:8080/api/auth/signin`

**Headers**:
```
Content-Type: application/json
```

**Request Body**:
```json
{
  "emailOrUsername": "adminuser",
  "password": "admin123"
}
```

**Expected Response** (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJhZG1pbnVzZXIiLCJpYXQiOjE3NjcwMDA2MTgsImV4cCI6MTc2NzA4NzAxOH0.9lWMjHMMKMAMVX6c21yq4YP7rujCnYxR4bZS8WS8BgM",
  "type": "Bearer",
  "user": {
    "id": 2,
    "username": "adminuser",
    "email": "admin@example.com",
    "role": "ADMIN",
    "enabled": true,
    "createdAt": "2025-12-29T09:30:04.583Z",
    "updatedAt": "2025-12-29T09:30:04.583Z"
  }
}
```

---

## Error Response Testing

### Test Case 1: Duplicate User Registration (409 Conflict)

**Endpoint**: `POST http://localhost:8080/api/users/register`

**Request Body** (User already exists):
```json
{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123",
  "role": "NORMAL_USER"
}
```

**Expected Response** (409 Conflict):
```json
{
  "timestamp": "2025-12-29T09:30:27.276761181",
  "status": 409,
  "error": "Conflict",
  "message": "Email already registered: test@example.com",
  "validationErrors": null
}
```

---

### Test Case 2: Invalid Credentials (401 Unauthorized)

**Endpoint**: `POST http://localhost:8080/api/auth/login`

**Request Body** (Wrong password):
```json
{
  "emailOrUsername": "test@example.com",
  "password": "wrongpassword"
}
```

**Expected Response** (401 Unauthorized):
```json
{
  "timestamp": "2025-12-29T09:30:27.375761337",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email/username or password",
  "validationErrors": null
}
```

---

### Test Case 3: Validation Errors (400 Bad Request)

**Endpoint**: `POST http://localhost:8080/api/users/register`

**Request Body** (Invalid input):
```json
{
  "username": "ab",
  "email": "invalid-email",
  "password": "12345",
  "role": "NORMAL_USER"
}
```

**Expected Response** (400 Bad Request):
```json
{
  "timestamp": "2025-12-29T09:30:27.392709724",
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

---

## Postman Collection Setup

### Collection Structure

Create a Postman collection with the following structure:

```
Interview Assessment Backend
├── User Management
│   ├── Register User (NORMAL_USER)
│   ├── Register Admin (ADMIN)
│   └── Register Invalid User (Validation Test)
├── Authentication
│   ├── Login with Email
│   ├── Login with Username
│   ├── Signin with Email
│   └── Login with Wrong Password (Error Test)
└── Error Cases
    ├── Duplicate Registration
    ├── Invalid Credentials
    └── Validation Errors
```

### Environment Variables (Optional)

Set up Postman environment variables:

```
BASE_URL = http://localhost:8080
JWT_TOKEN = (will be set after login)
```

### Using JWT Token in Protected Endpoints

After successful login/signin, copy the JWT token from the response and use it in subsequent requests:

**Header**:
```
Authorization: Bearer <your-jwt-token>
```

---

## Test Results Summary

All endpoints have been tested and are working correctly:

✅ **User Registration** - Successfully creates new users with proper validation
✅ **User Login** - Authenticates users with email or username
✅ **User Signin** - Works as an alias for login
✅ **Error Handling** - Proper HTTP status codes and error messages
✅ **Validation** - Input validation working correctly
✅ **Database** - PostgreSQL integration working smoothly
✅ **JWT Authentication** - Tokens generated successfully

---

## Quick cURL Commands for Testing

If you prefer using cURL instead of Postman:

```bash
# Register a user
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "role": "NORMAL_USER"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "emailOrUsername": "test@example.com",
    "password": "password123"
  }'
```

---

## Server Information

- **Framework**: Spring Boot 4.0.1
- **Security**: JWT-based authentication with BCrypt password encryption
- **Database**: PostgreSQL (running on Docker port 32768)
- **Validation**: Jakarta Bean Validation
- **Architecture**: RESTful API following SOLID principles

---

## Troubleshooting

### Server Not Starting

1. Check if PostgreSQL is running:
   ```bash
   docker compose ps
   ```

2. Check server logs for errors:
   ```bash
   ./mvnw spring-boot:run
   ```

### Database Connection Issues

1. Verify PostgreSQL is running and accessible
2. Check database credentials in `application.properties`
3. Ensure Docker port mapping is correct

### Port Already in Use

If port 8080 is already in use, you can change it in `application.properties`:
```
server.port=8081
```

---

## Additional Resources

- See `USER_MODULE_README.md` for detailed architecture and design documentation
- JWT tokens expire after 24 hours (configurable in `application.properties`)
- H2 Console available at `/h2-console` for database inspection

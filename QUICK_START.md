# Quick Start Guide - Running Server for API Testing

## 🚀 Server is RUNNING!

The Spring Boot server is currently running and ready for manual API testing in Postman.

### Server Details
- **URL**: `http://localhost:8080`
- **Status**: ✅ Active
- **Database**: PostgreSQL (via Docker)
- **Authentication**: JWT-based

## 📝 Quick Commands

### Start Everything (if not running)
```bash
# 1. Start PostgreSQL
docker compose up -d postgres

# 2. Run the server
./mvnw spring-boot:run
```

### Stop Everything
```bash
# Stop server: Ctrl+C in the terminal where server is running

# Stop PostgreSQL
docker compose down
```

## 🧪 Quick API Test

### Register a User
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "role": "NORMAL_USER"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "emailOrUsername": "test@example.com",
    "password": "password123"
  }'
```

## 📚 Complete Documentation

For comprehensive API documentation, Postman setup, and all test cases, see:

**👉 [API_TESTING_GUIDE.md](./API_TESTING_GUIDE.md)**

## 🔍 Available Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/auth/login` | Login with email or username |
| POST | `/api/auth/signin` | Signin (alias for login) |

## ✅ Verified Test Results

All endpoints have been tested and verified:
- ✅ User registration with validation
- ✅ User login with email/username
- ✅ JWT token generation
- ✅ Error handling (409, 401, 400)
- ✅ Database integration

## 💡 Tips for Postman Testing

1. Import the endpoints from `API_TESTING_GUIDE.md`
2. Set base URL as environment variable: `http://localhost:8080`
3. After login, save the JWT token for protected endpoints
4. Use the token in headers: `Authorization: Bearer <token>`

---

**For detailed architecture and implementation details**, see: [USER_MODULE_README.md](./USER_MODULE_README.md)

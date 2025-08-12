# Postman Collection for User Management API

This Postman collection provides comprehensive testing capabilities for user authentication and creating users with detailed personal information.

## Files Included

1. **postman-collection.json** - Main collection with all API requests
2. **postman-environment.json** - Environment variables for the collection
3. **POSTMAN_README.md** - This documentation file

## Collection Structure

### 1. Authentication
- **Login** - Authenticate user and retrieve access token
- **Refresh Token** - Refresh expired access token

### 2. User Management
- **Create User with Personal Info** - Create comprehensive user profiles
- **Get User Details** - Retrieve user information
- **Update User Personal Info** - Modify existing user data

### 3. Bulk Operations
- **Create Multiple Users** - Batch user creation

## Features

### Automated Token Management
- Automatically stores auth tokens after successful login
- Uses tokens for subsequent authenticated requests
- Validates token presence before making requests

### Comprehensive User Data
The collection creates users with detailed information including:
- **Personal Information**: Name, date of birth, gender, nationality, marital status
- **Contact Information**: Multiple emails, phones, complete address
- **Professional Information**: Job details, company info, salary, manager
- **Identification**: SSN, driver's license, passport details
- **Preferences**: Language, timezone, notification settings, privacy preferences
- **Emergency Contact**: Complete emergency contact information
- **Metadata**: Registration source, referral codes, terms agreement

### Dynamic Data Generation
- Uses Postman's faker library for random data generation
- Generates unique emails, phone numbers, and IDs for testing
- Prevents duplicate data conflicts during testing

### Comprehensive Testing
Each request includes:
- Status code validation
- Response structure verification
- Response time performance checks
- Data integrity validation
- Error handling tests

## Setup Instructions

### 1. Import Collection
1. Open Postman
2. Click "Import" button
3. Select `postman-collection.json`
4. Collection will appear in your workspace

### 2. Import Environment
1. Click the gear icon (Manage Environments)
2. Click "Import"
3. Select `postman-environment.json`
4. Select the environment from the dropdown

### 3. Configure Environment Variables
Update these variables in your environment:
- `base_url`: Your API base URL (e.g., https://api.yourapp.com)
- `username`: Your login username/email
- `password`: Your login password

## Usage Instructions

### Basic Workflow
1. **First, run "Login"** to authenticate and get access token
2. **Run "Create User with Personal Info"** to create a new user
3. **Use "Get User Details"** to verify user creation
4. **Use "Update User Personal Info"** to modify user data

### Authentication Flow
```
Login → Store Token → Make Authenticated Requests
```

### Error Handling
- If authentication fails, check credentials in environment
- If token expires, run "Refresh Token" or "Login" again
- Check console logs for detailed error information

## Request Examples

### Login Request
```json
{
  "username": "admin@example.com",
  "password": "AdminPassword123!"
}
```

### Create User Request (Sample)
```json
{
  "username": "john.doe.123",
  "email": "john.doe@example.com",
  "password": "SecurePassword123!",
  "personalInfo": {
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "1990-01-15",
    "gender": "Male",
    "nationality": "American"
  },
  "contactInfo": {
    "primaryPhone": "+1-555-0123",
    "address": {
      "street": "123 Main Street",
      "city": "New York",
      "state": "NY",
      "zipCode": "10001",
      "country": "United States"
    }
  }
  // ... additional fields
}
```

## Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `base_url` | API base URL | https://api.example.com |
| `username` | Login username | admin@example.com |
| `password` | Login password | AdminPassword123! |
| `auth_token` | JWT token (auto-populated) | eyJhbGciOiJIUzI1NiIs... |
| `user_id` | Created user ID (auto-populated) | 12345 |

## Testing Features

### Pre-request Scripts
- Token validation
- Random data generation
- Request logging

### Test Scripts
- Response validation
- Performance testing
- Data extraction and storage
- Error handling verification

### Console Logging
- Request/response tracking
- Token management logs
- Error debugging information

## Security Considerations

1. **Sensitive Data**: Password is marked as "secret" type in environment
2. **Token Storage**: Tokens are stored temporarily in collection variables
3. **Data Privacy**: Personal information includes realistic but fake data
4. **Authentication**: All user operations require valid authentication

## Customization

### Adding New Requests
1. Right-click on appropriate folder
2. Add new request
3. Configure headers with `Authorization: Bearer {{auth_token}}`
4. Add appropriate tests and pre-request scripts

### Modifying User Data
Edit the request body in "Create User with Personal Info" to match your API schema.

### Environment Setup
Create different environments for:
- Development
- Staging
- Production

## Troubleshooting

### Common Issues
1. **401 Unauthorized**: Run login request first
2. **Token Expired**: Refresh token or login again
3. **Validation Errors**: Check request payload format
4. **Network Issues**: Verify base_url in environment

### Debug Steps
1. Check Postman console for logs
2. Verify environment variables are set
3. Test authentication endpoint separately
4. Validate request payload against API documentation

## API Endpoints Covered

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/login` | User authentication |
| POST | `/auth/refresh` | Token refresh |
| POST | `/users` | Create single user |
| GET | `/users/{id}` | Get user details |
| PUT | `/users/{id}` | Update user |
| POST | `/users/bulk` | Create multiple users |

This collection provides a complete testing framework for user management operations with comprehensive personal information handling.

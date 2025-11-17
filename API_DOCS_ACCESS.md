# API Documentation Access Guide

## Overview
Your E-Commerce Backend API now has comprehensive documentation available through multiple formats.

## 📖 Documentation Access Methods

### 1. **Interactive Swagger UI** (Recommended)
Once your application is running, access the interactive API documentation at:
```
http://localhost:8080/swagger-ui.html
```

**Features:**
- Test API endpoints directly from the browser
- View request/response schemas
- See parameter validation rules
- Try out endpoints with custom data

### 2. **OpenAPI JSON Specification**
Access the raw OpenAPI specification at:
```
http://localhost:8080/v3/api-docs
```

**Use cases:**
- Import into API clients (Postman, Insomnia, etc.)
- Use with API documentation generators
- Integrate with API gateways

### 3. **HTML Documentation**
Static HTML documentation available at:
```
http://localhost:8080/api-documentation.html
```

**Features:**
- Professional formatted API reference
- Copy-paste ready code examples
- Searchable content
- Works offline after loading

### 4. **Markdown Documentation**
Full documentation in markdown format:
```
API_DOCUMENTATION.md
```

**Location:** Root directory of the project  
**Use cases:**
- Include in GitHub repositories
- Render in documentation platforms
- Version control friendly

---

## 🚀 Quick Start

### Prerequisites
- Java 21 or higher
- Maven 3.6+
- (Optional) Redis for caching features

### Running the Application

```bash
# Navigate to project directory
cd ecom

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### First API Call

**Get all products:**
```bash
curl http://localhost:8080/api/products
```

**Get specific product:**
```bash
curl http://localhost:8080/api/product/1
```

**Search products:**
```bash
curl "http://localhost:8080/api/products/search?keyword=Nike"
```

---

## 📋 API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products (paginated) |
| GET | `/api/product/{id}` | Get product by ID |
| GET | `/api/product/{id}/image` | Get product image |
| GET | `/api/products/search` | Search products by keyword |
| POST | `/api/product` | Create new product (with image) |

---

## 🔧 Configuration

### Application Properties
Edit `src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Redis Configuration
spring.redis.host=localhost
spring.redis.port=6379

# Database (H2 default)
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

### Swagger Configuration
Customize API documentation in `OpenAPIConfig.java`:
- API title and version
- Contact information
- License details
- External documentation URLs

---

## 📦 Dependencies Added for Documentation

The following dependencies were added to `pom.xml`:

1. **springdoc-openapi-starter-webmvc-ui** (v2.1.0)
   - Provides Swagger UI integration
   - Auto-generates OpenAPI specification from code annotations

2. **swagger-annotations** (v2.2.20)
   - Provides @Operation, @ApiResponse, @Parameter annotations
   - Enhances code-based documentation

---

## 📝 Annotation Guide

The API uses OpenAPI 3.0 annotations for documentation:

```java
@Operation(
    summary = "Operation summary",
    description = "Detailed description"
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "404", description = "Not found")
})
@Parameter(description = "Parameter description", example = "example")
@Tag(name = "Tag Name", description = "Tag description")
```

---

## 🔍 Testing the API

### Using cURL
```bash
# Create product
curl -X POST "http://localhost:8080/api/product" \
  -F "product={\"name\":\"Product\",\"price\":99.99};type=application/json" \
  -F "imageFile=@image.jpg"

# Get products with filter
curl -X GET "http://localhost:8080/api/products?pageNum=1&pageSize=10" \
  -H "Content-Type: application/json"
```

### Using Postman
1. Open Postman
2. Import from URL: `http://localhost:8080/v3/api-docs`
3. Postman will auto-generate collection
4. Test endpoints directly

### Using Swagger UI
1. Navigate to `http://localhost:8080/swagger-ui.html`
2. Click on any endpoint
3. Click "Try it out"
4. Fill in parameters
5. Click "Execute"

---

## 🐛 Troubleshooting

### Swagger UI not loading
- **Problem:** 404 error when accessing `/swagger-ui.html`
- **Solution:** Ensure springdoc-openapi dependency is in pom.xml and application is running

### OpenAPI docs not generating
- **Problem:** `/v3/api-docs` returns empty
- **Solution:** Check that annotations are correctly applied to controllers

### Redis errors
- **Problem:** Redis connection errors in logs
- **Solution:** Either start Redis server or disable Redis configuration

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [OpenAPI Specification](https://swagger.io/specification/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

---

## 🤝 Contributing

For improvements or issues:
1. Update API annotations in controllers
2. Modify `OpenAPIConfig.java` for custom configurations
3. Regenerate documentation automatically from annotations

---

## 📞 Support

**Documentation Generated:** November 16, 2025  
**Version:** 1.0.0  
**Java Version:** 21

For questions or support, refer to the interactive Swagger UI documentation or check the markdown documentation file.

---

**Next Steps:**
1. ✅ Run the application: `mvn spring-boot:run`
2. ✅ Visit: `http://localhost:8080/swagger-ui.html`
3. ✅ Start testing endpoints!

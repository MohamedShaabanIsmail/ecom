# E-Commerce Backend API Documentation

## Base URL
```
http://localhost:8080/api
```

## Overview
This API provides endpoints for managing products in an e-commerce application. It supports CRUD operations, product search, filtering, and image management.

---

## Endpoints

### 1. Get All Products
**Retrieve a paginated list of products with optional filtering**

- **Method:** `GET`
- **Endpoint:** `/api/products`
- **Query Parameters:**
  - `pageNum` (integer, optional, default: 1) - Page number for pagination
  - `pageSize` (integer, optional, default: 5) - Number of products per page
  - `filterRequest` (object, optional) - Filter criteria in request body

- **Request Body (Optional):**
```json
{
  "brand": "Nike",
  "minPrice": 50.00,
  "maxPrice": 200.00,
  "category": "Electronics"
}
```

- **Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "Product Name",
    "description": "Product description",
    "brand": "Brand Name",
    "price": 99.99,
    "category": "Category",
    "releaseDate": "2025-11-16T00:00:00.000+00:00",
    "productAvailable": true,
    "stockQuantity": 50,
    "imageName": "image.jpg",
    "imageType": "image/jpeg",
    "imageData": null
  }
]
```

- **Example Request:**
```bash
curl -X GET "http://localhost:8080/api/products?pageNum=1&pageSize=5" \
  -H "Content-Type: application/json"
```

---

### 2. Get Product by ID
**Retrieve a specific product by its ID**

- **Method:** `GET`
- **Endpoint:** `/api/product/{id}`
- **Path Parameters:**
  - `id` (integer, required) - Product ID

- **Response:** `200 OK`
```json
{
  "id": 1,
  "name": "Product Name",
  "description": "Product description",
  "brand": "Brand Name",
  "price": 99.99,
  "category": "Category",
  "releaseDate": "2025-11-16T00:00:00.000+00:00",
  "productAvailable": true,
  "stockQuantity": 50,
  "imageName": "image.jpg",
  "imageType": "image/jpeg",
  "imageData": null
}
```

- **Response:** `404 Not Found` - If product doesn't exist

- **Example Request:**
```bash
curl -X GET "http://localhost:8080/api/product/1"
```

---

### 3. Create New Product
**Add a new product with image upload**

- **Method:** `POST`
- **Endpoint:** `/api/product`
- **Content-Type:** `multipart/form-data`

- **Request Parameters:**
  - `product` (form-data, required) - Product object as JSON
  - `imageFile` (file, required) - Image file (JPEG, PNG, etc.)

- **Product Object Structure:**
```json
{
  "name": "New Product",
  "description": "Product description",
  "brand": "Brand Name",
  "price": 99.99,
  "category": "Electronics",
  "releaseDate": "2025-11-16",
  "productAvailable": true,
  "stockQuantity": 100
}
```

- **Response:** `201 Created`
```json
{
  "id": 2,
  "name": "New Product",
  "description": "Product description",
  "brand": "Brand Name",
  "price": 99.99,
  "category": "Electronics",
  "releaseDate": "2025-11-16T00:00:00.000+00:00",
  "productAvailable": true,
  "stockQuantity": 100,
  "imageName": "image.jpg",
  "imageType": "image/jpeg",
  "imageData": [...]
}
```

- **Response:** `500 Internal Server Error` - If file upload fails

- **Example Request:**
```bash
curl -X POST "http://localhost:8080/api/product" \
  -F "product={\"name\":\"New Product\",\"description\":\"Test\",\"brand\":\"TestBrand\",\"price\":99.99,\"category\":\"Electronics\",\"releaseDate\":\"2025-11-16\",\"productAvailable\":true,\"stockQuantity\":100};type=application/json" \
  -F "imageFile=@/path/to/image.jpg"
```

---

### 4. Get Product Image
**Retrieve the image for a specific product**

- **Method:** `GET`
- **Endpoint:** `/api/product/{id}/image`
- **Path Parameters:**
  - `id` (integer, required) - Product ID

- **Response:** `200 OK` - Binary image data
- **Response Headers:**
  - `Content-Type`: Determined by product's image type (e.g., `image/jpeg`, `image/png`)

- **Example Request:**
```bash
curl -X GET "http://localhost:8080/api/product/1/image" \
  -o product_image.jpg
```

---

### 5. Search Products
**Search products by keyword**

- **Method:** `GET`
- **Endpoint:** `/api/products/search`
- **Query Parameters:**
  - `keyword` (string, required) - Search keyword (searches product name, description, brand)

- **Response:** `302 Found`
```json
[
  {
    "id": 1,
    "name": "Nike Air Max",
    "description": "High-performance running shoe",
    "brand": "Nike",
    "price": 149.99,
    "category": "Footwear",
    "releaseDate": "2025-11-16T00:00:00.000+00:00",
    "productAvailable": true,
    "stockQuantity": 50,
    "imageName": "nike_air_max.jpg",
    "imageType": "image/jpeg",
    "imageData": null
  }
]
```

- **Example Request:**
```bash
curl -X GET "http://localhost:8080/api/products/search?keyword=Nike"
```

---

## Data Models

### Product
| Field | Type | Description |
|-------|------|-------------|
| id | Integer | Unique product identifier (auto-generated) |
| name | String | Product name |
| description | String | Product description |
| brand | String | Product brand |
| price | BigDecimal | Product price |
| category | String | Product category |
| releaseDate | Date | Product release date |
| productAvailable | Boolean | Whether product is available |
| stockQuantity | Integer | Current stock quantity |
| imageName | String | Image file name |
| imageType | String | MIME type of image (e.g., image/jpeg) |
| imageData | Byte[] | Image binary data |

### FilterRequest
| Field | Type | Description |
|-------|------|-------------|
| brand | String | Filter by brand (optional) |
| minPrice | BigDecimal | Minimum price filter (optional) |
| maxPrice | BigDecimal | Maximum price filter (optional) |
| category | String | Filter by category (optional) |

---

## HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK - Request successful |
| 201 | Created - Resource successfully created |
| 302 | Found - Redirect/Search found results |
| 404 | Not Found - Product not found |
| 500 | Internal Server Error - Server error occurred |

---

## Error Handling

### Error Response Format
```json
{
  "error": "Error message describing what went wrong"
}
```

### Common Errors

**Product Not Found**
```
Status: 404
```

**Image Upload Failed**
```
Status: 500
Response: "Error message from exception"
```

---

## Features

- ✅ **Pagination Support** - Get products in pages
- ✅ **Advanced Filtering** - Filter by brand, price range, category
- ✅ **Image Upload** - Store product images as binary data
- ✅ **Image Retrieval** - Get product images with correct MIME types
- ✅ **Search Functionality** - Search products by keyword
- ✅ **CORS Enabled** - Cross-origin requests supported
- ✅ **Redis Caching** - Configured for performance optimization

---

## CORS Configuration

Cross-Origin Resource Sharing (CORS) is enabled for all origins. This allows frontend applications running on different domains to access these APIs.

---

## Rate Limiting

Currently, no rate limiting is implemented. Consider adding rate limiting for production environments.

---

## Authentication & Authorization

Currently, no authentication or authorization is implemented. Consider adding JWT or OAuth2 for production environments.

---

## Caching

Redis caching is configured but specific caching strategies need to be implemented in the service layer for optimal performance.

---

## Contact & Support

For issues or questions about this API, please contact the development team.

---

**Last Updated:** November 16, 2025  
**Version:** 1.0.0

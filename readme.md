# Cats API

A Spring Boot application for managing cat images, integrated with The Cat API.

## Features

- **Upload Cat Images**: Upload and save cat images to the database.
- **Fetch All Cat Images**: Retrieve a list of all stored cat images.
- **Delete Cat Images**: Delete a specific cat image by its ID.
- **Populate Cat Images**: Fetch and store cat images from The Cat API.

## Technologies Used

- **Spring Boot**: Backend framework.
- **MySQL**: Database for storing cat images.
- **Swagger UI**: API documentation and testing.
- **Lombok**: Simplified getter, setter, and constructor generation.
- **The Cat API**: External API for fetching cat images.

## Setup Instructions

### Prerequisites

1. Java 17 or higher installed.
2. MySQL database running.
3. Maven installed.

### Configuration

1. Clone the repository:
   ```
   git clone <repository-url>
   cd <repository-folder>
Update the database settings in application.properties:

 ```
spring.datasource.url=jdbc:mysql://localhost:3306/cats_api?useSSL=false&serverTimezone=UTC
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
Add your The Cat API key in application.properties:
catapi.key=live_a7mSrA7UWjYuK4EsdylxyoUGZsYJQ9hpnYFJJ5IxiEbtSDZY8qRtTcsArYxIj6yj
Run the application:

mvn spring-boot:run
Database Initialization
The application will create and update the cat_images table automatically. Ensure MySQL is running and accessible.
```

## API Endpoints
#### 1. Upload Cat Image
POST /api/cat-images
Description: Uploads a cat image.
Request:
``` 
curl --location 'localhost:8080/api/cat-images' \
--form 'file=@"/path/to/your/image.jpg"'
```
Response: Returns the uploaded image details.

#### 2. Get All Cat Images
GET /api/cat-images

Description: Retrieves all stored cat images.
Request:
``` 
curl --location 'localhost:8080/api/cat-images'
Response: Returns a list of cat images.
```

#### 3. Delete Cat Image

DELETE /api/cat-images/{id}

Description: Deletes a specific cat image by ID.
Request:
``` 
curl --location --request DELETE 'http://localhost:8080/api/cat-images/{id}'
Response: Confirms deletion.
```

####  4. Populate Cat Images
POST /api/cat-images/populate

Description: Populates the database with cat images from The Cat API.
Request:
``` 
curl --location --request POST 'localhost:8080/api/cat-images/populate'
Response: Returns the list of fetched images.

```

```
Project Structure
src/
├── main/
│   ├── java/com/example/catsapi/
│   │   ├── controller/  # REST controllers
│   │   ├── entity/      # Entity classes
│   │   ├── response/    # Response wrapper
│   │   ├── service/     # Business logic
│   ├── resources/
│       ├── application.properties  # Configuration
│       ├── schema.sql              # Database schema (if needed)
```

#### License

This project is licensed under the MIT License.


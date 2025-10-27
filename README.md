# 📚 Book Catalogue API

A robust RESTful API developed as part of a **PayU technical assessment**.  
This service allows book collectors to **catalogue, add, update, and remove books**.

---

## 🛠 Technology Stack

This project adheres to the specified technical requirements:

| Component         | Version / Detail | Purpose                                    |
|-------------------|------------------|--------------------------------------------|
| Backend Framework | Spring Boot 2.x  | Rapid application development              |
| Language          | Java 1.8         | Core business logic                        |
| Build Tool        | Maven            | Project lifecycle management               |
| Database (Test)   | H2 In-Memory     | Lightweight, zero-setup DB for dev/testing |
| API Documentation | Swagger UI       | Interactive API exploration                |


## 🎯 Key Features

The API enforces critical business rules to maintain data integrity:

- **RESTful CRUD Operations:** Manage the full lifecycle of a Book resource.
- **Data Validation:**
  - **Unique ISBN:** ISBN must be unique across all books.
  - **Required Fields:** `name`, `isbn`, `price`, `publishDate`, `bookType`.
  - **Price Constraint:** Price must be non-negative (≥ 0).
  - **Date Format:** `publishDate` strictly in `dd/MM/yyyy` format.

## 🚀 Getting Started

Follow these steps to **clone, build, and run** the API locally.

<details>
  <summary>Linux / macOS Instructions</summary>

```bash
  # Clone the repository
  git clone https://github.com/MrJusticeShai/book-catalogue-api.git
  cd book-catalogue-api

  # Build the project
  mvn clean install

  # Run the Spring Boot application
  mvn spring-boot:run
```
The service will be accessible at: http://localhost:9000

Using Executable Jar
```
# Build the project (creates fat jar)
mvn clean package

# Run the jar
java -jar target/book-catalogue-api-0.0.1-SNAPSHOT.jar
```
> 💡 Tip: The jar method is useful if Maven is not installed or for deployment purposes.
</details>

---

<details><summary> Windows Instructions (Command Prompt / PowerShell) </summary>

```bash
# Clone the repository
git clone https://github.com/MrJusticeShai/book-catalogue-api.git
cd book-catalogue-api

#  Install Maven (if not already installed)
# macOS (via Homebrew)
brew install maven

# Linux (Debian/Ubuntu)
sudo apt update
sudo apt install maven -y

# Build the project
mvn clean package
# Run the Spring Boot web application
mvn spring-boot:run
#  Run unit tests
mvn test

```
The service will be accessible at: http://localhost:9000

Using Executable Jar

```bash
# Build the project (creates fat jar)
mvn clean package

# Download Apache Maven from https://maven.apache.org/download.cgi
# Extract and add the 'bin' directory to your PATH
# Example: C:\apache-maven-3.9.9\bin

# Build and package the project
mvn clean package
#  Run unit tests
mvn test
# Run the Spring Boot web application
mvn spring-boot:run
### Or
# Run the jar
java -jar target\book-catalogue-api-0.0.1-SNAPSHOT.jar
```
> 💡 Tip: The jar method is useful if Maven is not installed or for deployment purposes.
</details>

---

## 💻 API Exploration

### Swagger UI (Interactive Testing)
Navigate to: [http://localhost:9000/swagger-ui.html](http://localhost:9000/swagger-ui.html)  
Swagger provides a user-friendly interface to test **GET, POST, PUT, DELETE** endpoints.

### H2 Database Console
Inspect the in-memory H2 database:

| Setting       | Value                            |
|---------------|----------------------------------|
| Console URL   | http://localhost:9000/h2-console |
| JDBC URL      | jdbc:h2:mem:booksdb              |
| User/Password | sa / (leave blank)               |

> Note: H2 is ephemeral; all data is wiped on restart.

## 📦 API Endpoints

| Endpoint               | Method | Purpose                         | HTTP Statuses                                |
|------------------------|--------|---------------------------------|----------------------------------------------|
| /api/books             | GET    | List all books                  | 200 (OK)                                     |
| /api/books/isbn/{isbn} | GET    | Retrieve a book by ISBN         | 200 (OK), 404 (Not Found)                    |
| /api/books             | POST   | Create a new book               | 201 (Created), 400 (Bad Request)             |
| /api/books/isbn/{isbn} | PUT    | Update an existing book by ISBN | 200 (OK), 400 (Bad Request), 404 (Not Found) |
| /api/books/isbn/{isbn} | DELETE | Delete a book by ISBN           | 204 (No Content), 404 (Not Found)            |

### Example JSON Body (POST / PUT)

```json
{
  "name": "The Great Novel",
  "isbn": "123-456-789-0123",
  "publishDate": "15/06/2024",
  "price": 29.99,
  "bookType": "HARDCOVER"
}
```
> publishDate must be in dd/MM/yyyy format.

### ⚡ Quick cURL Demonstrations

➕ **Create a Book**
```
curl -X POST http://localhost:9000/api/books \
-H "Content-Type: application/json" \
-d '{"name":"Book1","isbn":"111","publishDate":"24/10/2025","price":19.99,"bookType":"HARDCOVER"}'
```

📖 **Get All Books**
```
curl http://localhost:9000/api/books
```

📖 **Get Book By Isbn**
```
curl http://localhost:9000/api/books/isbn/111
```

✏️ **Update a Book**
```
curl -X PUT http://localhost:9000/api/books/isbn/111 \
-H "Content-Type: application/json" \
-d '{"name":"Updated Book","isbn":"111","publishDate":"25/10/2025","price":24.99,"bookType":"SOFTCOVER"}'
```

❌ Delete a Book
```
curl -X DELETE http://localhost:9000/api/books/isbn/111
```
> All endpoints follow RESTful principles with clean separation of layers.


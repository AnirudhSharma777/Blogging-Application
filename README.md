# 📚 Blog Application

Welcome to the **Blog Application**! This is a full-stack project built with **Spring Boot** and **PostgreSQL**. It provides a simple yet powerful platform for users to create, update, and delete blog posts. Additionally, it integrates with the **OpenAI API** to generate AI-powered blog summaries.

---

## 🌟 Features

- ✏️ **CRUD operations** for blog posts
- 🔍 **Search and filter** blogs by various fields
- 🧹 **Sorting and pagination** support
- 🤖 **AI-powered blog summaries** using the OpenAI API
- 🔐 **User authentication** for secure access
- 🐳 **Docker Compose integration** for PostgreSQL setup
- 📦 **Clean architecture** with DTOs, services, and repositories
- 🧪 **Unit testing** using JUnit and Mockito

---

## 🚀 Technologies Used

- **Spring Boot** - Backend framework
- **PostgreSQL** - Database
- **Docker Compose** - Containerized database
- **OpenAI API** - AI-powered text generation
- **Gradle** - Build tool
- **Java 23** - Language
- **JUnit & Mockito** - Testing

---

## 📦 Installation

### 1. **Clone the repository**

```bash
git clone https://github.com/AnirudhSharma777/Blogging-Application.git
cd blog-application
```

### 2. **Set up environment variables**

Create an `application.yml` file in `src/main/resources/` and configure your database and OpenAI API key:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/blogdb
    username: postgres
    password: password
  jpa:
    hibernate:
      ddl-auto: update

openai:
  api:
    key: YOUR_OPENAI_API_KEY
    url: https://api.openai.com/v1/chat/completions
```

### 3. **Run PostgreSQL with Docker**

Ensure you have Docker installed, then start PostgreSQL:

```bash
docker-compose up -d
```

Your `docker-compose.yml` should look like this:

```yaml
version: '3.8'
services:
  postgres:
    image: postgres
    environment:
      POSTGRES_DB: blogdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
```

### 4. **Build and run the project**

```bash
./gradlew clean build
./gradlew bootRun
```

---

## 📡 API Endpoints

### Blog Endpoints

| Method | Endpoint                            | Description                      |
|------- |-----------------------------------  |----------------------------------|
| GET    | `/api/v1/blogs`                     | Get all blogs                    |
| GET    | `/api/v1/blogs/{id}`                | Get blog by ID                   |
| GET    | `/api/v1/blogs/paginated`           | Get paginated blog list          |
| GET    | `/api/v1/blogs/sorted`              | Get sorted blog list             |
| POST   | `/api/v1/blogs`                     | Create a new blog                |
| PUT    | `/api/v1/blogs/{id}`                | Update blog by ID                |
| DELETE | `/api/v1/blogs/{id}`                | Delete blog by ID                |
| POST   | `/api/v1/openai/generate`           | Generate blog summary (AI)       |

### Authentication Endpoints

| Method | Endpoint                            | Description                          |
|------- |-------------------------------------|--------------------------------------|
| POST   | `/api/v1/auth/register`              | Register a new user                  |
| POST   | `/api/v1/auth/login`                 | Authenticate user                    |

---

## ✅ Usage

### **Create a new blog**

```bash
curl -X POST http://localhost:8080/api/v1/blogs \
     -H "Content-Type: application/json" \
     -d '{"title": "My First Blog", "content": "This is my blog content", "userId": "1"}'
```

### **Get paginated blogs**

```bash
curl -X GET "http://localhost:8080/api/v1/blogs/paginated?page=0&size=5"
```

### **Get sorted blogs**

```bash
curl -X GET "http://localhost:8080/api/v1/blogs/sorted?field=title"
```

### **Generate an AI-powered summary**

```bash
curl -X POST http://localhost:8080/api/v1/openai/generate \
     -H "Content-Type: application/json" \
     -d "Summarize the impact of AI on modern blogging."
```

### **User Registration**

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
     -H "Content-Type: application/json" \
     -d '{"username": "johndoe", "password": "password123", "email": "john@example.com"}'
```

### **User Login**

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username": "johndoe", "password": "password123"}'
```

---

## 🛠️ Testing

You can run unit tests with:

```bash
./gradlew test
```

The tests use **JUnit** and **Mockito** to validate the application's functionality.

---

## 📄 Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

1. Fork the project
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a pull request

---

## 🔒 License

This project is licensed under the MIT License — feel free to use, modify, and share!

---

## 📧 Contact

For any questions or feedback, reach out via [anirudhsharma857000@gmail.com](mailto:anirudhsharma857000@gmail.com).

---

Happy coding! 🚀


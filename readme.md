# Expense Tracker Backend (Spring Boot + PostgreSQL + Docker)

## Project Overview

This is a backend application for an **Expense Tracker**, built using **Java 21, Spring Boot, Spring Data JPA, and PostgreSQL**. It provides:

* **User Authentication** (JWT-based)
* **Expense Management** (CRUD)
* **Budget Management & Alerts**
* **Analytics APIs** (Daily/Monthly spending summary)
* **Batch Expense Import**
* **Dockerized Environment**
* **CI/CD Ready Setup**

This project simulates a production-ready microservice architecture similar to apps like **Revolut**.

---

## Tech Stack

| Layer            | Technology                            |
| ---------------- | ------------------------------------- |
| Backend          | Java 21, Spring Boot, Spring Security |
| Database         | PostgreSQL 16                         |
| ORM              | Spring Data JPA / Hibernate           |
| Authentication   | JWT                                   |
| Containerization | Docker, Docker Compose                |
| CI/CD            | GitHub Actions (optional)             |
| API Testing      | Postman                               |

---

## Getting Started

Follow these steps to run the project on your local machine:

1. **Clone the repository**

```bash
git clone https://github.com/<your-username>/expense-tracker.git
cd expense-tracker
```

2. **Create .env file**
   Create a `.env` file in the project root with the following content (adjust values as needed):

```
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/expense_db
SPRING_DATASOURCE_USERNAME=your_usernsme
SPRING_DATASOURCE_PASSWORD=your_password
APP_PORT=8081
JWT_SECRET=your-secret-key
```

3. **Build the project**

```bash
mvn clean package -DskipTests
```

4. **Run using Docker Compose**

```bash
docker-compose up --build
```

5. **Access the application**

* Backend APIs: `http://localhost:8081`
* PostgreSQL (pgAdmin optional): `http://localhost:5050`

    * Add the PostgreSQL server using credentials from `.env`

---

## Docker Setup

### Dockerfile

Create a Dockerfile to containerize the Spring Boot application. Make sure the JAR filename matches your build output.

### docker-compose.yml

Use Docker Compose to define services:

* **PostgreSQL** (database)
* **pgAdmin** (optional GUI for DB management)
* **App** (Spring Boot service)

Set up environment variables in the `.env` file and use them in `docker-compose.yml`.

---

## API Endpoints

### Authentication

* `POST /api/auth/signup` → Register user
* `POST /api/auth/login` → Login and get JWT

### Expenses

* `POST /api/expenses` → Add expense
* `GET /api/expenses` → Fetch user expenses
* `PUT /api/expenses/{id}` → Update expense
* `DELETE /api/expenses/{id}` → Delete expense
* `POST /api/expenses/bulk` → Bulk import expenses

### Budgets

* `POST /api/budgets` → Set category budget
* `GET /api/budgets` → Get budget summary

### Analytics

* `GET /api/expenses/summary/daily` → Daily spending breakdown
* `GET /api/expenses/summary/monthly` → Monthly spending summary

---

## Headers for Protected Endpoints

Include in all requests:

```
Authorization: Bearer <JWT_TOKEN>
```

---

## Testing APIs with Postman

1. Sign up a new user.
2. Login → copy JWT token.
3. Include JWT in headers for all requests.
4. Test CRUD operations, budgets, and analytics.

---

## CI/CD (GitHub Actions Example)

Set up a pipeline to:

1. Checkout code
2. Set up Java
3. Build Maven package
4. Build Docker image
5. Optionally deploy using Docker Compose

---

## Key Notes

* PostgreSQL data persists in Docker volume.
* App runs on configurable port (default 8081).
* JWT authentication secures all `/api/expenses/*` endpoints.
* Batch import endpoint optimized for large records.

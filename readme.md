# 📚 Library Management System

A simple **Library Management System** built with **Spring Boot**, **MySQL**, **Thymeleaf**, and **Bootstrap**.

The project provides management of books, users, authors, publishers, categories, and book borrowing/returning.

---

## 🚀 Technologies

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Spring Security
* Thymeleaf
* MySQL 8
* Bootstrap 5
* Maven
* Docker & Docker Compose

---

## ✨ Features

### 📖 Book Management

* Create books
* Edit books
* Delete books
* ISBN validation
* Automatic ISBN generation when empty
* Quantity management
* Available quantity management
* Prevent reducing quantity below borrowed books

### 👤 User Management

* Create users
* Edit users
* Delete users
* User roles
* Password encryption with BCrypt
* Duplicate email validation

### 🏷️ Categories

* Create categories
* Edit categories
* Delete categories
* Duplicate name validation

### ✍️ Authors

* Create authors
* Edit authors
* Delete authors
* Duplicate validation

### 🏢 Publishers

* Create publishers
* Edit publishers
* Delete publishers
* Duplicate validation

### 📚 Borrow Management

* Borrow books
* Return books
* Borrow status management
* Borrow date and due date validation
* Prevent duplicate active borrows
* Automatic available-quantity updates

### 🔐 Security

* Login authentication
* Spring Security
* BCrypt password hashing
* Role-based authorization
* CSRF protection

### 🎨 UI

* Bootstrap-based interface
* Responsive forms and tables
* Searchable entity selection
* Confirmation modal for deletion
* Success, error and warning alerts
* Back navigation with `returnTo`
* Add users/books/entities directly from related forms

---

## 📂 Project Structure

```text
library-management/
│
├── docker/
│   ├── docker-compose.yml
│   │
│   └── mysql/
│       ├── 01-schema.sql
│       └── 02-data.sql
│
├── src/
│   └── main/
│       ├── java/
│       │   └── ...
│       │
│       └── resources/
│           ├── static/
│           └── templates/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## 🐳 Run MySQL with Docker

Make sure Docker is installed and running.

Go to the Docker directory:

```bash
cd docker
```

Start MySQL:

```bash
docker compose up -d
```

The MySQL database will be available on:

```text
localhost:3306
```

### Database configuration

```text
Database: library_db
Username: admin
Password: 1234
```

---

## 🗄️ Database Initialization

The project uses MySQL initialization scripts:

```text
docker/mysql/
├── 01-schema.sql
└── 02-data.sql
```

`01-schema.sql` creates the database tables and relationships.

`02-data.sql` inserts test data.

These scripts are executed automatically by MySQL **only when the database volume is initialized for the first time**.

If you want to completely recreate the database:

```bash
cd docker
docker compose down -v
docker compose up -d
```

> ⚠️ `docker compose down -v` deletes the MySQL volume and all existing database data.

---

## ▶️ Run the Application

After starting MySQL, return to the project root:

```bash
cd ..
```

Run Spring Boot with Maven Wrapper:

### macOS / Linux

```bash
./mvnw spring-boot:run
```

### Windows

```cmd
mvnw.cmd spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## 🔑 Authentication

The application uses **Spring Security** for authentication and authorization.

A test administrator account can be provided through:

```text
docker/mysql/02-data.sql
```

Make sure the stored password is BCrypt encoded.

---

## 🧹 Useful Commands

### Start MySQL

```bash
cd docker
docker compose up -d
```

### Stop MySQL

```bash
cd docker
docker compose stop
```

### Stop and remove containers

```bash
cd docker
docker compose down
```

### Reset database

```bash
cd docker
docker compose down -v
docker compose up -d
```

### View MySQL logs

```bash
cd docker
docker compose logs mysql
```

### Check running containers

```bash
docker ps
```

---

## 🔑 Default Login

The project includes a default administrator account for testing.

| Field    | Value            |
| -------- | ---------------- |
| Email    | `admin@test.com` |
| Password | `1234`           |
| Role     | `ADMIN`          |


# PrayerConnect

PrayerConnect is a Java-based application designed to help users share, manage, and pray for requests in a secure and modern way. It uses Spring Boot, RESTful APIs, and JWT authentication for a robust developer and user experience.

---

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Usage Examples](#usage-examples)
- [API Reference](#api-reference)
- [Contributing](#contributing)
- [Testing](#testing)
- [License](#license)
- [Contact](#contact)

---

## Features

- **User Registration & Login:** Secure authentication using Spring Security and JWT.
- **Prayer Requests:** Create, view, update, and delete prayer requests.
- **RESTful API:** All features accessible via endpoints.
- **DTO Pattern:** Clean separation between internal models and API responses.
- **Unit Tests:** Reliable codebase with test coverage.
- **Modern Security:** Updated security configuration for improved UX.

---

## Getting Started

Get PrayerConnect running locally by following these steps:

### Prerequisites

- Java 8 or higher
- Maven or Gradle
- Git

### Installation

1. **Clone the repository**
   ```sh
   git clone https://github.com/Ranelan/PrayerConnect.git
   cd PrayerConnect
   ```

2. **Build the project**
   ```sh
   ./mvnw clean install
   ```
   _or_
   ```sh
   ./gradlew build
   ```

3. **Run the application**
   ```sh
   ./mvnw spring-boot:run
   ```
   _or_
   ```sh
   ./gradlew bootRun
   ```

4. **Access the application**
   - Open your browser and go to: `http://localhost:8080`

---

## Project Structure

```
PrayerConnect/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/prayerconnect/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── PrayerConnectApplication.java
│   ├── test/
│   │   └── java/
│   │       └── com/prayerconnect/
├── pom.xml / build.gradle
└── README.md
```

---

## Configuration

PrayerConnect uses [Spring Boot](https://spring.io/projects/spring-boot) for configuration. All configuration settings are located in `src/main/resources/application.properties`.

### Database Configuration

By default, PrayerConnect uses MYSQL database for development. To use PostgreSQL, or another database, update these properties:

```
**Example for MySQL:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/prayerconnect
spring.datasource.username=your_mysql_user
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

### Security Configuration

PrayerConnect uses JWT for authentication. Set your secret key and token expiration in `application.properties`:

```properties
jwt.secret=your_jwt_secret_key
jwt.expirationMs=86400000
```

### Customizing the Server Port

Change the server port (default: 8080):

```properties
server.port=8080
```

---

## Usage Examples

Here are sample API calls using `curl`:

### Register a new user

```sh
curl -X POST http://localhost:8080/api/register \
     -H "Content-Type: application/json" \
     -d '{"username":"testuser","password":"yourpassword"}'
```

### Login (get JWT token)

```sh
curl -X POST http://localhost:8080/api/login \
     -H "Content-Type: application/json" \
     -d '{"username":"testuser","password":"yourpassword"}'
```

### Create a prayer request

```sh
curl -X POST http://localhost:8080/api/prayers \
     -H "Authorization: Bearer <your_token>" \
     -H "Content-Type: application/json" \
     -d '{"title":"Pray for health","description":"Need prayers for my family"}'
```

### Get all prayer requests

```sh
curl http://localhost:8080/api/prayers
```

---

## API Reference

| Endpoint                 | Method | Description                                    |
|--------------------------|--------|------------------------------------------------|
| `/api/register`          | POST   | Register a new user                            |
| `/api/login`             | POST   | Log in and get JWT token                       |
| `/api/prayers`           | GET    | Get all prayer requests                        |
| `/api/prayers`           | POST   | Create a new prayer request (auth required)    |
| `/api/prayers/{id}`      | PUT    | Update a prayer request (auth required)        |
| `/api/prayers/{id}`      | DELETE | Delete a prayer request (auth required)        |

More details can be found in the controller classes in `src/main/java/com/prayerconnect/controller/`.

---

## Contributing

Contributions are welcome!

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

**Coding standards:**
- Follow existing code style and conventions
- Write clear commit messages
- Add unit tests for new features

---

## Testing

To run tests:

```sh
./mvnw test
```
_or_
```sh
./gradlew test
```

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Contact

Maintainer: [Ranelan](https://github.com/Ranelan)  
For questions, open an issue or email: raneyclassic@gmail.com

---

**Tip:**  
If you're new to Java or Spring Boot, check out the official [Spring Boot documentation](https://spring.io/projects/spring-boot) for helpful guides and tutorials!

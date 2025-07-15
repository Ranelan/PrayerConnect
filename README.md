# PrayerConnect

PrayerConnect is a Java Spring Boot application designed to facilitate the submission, review, and management of prayer requests and testimonies. It provides a secure platform where users can submit and share prayers and testimonies with both regular and admin users.

## Features

- **User Roles:** Supports both regular users and admin users, each with distinct permissions and capabilities.
- **Prayer Requests:** Users can create and submit prayer requests, which can be reviewed and commented on by admins.
- **Testimonies:** Users can submit testimonies, and admins can review and manage them.
- **Anonymous Submissions:** Users have the option to submit prayer requests anonymously.
- **Approval Workflow:** Admins can review, comment, and update the approval status of each prayer request and testimony.
- **JWT-based Authentication:** Secure access to endpoints using JSON Web Token (JWT) authentication.
- **Spring Security Integration:** Configured security with custom authentication filters. SecurityConfig updated to allow access to Testimony endpoints.
- **DTO Pattern:** Uses Data Transfer Objects for safely transporting prayer request and testimony data.
- **Unit Test Coverage:** Factory, Service and Controller tests 

## Getting Started

### Prerequisites

- Java 17 or above
- Maven or Gradle
- [Spring Boot](https://spring.io/projects/spring-boot) dependencies
- Database (e.g., H2, MySQL, or PostgreSQL) configured in `application.properties`

### Installation

1. **Clone the Repository**
    ```bash
    git clone https://github.com/Ranelan/PrayerConnect.git
    cd PrayerConnect
    ```

2. **Configure Environment**
    - Set up your database connection in `src/main/resources/application.properties`.
    - Add your JWT secret in the same properties file:
      ```
      jwt.secret=YOUR_SECRET_KEY_HERE
      ```

3. **Build and Run**
    - Using Maven:
      ```bash
      mvn spring-boot:run
      ```
    - Or using Gradle:
      ```bash
      ./gradlew bootRun
      ```

4. **Access the Application**
    - By default, the application runs on `http://localhost:8080`.

## Usage

- **API Endpoints:** (secured with JWT)
    - `/api/prayerRequest/**` — For submitting and retrieving prayer requests
    - `/api/testimony/**` — For submitting and retrieving testimonies
    - `/api/admins/**` — Admin operations
    - `/api/regular-users/**` — User operations

- Obtain a JWT token by logging in, then include it in the `Authorization` header for API requests:
    ```
    Authorization: Bearer YOUR_JWT_TOKEN
    ```

## Project Structure

- `src/main/java/za/co/PrayerConnect/` — Main application code
- `domain/` — Entity classes (e.g., User, Admin, PrayerRequest, Testimony)
- `dto/` — Data Transfer Objects
- `config/` — Security and application configuration
- `service/` — Business logic and service layer (Recent: Service and interaction logic improved, new tests added)
- `repository/` — Data access layer, including TestimonyRepository
- `controller/` — Controllers, including PrayerRequestController, TestimonyController, PrayerInteractionController

## Security

- Uses Spring Security and JWT for authentication and authorization.
- Passwords are encrypted using BCrypt.
- SecurityConfig updated to allow access to Testimony endpoints.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request.

## License

This project currently does not specify a license.

---

© 2025 Ranelan

_Recent updates summarized from commits; results may be incomplete. For more details, see [recent commits](https://github.com/Ranelan/PrayerConnect/commits)._

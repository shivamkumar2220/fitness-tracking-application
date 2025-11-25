# Online Fitness Tracking Application

This Spring Boot application delivers a lightweight fitness-tracking platform with two tailored dashboards:

- **Admin portal** – manage users, approve community content, configure system settings, and monitor engagement metrics.
- **User portal** – log workouts, follow progress insights, join challenges, and read curated guidance.

## Stack

- Java 17 / Spring Boot 3.3
- Spring Data JPA with in-memory H2
- Thymeleaf for server-rendered UI plus Chart.js visualizations
- Maven for build/test

## Getting Started

```bash
mvn clean package
mvn spring-boot:run
```

Then open:

- `http://localhost:8080/admin` – Admin dashboard (seeded credentials: `admin@example.com` / `admin123`)
- `http://localhost:8080/user?userId=2` – Sample user dashboard for Jane Doe

## Data & Persistence Notes

- H2 stores data in-memory (`jdbc:h2:mem:fitnesstracker`), making local runs disposable.
- `DataInitializer` seeds demo admins, users, workouts, content, settings, and challenges on startup when tables are empty.
- Replace H2 with PostgreSQL/MySQL by updating `spring.datasource.*` and `spring.jpa.hibernate.ddl-auto`.

## Testing

Run all tests:

```bash
mvn test
```

Included tests cover:

- Context bootstrapping (`FitnessTrackerApplicationTests`)
- `UserService` workout logging and analytics

## Project Structure

```
src/main/java/com/sach/fitnesstracker
├── config        # Startup data seeding
├── controller    # MVC controllers (Admin/User dashboards)
├── dto           # Dashboard view models
├── model         # JPA entities
├── repository    # Spring Data repositories
├── service       # Admin & user business logic
└── ...
src/main/resources
├── templates     # Thymeleaf views
├── static/css    # Shared styling
└── static/js     # Chart logic for dashboards
```

## Next Steps

- Swap to a persistent database and enable authentication.
- Add REST APIs/mobile clients.
- Expand analytics with goal tracking and notifications.


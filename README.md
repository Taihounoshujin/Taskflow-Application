TaskFlow

A Trello-style project management REST API built with Spring Boot 3, PostgreSQL, and JWT authentication.
Users can create workspaces, organize boards within them, define columns on each board (e.g. "To Do", "In Progress", "Done"),
and manage cards with assignees and due dates.
Every endpoint is protected by stateless JWT authentication.
__________

Tech Stack
- Java 21 · Spring Boot 3.3
- Spring Data JPA + Hibernate for persistence
- PostgreSQL in production/Docker · H2 in-memory for local dev
- Spring Security + JWT (JJWT 0.12) for stateless authentication
- BCrypt for password hashing
- SpringDoc OpenAPI for auto-generated Swagger documentation
- Lombok for boilerplate reduction
- Jakarta Bean Validation for input validation
- Docker + Docker Compose for one-command deployment
__________







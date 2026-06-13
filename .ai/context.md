Repository AI Context

Purpose

This repository (order-service) is part of the Production Troubleshooting Lab, whose purpose is to simulate, observe, troubleshoot, and optimize real-world Spring Boot applications and microservices.

The emphasis is on production support, observability, performance tuning, and microservice best practices rather than business complexity.

⸻

Tech Stack

* Java 21
* Spring Boot 3.x
* Maven
* MySQL 8
* Redis
* Docker Compose
* Spring Data JPA + Hibernate

Future integrations:

* OpenFeign
* Prometheus
* Grafana
* OpenTelemetry
* Jaeger
* Resilience4j
* JMeter
* k6

⸻

Architecture Principles

Follow SOLID principles.

Prefer simplicity over unnecessary abstraction.

Keep code production-grade and interview-ready.

Always maintain backward compatibility with previous stages.

Support future additions without major refactoring.

Services should remain independently deployable.

⸻

Package Structure

Separate concerns into:

* controller
* service
* repository
* entity
* dto
* mapper
* config
* exception
* util

Never expose entities directly to API consumers.

⸻

Dependency Injection

Use constructor injection only.

Never use field injection.

⸻

DTO Guidelines

Prefer Java records for DTOs.

Request DTOs and Response DTOs must be separate.

Controllers should exchange DTOs only.

⸻

Database Guidelines

Prefer UUID primary keys.

Enable lazy loading by default.

Avoid EAGER fetching unless required.

Use indexes where appropriate.

Design entities to support future performance experiments such as:

* N+1 queries
* Connection pool exhaustion
* Slow queries
* Lock contention

⸻

REST API Standards

Use proper HTTP status codes.

Use validation annotations.

Implement global exception handling.

Return meaningful error messages.

Avoid exposing internal exceptions.

⸻

Logging Standards

Use SLF4J + Logback.

Never use System.out.println.

Log levels:

* INFO
* WARN
* ERROR

Support future structured logging.

Every request should eventually support:

* correlationId
* endpoint
* execution time
* timestamp

⸻

Performance Principles

Code should support future simulations of:

* slow APIs
* memory leaks
* high CPU usage
* deadlocks
* thread starvation
* connection pool exhaustion
* external service latency

Avoid premature optimization.

Measure first, optimize later.

⸻

Observability

Expose Spring Boot Actuator endpoints.

Design code so metrics and tracing can be added without restructuring.

Future tools:

* Micrometer
* Prometheus
* Grafana
* OpenTelemetry
* Jaeger

⸻

Testing

Use:

* JUnit 5
* Mockito

Prefer readability over excessive mocking.

⸻

Coding Guidelines

Generate complete code.

Avoid pseudo-code and placeholders.

Ensure generated code compiles.

Explain design decisions before implementation.

Update:

* pom.xml
* application.yml
* Dockerfile
* docker-compose changes

whenever necessary.

⸻

Future Failure Scenarios

The system should eventually support:

* Slow APIs
* Missing indexes
* N+1 queries
* Memory leaks
* High CPU usage
* Thread deadlocks
* Connection pool exhaustion
* External dependency failures
* Retry storms
* GC pauses

Always keep future observability and troubleshooting requirements in mind.
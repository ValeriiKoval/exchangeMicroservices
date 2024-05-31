# Exchange Microservices

This repository contains a microservices-based application for an exchange platform, built using Java 17, Spring Boot 2.7.5, Spring Cloud 2021.0.9, and various other technologies and patterns. The application follows a distributed architecture, leveraging microservices principles and cloud-native patterns to ensure scalability, resilience, and flexibility.

## Project Architecture

The project architecture is depicted in the following diagram:

![image](https://github.com/ValeriiKoval/exchangeMicroservices/assets/103948322/101659ef-8df6-468b-9738-0dbb2c72b00c)


The key components of the architecture are:

1. **Clients**: Represents the client applications or interfaces that interact with the exchange platform.
2. **Cloud**: Encompasses the cloud infrastructure and services utilized by the application.
   - **Redis**: Used for rate limiting and caching purposes.
   - **API Gateway**: Acts as the entry point for client requests, providing routing and load balancing capabilities.
   - **Service Discovery**: Enables service registration and discovery, allowing microservices to locate and communicate with each other dynamically.
3. **Business**: Contains the core business logic and microservices of the exchange platform.
   - **Auth Service**: Handles user authentication and registration, integrating with a MySQL database.
   - **Mail Service**: Responsible for sending email notifications, such as verification emails.
   - **Wallet Service**: Manages user wallets, including top-up and withdrawal operations, interacting with a MySQL database.
   - **Order Service**: Handles the creation and management of orders, persisting data in a MySQL database.
4. **Adapters**: Provides integration with external systems and services.
   - **Email Service**: Adapter for sending emails, utilizing an external mail server.
   - **Exchange Service**: Facilitates the exchange of currencies, retrieving stock information from external sources.
5. **Queue**: Implements an asynchronous messaging system using Apache Kafka for reliable and scalable communication between microservices.

## Technologies and Frameworks

The project utilizes the following technologies and frameworks:

- **Java 17**: The primary programming language used for the application.
- **Spring Boot 2.7.5**: A framework for building production-grade Spring applications with minimal configuration.
- **Spring Cloud 2021.0.9**: A suite of tools and libraries for implementing microservices architectures, including service discovery, API gateways, and circuit breakers.
- **Freemarker**: A template engine used for generating dynamic content, such as email templates.
- **MySQL**: A relational database management system used for data persistence.
- **Apache Kafka**: A distributed event streaming platform for building real-time data pipelines and applications.
- **Spring Service Registry**: A service registry implementation for service discovery and load balancing.
- **Spring API Gateway**: A gateway service for routing and load balancing client requests.
- **Spring Feign**: A declarative HTTP client for consuming and communicating with microservices.
- **JSON Web Tokens (JWT)**: An authentication mechanism for securing microservices communication.

## Microservices Patterns

The project incorporates the following microservices patterns:

1. **Service Discovery**: Microservices are registered with the Service Discovery component, allowing other services to locate and communicate with them dynamically.
2. **API Gateway**: The API Gateway acts as a single entry point for client requests, routing them to the appropriate microservices and providing load balancing capabilities.
3. **Event Source**: Apache Kafka is used as an event source, enabling asynchronous communication and decoupling between microservices through event-driven architectures.

Here's an updated version of the "Getting Started" section:

## Getting Started

To set up and run the project locally, follow these steps:

### Prerequisites

- Install Docker and Docker Compose on your machine. You can find instructions for your specific operating system on the official Docker website: https://docs.docker.com/get-docker/

### Running the Application

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/ValeriiKoval/exchangeMicroservices.git
   ```

2. Navigate to the project's root directory:

   ```bash
   cd exchangeMicroservices
   ```

3. Build and start the application using Docker Compose:

   ```bash
   docker-compose up --build
   ```

   This command will build and start all the necessary containers for the microservices, databases, and other dependencies.

4. Once the containers are up and running, you can access the application through the API Gateway service.

### Testing the API

A Postman collection is included in the `postman_collection` folder of the repository. You can import this collection into Postman to test the various endpoints and functionalities of the exchange platform.

To use the Postman collection:

1. Open Postman.
2. Click on the "Import" button.
3. Select the "Upload Files" option.
4. Navigate to the `postman_collection` folder and select the Postman collection file.
5. Click "Import".

The Postman collection will now be available in your workspace, allowing you to explore and test the API endpoints.
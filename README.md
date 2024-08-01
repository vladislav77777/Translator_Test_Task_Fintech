# Translation Service

This is a simple translation service built with Spring Boot that uses an external translation API.

## Prerequisites

- Docker
- Docker Compose
- Java 17 or higher
- Maven

## Getting Started

1. Clone the repository:

    ```bash
    git clone https://github.com/vladislav77777/Translator_Test_Task_Fintech
    cd translation-service
    ```

2. Build the project:

    ```bash
    mvn clean install
    ```

3. Start the PostgreSQL database using Docker Compose:

    ```bash
    docker-compose up -d
    ```

4. Run the application:

    ```bash
    mvn spring-boot:run
    ```

5. Open your browser and go to `http://localhost:8080`.

## Usage

Enter the text you want to translate, the source language, and the target language, then click the "Translate".

Screens:

TODO...



![image](https://github.com/user-attachments/assets/6390cbca-6f98-47bf-999e-ce0835c82fcb)

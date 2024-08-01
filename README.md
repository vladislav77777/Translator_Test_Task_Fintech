# Translation Service

This is a simple translation service built with Spring Boot that uses an external translation rapid API.

## Prerequisites

- Docker
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

4. Run the application.

5. Open your browser and go to `http://localhost:8080`.

## Usage

Enter the text you want to translate, the source language, and the target language, then click the "Translate".

Screens:

![image](https://github.com/user-attachments/assets/39d72bad-a4fd-4731-8220-e7eae157f5a7)

![image](https://github.com/user-attachments/assets/a4af0c95-c59d-4623-8ec4-b90e5d436433)

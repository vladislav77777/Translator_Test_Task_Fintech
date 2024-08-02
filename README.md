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
    
2. Build the project and add .env file:

    ```bash
    mvn clean install
    ```

    .env file: https://disk.yandex.ru/d/5BzAc_341XoLtg
   
- Paste it in the root directory of the project.

3. Start the PostgreSQL, Liquibase and the application itself using Docker Compose:

    ```bash
    docker-compose up -d
    ```
4. Open your browser and go to `http://localhost:8080`.

## Usage

Enter the text you want to translate, the source language, and the target language, then click the "Translate".

Screens:


![image](https://github.com/user-attachments/assets/39d72bad-a4fd-4731-8220-e7eae157f5a7)

ошибка с языком перевода
![image](https://github.com/user-attachments/assets/538d7d98-b510-4db7-90a2-96d2c143dfdf)

![image](https://github.com/user-attachments/assets/a4af0c95-c59d-4623-8ec4-b90e5d436433)

ошибка API

![image](https://github.com/user-attachments/assets/be8ed6d5-01ac-42c8-9eb8-1bbbcea67edf)



сохранение в БД


![image](https://github.com/user-attachments/assets/68072696-6d2b-4ede-a184-0a15d185fa51)

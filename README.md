# 📚 Book Library API
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

RESTful веб-сервис для управления библиотечной системой. Приложение позволяет вести учет книг, авторов, читателей, а также оформлять выдачу и возврат книг с соблюдением бизнес-логики.

Разработано в рамках учебного командного проекта на **Spring Boot 4**.

---

## 🚀 Функциональность

### Основные возможности
*   **Управление книгами и авторами**: CRUD операции, создание связей "Многие-ко-многим".
*   **Управление читателями**: Регистрация, редактирование профилей.
*   **Цикл выдачи книг**:
    *   Оформление выдачи (с проверкой доступности экземпляров).
    *   Возврат книги (с обновлением статуса и остатков).
    *   Отслеживание активных и просроченных выдач.
*   **Поиск и фильтрация**: Гибкий поиск книг по названию, автору, жанру и году издания.
*   **Пагинация и сортировка**: Поддержка `Pageable` для всех списковых эндпоинтов.
*   **Валидация и обработка ошибок**: Глобальный обработчик исключений (`@RestControllerAdvice`) для единого формата ответов об ошибках.

### Технические особенности
*   Использование **DTO** для скрытия внутренней структуры БД.
*   Разделение ответственности на слои: Controller → Service → Repository.
*   Интеграция с **PostgreSQL** (включая использование транзакций для операций выдачи).
*   Документация API через **Swagger / OpenAPI 3**.

---

## 🛠 Технологический стек

| Технология | Описание |
| :--- | :--- |
| **Java** | 17 / 21 |
| **Framework** | Spring Boot 4.05 |
| **Database** | PostgreSQL 17+ |
| **ORM** | Spring Data JPA / Hibernate |
| **Build Tool** | Maven / Gradle |
| **Docs** | springdoc-openapi-ui |
| **Utils** | Lombok |
| **Security** | Spring security |
| **Docker** | Docker compose, Dockerfile |
| **Testing** | JUnit 5, Mockito, Testcontainers |

---

## 📦 Структура проекта

```text
LibraryAPI/
├── src/
│   └── main/java/com/example/spring_REST/API/
│       ├── config/           # Конфигурация приложения
│       ├── controller/       # REST контроллеры
│       │   ├── LoanController.java
│       │   └── ReaderController.java
│       ├── exception/        # Глобальная обработка ошибок
│       │   ├── BookNotAvailableException.java
│       │   ├── GlobalExceptionHandler.java
│       │   ├── LoanAlreadyReturnedException.java
│       │   └── ReaderNotFoundException.java
│       ├── model/
│       │   ├── dto/          # Data Transfer Objects
│       │   │   ├── AuthorDTO.java
│       │   │   ├── BookDTO.java
│       │   │   ├── LoanDTO.java
│       │   │   └── ReaderDTO.java
│       │   └── entity/       # JPA сущности
│       │       ├── Author.java
│       │       ├── Book.java
│       │       ├── Loan.java
│       │       └── Reader.java
│       ├── repository/       # Репозитории доступа к данным
│       │   ├── AuthorRepository.java
│       │   ├── BookRepository.java
│       │   ├── LoanRepository.java
│       │   └── ReaderRepository.java
│       ├── service/          # Бизнес-логика
│       │   ├── LoanService.java
│       │   ├── LoanServiceImpl.java
│       │   ├── ReaderService.java
│       │   └── ReaderServiceImpl.java
│       └── Application.java
│
├── src/main/resources/       # Конфигурационные файлы (application.yml и др.)
├── .env                      # Переменные окружения
├── compose.yaml              # Docker Compose конфигурация
├── Dockerfile                # Образ приложения
├── pom.xml                   # Maven зависимости
└── README.md                 # Этот файл

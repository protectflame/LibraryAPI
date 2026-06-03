# 📚 LibraryAPI

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.5-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

RESTful API для управления онлайн-библиотекой. Позволяет вести учёт книг, авторов и читателей, а также оформлять выдачу и возврат книг с проверкой доступности экземпляров.

---

## 🚀 Функциональность

- **Книги и авторы** — CRUD, связь многие-ко-многим, поиск и фильтрация по названию, автору, жанру, году
- **Читатели** — регистрация и управление профилями
- **Выдачи** — оформление с проверкой наличия экземпляров, возврат, отслеживание активных и просроченных
- **Пагинация и сортировка** — поддержка `Pageable` на всех списковых эндпоинтах
- **Безопасность** — JWT-аутентификация, ролевая модель (ADMIN / USER)
- **Документация** — Swagger UI / OpenAPI 3
- **Обработка ошибок** — глобальный `@RestControllerAdvice` с единым форматом ответов
- **Миграции БД** — Flyway
- **Веб-интерфейс** — встроенная админ-панель

---

## 🛠 Технологический стек

| Технология         | Версия / Описание                      |
|--------------------|----------------------------------------|
| Java               | 21                                     |
| Spring Boot        | 4.0.5                                  |
| Spring Security    | JWT (jjwt 0.12.6)                      |
| Spring Data JPA    | Hibernate ORM                          |
| PostgreSQL         | 17                                     |
| Flyway             | 12.4.0 — миграции схемы БД             |
| springdoc-openapi  | 3.0.2 — Swagger UI                     |
| Lombok             | Генерация boilerplate-кода             |
| Docker             | Dockerfile + Docker Compose            |
| JUnit 5 + Mockito  | Юнит-тесты                             |
| Testcontainers     | Интеграционные тесты с PostgreSQL      |
| JaCoCo             | 0.8.13 — покрытие кода                 |

---

## 📦 Структура проекта

```
LibraryAPI/
├── src/main/java/com/example/spring_REST/API/
│   ├── config/                  # SecurityConfig (JWT, CORS)
│   ├── controller/              # REST-контроллеры
│   ├── exception/               # Кастомные исключения + GlobalExceptionHandler
│   ├── model/
│   │   ├── dto/                 # AuthorDTO, BookDTO, LoanDTO, ReaderDTO
│   │   └── entity/              # JPA-сущности: Author, Book, Loan, Reader, User
│   ├── repository/              # Spring Data JPA репозитории
│   ├── security/                # JwtAuthenticationFilter
│   └── service/                 # Бизнес-логика (интерфейсы + реализации)
│
├── src/main/resources/
│   ├── static/
│   │   └── admin_panel.html     # Встроенная веб-админка
│   ├── db/migration/            # Flyway SQL-миграции
│   └── application.properties
│
├── compose.yaml                 # Docker Compose (app + postgres)
├── Dockerfile
├── pom.xml
└── .env                         # Переменные окружения (не коммитить!)
```

---

## ⚙️ Запуск

### Вариант 1 — Docker Compose (рекомендуется)

1. Склонируй репозиторий:
```bash
git clone https://github.com/protectflame/LibraryAPI.git
cd LibraryAPI
```

2. Создай файл `.env` в корне проекта:
```env
DB_NAME=library_db
DB_USER=library_user
DB_PASSWORD=your_password

POSTGRES_DB=library_db
POSTGRES_USER=library_user
POSTGRES_PASSWORD=your_password

JWT_SECRET=your_very_long_secret_key_at_least_256_bits
JWT_EXPIRATION=86400000
```

3. Запусти:
```bash
docker compose up --build
```

Сервер будет доступен на `http://localhost:8080`

---

### Вариант 2 — Локальный запуск

1. Создай базу данных в PostgreSQL:
```sql
CREATE DATABASE library_db;
```

2. Настрой `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=validate

jwt.secret=your_very_long_secret_key_at_least_256_bits
jwt.expiration=86400000
```

3. Запусти через Maven:
```bash
./mvnw spring-boot:run
```

---

## 👤 Создание первого администратора

Таблица `users` заполняется вручную через SQL. Пароль обязательно должен быть захэширован BCrypt.

Сгенерируй хэш на [bcrypt-generator.com](https://bcrypt-generator.com) (rounds = 10), затем выполни в psql или pgAdmin:

```sql
INSERT INTO users (username, password, email, role, created_at)
VALUES (
    'admin',
    '$2a$10$ВАШ_BCRYPT_ХЭШ_ЗДЕСЬ',
    'admin@library.com',
    'ADMIN',
    NOW()
);
```

---

## 🔐 Аутентификация

API использует **JWT Bearer**-токены.

### Получить токен

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "your_password"
}
```

Ответ:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Все защищённые запросы требуют заголовок:
```
Authorization: Bearer <токен>
```

---

## 📋 API Эндпоинты

### 🔑 Авторизация

| Метод | URL | Доступ | Описание |
|-------|-----|--------|----------|
| POST | `/api/v1/auth/login` | Публичный | Получить JWT |
| POST | `/api/v1/auth/register` | Публичный | Регистрация |

### 📖 Книги

| Метод | URL | Доступ | Описание |
|-------|-----|--------|----------|
| GET | `/api/v1/books` | Публичный | Список книг (с пагинацией) |
| GET | `/api/v1/books/{id}` | Публичный | Книга по ID |
| POST | `/api/v1/books` | ADMIN | Создать книгу |
| PUT | `/api/v1/books/{id}` | ADMIN | Обновить книгу |
| DELETE | `/api/v1/books/{id}` | ADMIN | Удалить книгу |

### 👥 Читатели

| Метод | URL | Доступ | Описание |
|-------|-----|--------|----------|
| GET | `/api/v1/readers` | ADMIN | Список читателей |
| GET | `/api/v1/readers/{id}` | ADMIN | Читатель по ID |
| POST | `/api/v1/readers` | ADMIN | Добавить читателя |
| PUT | `/api/v1/readers/{id}` | ADMIN | Обновить читателя |
| DELETE | `/api/v1/readers/{id}` | ADMIN | Удалить читателя |

### ✍️ Авторы

| Метод | URL | Доступ | Описание |
|-------|-----|--------|----------|
| GET | `/api/v1/authors` | ADMIN | Список авторов |
| GET | `/api/v1/authors/{id}` | ADMIN | Автор по ID |
| POST | `/api/v1/authors` | ADMIN | Добавить автора |
| PUT | `/api/v1/authors/{id}` | ADMIN | Обновить автора |
| DELETE | `/api/v1/authors/{id}` | ADMIN | Удалить автора |

### 📦 Выдачи

| Метод | URL | Доступ | Описание |
|-------|-----|--------|----------|
| GET | `/api/v1/loans` | ADMIN | Все выдачи |
| GET | `/api/v1/loans/active` | ADMIN | Активные выдачи |
| GET | `/api/v1/loans/overdue` | ADMIN | Просроченные выдачи |
| POST | `/api/v1/loans` | ADMIN | Создать выдачу |
| PATCH | `/api/v1/loans/{id}/return` | ADMIN | Вернуть книгу |
| DELETE | `/api/v1/loans/{id}` | ADMIN | Удалить выдачу |

---

## 🖥️ Админ-панель

Встроенный веб-интерфейс для управления библиотекой без Swagger и curl.

```
http://localhost:8080/admin_panel.html
```

Возможности:
- Авторизация с автосохранением JWT-токена
- Создание, просмотр и удаление книг, авторов, читателей
- Оформление выдач и возврат книг
- Raw JSON — просмотр всех ответов API для отладки

---

## 📄 Swagger UI

Интерактивная документация:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Тесты

```bash
# Запустить все тесты
./mvnw test

# Сформировать отчёт о покрытии (JaCoCo)
./mvnw verify
# Отчёт: target/site/jacoco/index.html
```

Для интеграционных тестов используется **Testcontainers** — Docker должен быть запущен.

---

## 🛡️ Роли

| Роль | Описание |
|------|----------|
| `ADMIN` | Полный доступ — CRUD по всем сущностям, управление выдачами |
| `USER` | Только чтение книг (GET `/api/v1/books/**`) |

---

## 🔧 CORS

CORS настроен разрешительно для разработки. В продакшене замени в `SecurityConfig.java`:

```java
// config.setAllowedOriginPatterns(List.of("https://your-domain.com")); //
```

---

## 📜 Лицензия

[MIT](LICENSE)
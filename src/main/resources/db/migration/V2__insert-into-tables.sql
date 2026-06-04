-- V2__seed_initial_data.sql
-- Начальное наполнение базы данных тестовыми записями

-- 1. Добавляем авторов
INSERT INTO authors (first_name, last_name, birth_date)
VALUES ('Лев', 'Толстой', '1828-09-09'),
       ('Фёдор', 'Достоевский', '1821-11-11');

-- 2. Добавляем книги (исправлено: created_at вместо createAt)
INSERT INTO books (title, genre, isbn, description, publish_year, total_copies, available_copies, created_at)
VALUES ('Война и мир', 'Роман', '9780000000001', 'Классический роман', 1869, 10, 10, CURRENT_TIMESTAMP),
       ('Преступление и наказание', 'Роман', '9780000000002', 'Классический роман', 1866, 8, 8, CURRENT_TIMESTAMP);

-- 3. Связываем авторов с книгами
INSERT INTO author_book_references (author_id, book_id)
VALUES (1, 1), -- Толстой -> Война и мир
       (2, 2);
-- Достоевский -> Преступление и наказание

-- 4. Добавляем пользователей (необходимо для readers с user_id)
-- Сначала создадим пользователей, чтобы читатели могли на них ссылаться
INSERT INTO users (username, password, email, role)
VALUES ('ivan_user', '$2a$10$example_hashed_password_1', 'ivan@example.com', 'READER'),
       ('librarian', '$2a$10$example_hashed_password_2', 'librarian@example.com', 'LIBRARIAN');

-- 5. Добавляем читателей
-- Примечание: второй читатель без привязки к пользователю (user_id = NULL)
INSERT INTO readers (name, email, phone, registered_at, user_id)
VALUES ('Иван Иванов', 'ivan@example.com', '89990000001', CURRENT_TIMESTAMP, 1),
       ('Пётр Петров', 'petr@example.com', '8999000002', CURRENT_TIMESTAMP, NULL);

-- 6. Создаём займы (выдачи книг)
-- Первая выдача: активная, срок не истёк
-- Вторая выдача: просрочена (due_date в прошлом), статус OVERDUE
INSERT INTO loans (reader_id, book_id, issue_date, due_date, return_date, status)
VALUES (1, 1, CURRENT_DATE, CURRENT_DATE + INTERVAL '14 days', NULL, 'ACTIVE'),
       (2, 2, CURRENT_DATE - INTERVAL '15 days', CURRENT_DATE - INTERVAL '1 day', NULL, 'OVERDUE');
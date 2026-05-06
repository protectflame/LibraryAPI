INSERT INTO authors (first_name, last_name, birth_date)
VALUES
    ('Лев', 'Толстой', '1828-09-09'),
    ('Фёдор', 'Достоевский', '1821-11-11');

INSERT INTO books (title, genre, isbn, description, publish_year, total_copies, available_copies, createAt)
VALUES
    ('Война и мир', 'Роман', '9780000000001', 'Классический роман', 1869, 10, 10, CURRENT_TIMESTAMP),
    ('Преступление и наказание', 'Роман', '9780000000002', 'Классический роман', 1866, 8, 8, CURRENT_TIMESTAMP);

INSERT INTO author_book_references (author_id, book_id)
VALUES
    (1, 1),
    (2, 2);

INSERT INTO readers (name, email, phone, registered_at)
VALUES
    ('Иван Иванов', 'ivan@example.com', '89990000001', CURRENT_TIMESTAMP),
    ('Пётр Петров', 'petr@example.com', '89990000002', CURRENT_TIMESTAMP);

INSERT INTO loans (reader_id, book_id, issue_date, due_date, return_date)
VALUES
    (1, 1, CURRENT_DATE, CURRENT_DATE + INTERVAL '14 days', NULL),
    (2, 2, CURRENT_DATE, CURRENT_DATE - INTERVAL '1 day', NULL);
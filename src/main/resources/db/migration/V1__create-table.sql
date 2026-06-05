-- 1. Таблица пользователей (создаем первой, т.к. readers ссылается на неё)
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(50) UNIQUE  NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    email      VARCHAR(100) UNIQUE NOT NULL,
    role       VARCHAR(20)         NOT NULL CHECK (role IN ('LIBRARIAN', 'READER')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Таблица читателей
CREATE TABLE readers
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(50)         NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    phone         VARCHAR(50) UNIQUE,
    registered_at TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id       INTEGER UNIQUE REFERENCES users (id) ON DELETE CASCADE
);

-- 3. Таблица книг
CREATE TABLE books
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(256)       NOT NULL,
    genre            VARCHAR(50)        NOT NULL,
    isbn             VARCHAR(20) UNIQUE NOT NULL,
    description      TEXT,
    publish_year     INTEGER,
    total_copies     INTEGER   DEFAULT 0,
    available_copies INTEGER   DEFAULT 0,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Таблица авторов
CREATE TABLE authors
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    birth_date DATE
);

-- 5. Связь многие-ко-многим: Авторы и Книги
CREATE TABLE author_book_references
(
    id        SERIAL PRIMARY KEY,
    author_id INTEGER NOT NULL REFERENCES authors (id) ON DELETE CASCADE,
    book_id   INTEGER NOT NULL REFERENCES books (id) ON DELETE CASCADE,
    -- Запрещаем дублирование одной и той же связи автора и книги
    UNIQUE (author_id, book_id)
);

-- 6. Таблица выдач книг (займы)
CREATE TABLE loans
(
    id          SERIAL PRIMARY KEY,
    reader_id   INTEGER NOT NULL REFERENCES readers (id) ON DELETE CASCADE,
    book_id     INTEGER NOT NULL REFERENCES books (id) ON DELETE CASCADE,
    issue_date  DATE        DEFAULT CURRENT_DATE,
    due_date    DATE        DEFAULT CURRENT_DATE,
    return_date DATE,
    -- Добавил проверку статусов для целостности данных
    status      VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'RETURNED', 'OVERDUE', 'LOST'))
);

-- === ИНДЕКСЫ (Рекомендуется для производительности) ===
-- Ускоряет поиск книги по ISBN
CREATE INDEX idx_books_isbn ON books (isbn);
-- Ускоряет поиск пользователя при логине
CREATE INDEX idx_users_username ON users (username);
-- Ускоряет поиск всех займов конкретного читателя
CREATE INDEX idx_loans_reader_id ON loans (reader_id);
-- Ускоряет поиск всех займов конкретной книги
CREATE INDEX idx_loans_book_id ON loans (book_id);
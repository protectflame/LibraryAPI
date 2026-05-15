CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    description TEXT,
    publish_year INTEGER,
    total_copies INTEGER DEFAULT 0,
    available_copies INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE authors (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE
);

CREATE TABLE author_book_references (
    id SERIAL PRIMARY KEY,
    author_id INTEGER NOT NULL REFERENCES authors(id),
    book_id INTEGER NOT NULL REFERENCES books(id)
);

CREATE TABLE readers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(50) UNIQUE,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER UNIQUE
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('LIBRARIAN', 'READER')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE readers
    ADD CONSTRAINT fk_reader_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

CREATE TABLE loans (
    id SERIAL PRIMARY KEY,
    reader_id INTEGER NOT NULL REFERENCES readers(id),
    book_id INTEGER NOT NULL REFERENCES books(id),
    issue_date DATE DEFAULT CURRENT_DATE,
    due_date DATE DEFAULT CURRENT_DATE,
    return_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE'
);
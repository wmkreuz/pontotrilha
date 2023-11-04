-- V1__create_books_table.sql

-- Crie a tabela "books"
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    author VARCHAR(255) NOT NULL,
    launch_date DATE,
    price NUMERIC(10, 2),
    title VARCHAR(255) NOT NULL
);
-- V1__create_person_table.sql

-- Crie a tabela "person"
CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    address TEXT,
    gender VARCHAR(10)
);
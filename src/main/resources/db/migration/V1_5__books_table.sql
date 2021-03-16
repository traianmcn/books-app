CREATE TABLE IF NOT EXISTS books(
id             SERIAL              PRIMARY KEY,
name           VARCHAR(255)        NOT NULL,
author         VARCHAR(255)        NOT NULL,
price          DECIMAL             NOT NULL,
created_at     TIMESTAMP           DEFAULT current_timestamp,
category_id    INT8                REFERENCES categories(id)
);
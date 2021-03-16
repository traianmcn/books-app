CREATE TABLE IF NOT EXISTS categories(
id             SERIAL              PRIMARY KEY,
name           VARCHAR(255)        NOT NULL,
description    VARCHAR(255)        NOT NULL,
created_at     TIMESTAMP           DEFAULT current_timestamp,
seller_id      INT8                REFERENCES sellers(id)
);
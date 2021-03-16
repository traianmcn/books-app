CREATE TABLE IF NOT EXISTS sellers(
id             SERIAL              PRIMARY KEY,
name           VARCHAR(255)        NOT NULL,
phone          VARCHAR(255)        NOT NULL,
address        VARCHAR(255)        NOT NULL,
created_at     TIMESTAMP           DEFAULT current_timestamp,
user_id        INT8                REFERENCES users(id)
);
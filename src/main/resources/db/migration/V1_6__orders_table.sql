CREATE TABLE IF NOT EXISTS orders(
id                   SERIAL              PRIMARY KEY,
order_info           VARCHAR(255)        NOT NULL,
order_value          DECIMAL             NOT NULL,
customer_id          INT8                REFERENCES customers(id),
content              INT8                REFERENCES books(id),
created_at           TIMESTAMP           DEFAULT current_timestamp
);
CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    amount_paid BIGINT,
    email VARCHAR(255),
    card_name VARCHAR(255),
    payment_date TIMESTAMP,
    stripe_customer_id VARCHAR(255),
    purchased_by_user_id BIGINT,
    FOREIGN KEY (purchased_by_user_id) REFERENCES users(id)
);
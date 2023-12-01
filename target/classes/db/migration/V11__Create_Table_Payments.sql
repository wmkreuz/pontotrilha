CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    amount_paid BIGINT,
    payment_date TIMESTAMP,
    payment_id_stripe VARCHAR(255),
    purchased_by_user_id BIGINT,
    CONSTRAINT fk_purchased_by_user_id FOREIGN KEY (purchased_by_user_id) REFERENCES users(id)
);
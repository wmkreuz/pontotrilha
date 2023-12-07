CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    event BIGINT,
    purchased_by_user BIGINT,
    url_payment VARCHAR(255) NOT NULL,
    FOREIGN KEY (event) REFERENCES events(id),
    FOREIGN KEY (purchased_by_user) REFERENCES users(id)
);
CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    event BIGINT,
    purchased_by_user BIGINT,
    FOREIGN KEY (event) REFERENCES events(id),
    FOREIGN KEY (purchased_by_user) REFERENCES users(id)
);
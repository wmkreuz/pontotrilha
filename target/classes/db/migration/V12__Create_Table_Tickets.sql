CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    event_id BIGINT,
    purchased_by_user_id BIGINT,
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (purchased_by_user_id) REFERENCES users(id)
);
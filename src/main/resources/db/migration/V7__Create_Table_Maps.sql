CREATE TABLE IF NOT EXISTS maps (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    latitude VARCHAR(255) NOT NULL,
    longitude VARCHAR(255) NOT NULL,
    id_google VARCHAR(255) NOT NULL
);
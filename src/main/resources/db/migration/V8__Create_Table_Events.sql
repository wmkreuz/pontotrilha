CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    location_name VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    number VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip_code VARCHAR(255) NOT NULL,
    complement VARCHAR(255),
    event_name VARCHAR(255) NOT NULL,
    description VARCHAR(4000) NOT NULL,
    start_date DATE NOT NULL,
    start_date_time TIME NOT NULL,
    end_date DATE NOT NULL,
    end_date_time TIME NOT NULL,
    ticket_title VARCHAR(255) NOT NULL,
    quantity BIGINT NOT NULL,
    ticket_price DOUBLE PRECISION NOT NULL,
    ticket_price_stripe VARCHAR(255) NOT NULL,
    start_of_sales DATE NOT NULL,
    start_of_sales_time TIME NOT NULL,
    end_of_sales DATE NOT NULL,
    end_of_sales_time TIME NOT NULL,
    min_purchase_quantity BIGINT NOT NULL,
    max_purchase_quantity BIGINT NOT NULL,
    event_status BIGINT NOT NULL,
    created_by_user_id BIGINT REFERENCES users(id),
    map_id BIGINT REFERENCES maps(id),
    img text,
    modality VARCHAR(255) NOT NULL,
    difficulty VARCHAR(255) NOT NULL, 
    CONSTRAINT fk_created_by_user FOREIGN KEY (created_by_user_id) REFERENCES users(id),
    CONSTRAINT fk_map FOREIGN KEY (map_id) REFERENCES maps(id)
);
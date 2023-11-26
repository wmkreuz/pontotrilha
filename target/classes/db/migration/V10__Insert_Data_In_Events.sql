INSERT INTO events (
    location_name, street, neighborhood, number, city, state, zip_code, complement,
    event_name, description, start_date, end_date, ticket_title, quantity, ticket_price,
    start_of_sales, end_of_sales, min_purchase_quantity, max_purchase_quantity,
    event_status, created_by_user_id, map_id
) VALUES (
    'Exemplo Location', 'Exemplo Street', 'Exemplo Neighborhood', '123', 'Exemplo City',
    'Exemplo State', '12345-678', 'Exemplo Complement', 'Exemplo Event', 'Exemplo Description',
    '2023-01-01', '2023-01-10', 'Exemplo Ticket', 100, 50.0, '2022-12-01', '2023-01-01',
    1, 10, 1, 1, 1
);
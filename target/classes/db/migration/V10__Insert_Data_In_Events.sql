INSERT INTO events (location_name, street, neighborhood, number, city, state, zip_code, complement,
    event_name, description, start_date, start_date_time, end_date, end_date_time,
    ticket_title, quantity, ticket_price, ticket_price_stripe, start_of_sales, start_of_sales_time,
    end_of_sales, end_of_sales_time, min_purchase_quantity, max_purchase_quantity, event_status,
    created_by_user_id, map_id)
VALUES
('Local A', 'Rua 1', 'Bairro A', '123', 'Cidade A', 'Estado A', '12345-678', 'Complemento A',
 'Evento A', 'Descrição do Evento A', '2023-12-01', '12:00:00', '2023-12-02', '18:00:00',
 'Ingresso A', 100, 50.0, '50.00', '2023-11-01', '10:00:00', '2023-11-30', '23:59:59', 1, 10, 100, 1, 1),
('Local B', 'Rua 2', 'Bairro B', '456', 'Cidade B', 'Estado B', '56789-012', 'Complemento B',
 'Evento B', 'Descrição do Evento B', '2023-12-10', '15:30:00', '2023-12-12', '22:30:00',
 'Ingresso B', 150, 75.0, '75.00', '2023-11-15', '09:00:00', '2023-11-30', '23:59:59', 1, 10, 150, 1, 2);
-- Exemplo de dados de amostra, ajuste conforme necessário
INSERT INTO events (
    location_name, street, neighborhood, city, state, zip_code,
    event_name, description, start_date, end_date, ticket_title,
    quantity, ticket_price, start_of_sales, end_of_sales,
    min_purchase_quantity, max_purchase_quantity, event_status,
    created_by_user_id, map_id
) VALUES (
    'Local A', 'Rua A', 'Bairro A', 'Cidade A', 'Estado A', 'CEP A',
    'Evento A', 'Descrição do Evento A', '2023-01-01', '2023-01-10', 'Ingresso A',
    100, 50.0, '2022-12-01', '2022-12-31',
    1, 10, 1, 1, 1
);

-- Adicione mais linhas conforme necessário

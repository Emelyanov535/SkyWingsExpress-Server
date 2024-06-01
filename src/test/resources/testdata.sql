-- Заполнение направлений для тестов
INSERT INTO t_route (id, origin, destination, distance) VALUES (1, 'CityA', 'CityB', 150.0);
INSERT INTO t_route (id, origin, destination, distance) VALUES (2, 'CityC', 'CityD', 200.0);
INSERT INTO t_route (id, origin, destination, distance) VALUES (3, 'CityE', 'CityF', 300.0);
INSERT INTO t_route (id, origin, destination, distance) VALUES (4, 'CityG', 'CityH', 250.0);
INSERT INTO t_route (id, origin, destination, distance) VALUES (5, 'CityI', 'CityJ', 180.0);
-- Заполнение ролей
INSERT INTO t_role (id, name) VALUES (1, 'USER');
INSERT INTO t_role (id, name) VALUES (2, 'OPERATOR');
INSERT INTO t_role (id, name) VALUES (3, 'MODERATOR');
-- Заполнение пользователей
INSERT INTO t_user (id, email, password, name, surname, send_notifications, two_factor)
VALUES
    (1, 'john.doe@example.com', 'password1', 'John', 'Doe', true, false),
    (2, 'jane.smith@example.com', 'password2', 'Jane', 'Smith', true, true),
    (3, 'alice.jones@example.com', 'password3', 'Alice', 'Jones', true, false),
    (4, 'bob.brown@example.com', 'password4', 'Bob', 'Brown', true, true),
    (5, 'carol.white@example.com', 'password5', 'Carol', 'White', false, false);
-- Заполнение рейсов
INSERT INTO t_flight (id, flight_number, route_id, departure_time, arrival_time, total_seats, available_seats, ticket_price, discount_percentage)
VALUES (1, 'SWE1', 1, '2024-06-15 09:00:00', '2024-06-15 12:00:00', 5, 150, 250.00, 0.0)
-- Заполнение билетов
    INSERT INTO t_ticket (id, ticket_number, flight_id, owner_id, final_price)
VALUES
    (1, 'SWE1-1', 1, NULL, NULL),
    (2, 'SWE1-2', 1, NULL, NULL),
    (3, 'SWE1-3', 1, NULL, NULL),
    (4, 'SWE1-4', 1, NULL, NULL),
    (5, 'SWE1-5', 1, NULL, NULL);
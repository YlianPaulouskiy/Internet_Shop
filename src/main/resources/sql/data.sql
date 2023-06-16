INSERT INTO users(name, last_name, email, password, phone)
VALUES ('Maxim', 'Yarosh', 'maxik123@gmail.com', 'qwerty123', '+47512312312'),
       ('Kate', 'Yarmoshyk', 'katrina@gmail.com', 'qwerty123', '+475123123122'),
       ('Vladik', 'Gorodcev', 'uladzik@gmail.com', 'qwerty123', '+475123121242');

INSERT INTO products(name, description, price, user_id)
VALUES ('processor', 'computer peripherals', 55.0, 1),
       ('computer', 'computer peripherals', 800, 1),
       ('sofa', 'furniture', 300, 2),
       ('carpet', 'furniture', 150, 2),
       ('nissan 350Z', 'car', 10000, 3),
       ('nissan 370Z', 'car', 12000, 3),
       ('SSD', 'computer peripherals', 200, 1),
       ('HD', 'computer peripherals', 100, 1),
       ('table', 'furniture', 60, 2),
       ('chair', 'furniture', 80, 2),
       ('kia sportage', 'car', 10000, 3),
       ('rino logan', 'car', 7000, 3),
       ('monitor', 'computer peripherals', 200, 1),
       ('keyboard', 'computer peripherals', 95, 1),
       ('bookshelf', 'furniture', 222, 2),
       ('door', 'furniture', 185, 2),
       ('bmw x5', 'car', 32000, 3),
       ('bmw x6', 'car', 35000, 3);


INSERT INTO addresses(city, street, house, flat, user_id)
VALUES ('Moscow', 'Sovetskay', '19B', 4, 1),
       ('SPB', 'Central', '6A', 18, 2),
       ('Minsk', 'Dryjnaya', '3', null, 3);


INSERT INTO orders(registration_time, user_id)
VALUES (now(), 1),
       (now(), 2),
       (now(), 3);

INSERT INTO order_product(order_id, product_id)
VALUES (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (2, 1),
       (2, 2),
       (2, 7),
       (2, 8),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4);

INSERT INTO USERS (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@mail.ru', '{noop}password2'),
       ('User3', 'user3@mail.ru', '{noop}password3'),
       ('User4', 'user4@mail.ru', '{noop}password4');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('ADMIN', 1),
       ('USER',1 ),
       ('USER', 2),
       ('USER',3 ),
       ('USER', 4);

INSERT INTO restaurant (name)
VALUES ('Khachapuri and Wine'),
       ('Munhell'),
       ('Kwakinn');

INSERT INTO menu_item (restaurant_id, available, name, price)
VALUES (1, current_date, 'salad', 25000),
       (1, current_date, 'khachapuri', 28000),
       (1, current_date, 'lobio', 20000),
       (1, '2021-03-24', 'phali', 5000),
       (1, '2021-03-24', 'wine', 26000),
       (2, '2021-03-24', 'salad', 23050),
       (2, '2021-03-24', 'ribs BBQ', 55050),
       (2, current_date, 'steak', 75050),
       (2, '2021-03-24', 'shashlik', 45000),
       (2, '2021-03-24', 'beer', 29000),
       (3, '2021-03-24', 'salad', 20000),
       (3, '2021-03-24', 'pork knuckle', 65000),
       (3, current_date, 'chicken', 45000),
       (3, '2021-03-24', 'rib eye steak', 95000),
       (3, '2021-03-24', 'beer', 35000);

INSERT INTO vote (user_id, vote_date, restaurant_id)
VALUES (1, current_date, 1),
       (2, '2021-04-5', 2),
       (3, current_date, 2),
       (4, current_date, 3),
       (1, '2021-04-6', 2),
       (2, '2021-04-6', 3);
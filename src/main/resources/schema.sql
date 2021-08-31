DROP TABLE vote ;
DROP TABLE menu_item ;
DROP TABLE user_roles ;
DROP TABLE users ;
DROP TABLE restaurant ;


CREATE TABLE users
(
    id               INTEGER                 NOT NULL AUTO_INCREMENT,
    name             VARCHAR                 NOT NULL,
    email            VARCHAR                 NOT NULL,
    password         VARCHAR                 NOT NULL,
    registered       TIMESTAMP DEFAULT now() NOT NULL,
    enabled          BOOL      DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER   DEFAULT 2000  NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurant (name);

CREATE TABLE menu_item
(
    id            INTEGER      NOT NULL AUTO_INCREMENT,
    restaurant_id INTEGER      NOT NULL,
    date          DATE         NOT NULL,
    name          VARCHAR(255) NOT NULL,
    price         INTEGER      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menu_item_unique_restaurant_id_date_name_idx ON menu_item (restaurant_id, date, name);

CREATE TABLE vote
(
    id            INTEGER NOT NULL AUTO_INCREMENT,
    user_id       INTEGER NOT NULL,
    date          DATE    NOT NULL,
    restaurant_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_date_user_id_idx ON vote (date, user_id);
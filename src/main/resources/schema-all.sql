DROP TABLE person IF EXISTS;

CREATE TABLE person  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    address VARCHAR(100)
);

DROP TABLE User IF EXISTS;
CREATE TABLE User (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(20),
    type VARCHAR(20)
);

INSERT INTO User values (1, 'test', 'ADMIN');
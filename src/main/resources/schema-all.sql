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

CREATE TABLE TrainLine (
    lineName VARCHAR(20),
    startPositionName VARCHAR(100),
    destinationName VARCHAR(100),
    price NUMERIC(10,2),
    startTime VARCHAR(20),
    arriveTime VARCHAR(20)
);

INSERT INTO TrainLine values('D35', 'Dalian', 'Panjin', 91, '10:50', '12:30');


CREATE TABLE TicketBalance (
    lineName VARCHAR(20),
    seatABalance int,
    seatBBalance int,
    seatCBalance int,
    seatEBalance int,
    seatFBalance int,
    day VARCHAR(20)
);
INSERT INTO TicketBalance values('D35', 100, 200, 50, 35, 40, '2020-01-19');

CREATE TABLE TicketRecord (
    id int,
    requsetId VARCHAR(50),
    buyer VARCHAR(20),
    passenger VARCHAR(20),
    startPosition VARCHAR(100),
    destination VARCHAR(100),
    seatNum VARCHAR(10),
    coachNum int,
    creatTime timestamp,
    status VARCHAR(10)
);
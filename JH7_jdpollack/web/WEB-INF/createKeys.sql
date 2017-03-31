CREATE TABLE KeyCollection (
    building VARCHAR(32),
    room VARCHAR(10),
    description VARCHAR(50),
    keyNumber VARCHAR(10),
    id int Primary Key Auto_Increment
);
INSERT INTO KeyCollection VALUES('Building1', '1000A', 'public space', '1GM-23', null);
INSERT INTO KeyCollection VALUES('Building1', '2000B', 'office', '2AA-4', null);
INSERT INTO KeyCollection VALUES('Building2', '1010A', 'small room', '2AA-5', null);
INSERT INTO KeyCollection VALUES('Building2', '2020B', 'large room', '1GMA-23', null);
INSERT INTO KeyCollection VALUES('Building2', '3000A', 'public space', '1GG-23', null);
INSERT INTO KeyCollection VALUES('Building2', '4000B', 'office', '2AB-4', null);
INSERT INTO KeyCollection VALUES('Building3', '1111A', 'small room', '2ABC-5', null);
INSERT INTO KeyCollection VALUES('Building3', '2222B', 'large room', '1GGM-23', null);

CREATE TABLE RequestData (
    name VARCHAR(50) DEFAULT 'Bob Default',
    keyNumber VARCHAR(10) NOT NULL,
    id int PRIMARY KEY Auto_Increment
);

INSERT INTO RequestData VALUES('Bob Test', '1GM-23', null);
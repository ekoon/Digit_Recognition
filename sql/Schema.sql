BEGIN;
DROP TABLE IF EXISTS Feature CASCADE;
DROP TABLE IF EXISTS Sample CASCADE;
DROP TABLE IF EXISTS Vowel CASCADE;
DROP TABLE IF EXISTS Speaker CASCADE;
DROP TABLE IF EXISTS Sex CASCADE;

CREATE TABLE Sex (
    id     integer,
    string varchar(16),

    PRIMARY KEY (id)
);

CREATE TABLE Class (
    id    integer,
    vowel varchar (3),

    PRIMARY KEY (id)
);

CREATE TABLE Speaker (
    id         integer,
    first_name varchar(36),
    sex        integer references Sex(id),

    PRIMARY KEY (id)
);

CREATE TABLE Sample (
    id      integer,
    speaker integer references Speaker(id),
    class   integer references Class(id),
    train   boolean,

    PRIMARY KEY (id)
);

CREATE TABLE Feature (
    sample  integer references Sample(id),
    feature integer,
    number  real,

    PRIMARY KEY (sample, feature)
);

CREATE TABLE Weight (
    speaker integer references Speaker(id),
    feature integer,
    weight  numeric,

    PRIMARY KEY (sample, feature),
    FOREIGN KEY (sample, feature) references Feature (sample, feature)
);


COMMIT;
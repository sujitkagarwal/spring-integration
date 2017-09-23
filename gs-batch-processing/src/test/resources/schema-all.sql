DROP TABLE people IF EXISTS;

CREATE TABLE people  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
   first_name VARCHAR(20),
   last_name VARCHAR(20)
);

DROP TABLE File IF EXISTS;

CREATE TABLE File(id INTEGER  IDENTITY PRIMARY KEY, name VARCHAR(255) NOT NULL UNIQUE, read_count   integer, write_count  integer, skip_count  integer, commit_count integer,  status varchar(10));



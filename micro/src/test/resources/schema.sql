DROP TABLE ACCOUNT
IF EXISTS;
CREATE TABLE ACCOUNT (
  id             INTEGER IDENTITY,
  first_name     VARCHAR(40),
  last_name      VARCHAR(40),
  account_number VARCHAR(50) NOT NULL,
  active         VARCHAR(3)  NOT NULL,
  CONSTRAINT PK_ACCOUNT PRIMARY KEY (id, account_number)
);
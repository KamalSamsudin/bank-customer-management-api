DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer (
id INT NOT NULL AUTO_INCREMENT,
name varchar(255) NOT NULL,
address varchar(255),
id_number varchar(255) NOT NULL UNIQUE,
email varchar(255),
contact_number varchar(255),
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS Account;
CREATE TABLE IF NOT EXISTS Account (
id INT NOT NULL AUTO_INCREMENT,
customer_id INT NOT NULL,
account_number varchar(255) NOT NULL UNIQUE,
account_type varchar(255) NOT NULL,
status varchar(255) NOT NULL,
PRIMARY KEY (customer_id, account_number),
FOREIGN KEY (customer_id) REFERENCES Customer(id)

);

DROP TABLE IF EXISTS Balance;
CREATE TABLE IF NOT EXISTS Balance (
id INT NOT NULL,
balance DECIMAL(19,2) NOT NULL,
currency varchar(255) NOT NULL,
updated_at varchar(255) NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (id) REFERENCES Account(account_number)
);
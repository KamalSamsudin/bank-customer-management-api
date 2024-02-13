INSERT INTO Customer (name, address, id_number, email, contact_number)
VALUES ('Jon Doe', '123 Main Street, New York, NY 10030', '1234567890', 'jon@gmail.com', '1234567890');

INSERT INTO Account (customer_id, account_number, account_type, status)
VALUES (1, '1122334455', 'SAVINGS', 'ACTIVE');

INSERT INTO Balance (id, balance, currency, updated_at)
VALUES (1122334455, 1000.00, 'MYR', '2024-02-14T05:47:26.853Z');
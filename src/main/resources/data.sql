-- data.sql
CREATE TABLE Customer (
                          user_id INT AUTO_INCREMENT PRIMARY KEY,
                          username VARCHAR(50) NOT NULL,
                          wallet_balance DECIMAL(18, 8) DEFAULT 50000.00000000
);
INSERT INTO Customer (username, wallet_balance) VALUES ('user1', 50000.00000000);
INSERT INTO Customer (username,  wallet_balance) VALUES ('user2',  50000.00000000);
-- Add more INSERT statements as needed



# Crypto Trading System

Crypto Trading System is a Java Spring Boot application for cryptocurrency trading. This project provides APIs for trading, retrieving wallet balances, and viewing trading history for supported cryptocurrency pairs.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Adding New Users](#adding-new-users)


## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 11 or higher
- Apache Maven

### Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/Wendy12345aa/CryptoTradingSystem.git
   ```

2. Build the project using Maven:

   ```shell
   mvn clean install
   ```

3. Run the application:

   ```shell
   java -jar target/crypto-trading-system-1.0.0.jar
   ```

## Usage

After starting the application, you can access the API endpoints to perform cryptocurrency trading, retrieve wallet balances, and view trading history.


## API Documentation

Explore our API documentation using Swagger UI:

[Swagger UI](http://localhost:8080/swagger-ui/index.html#/)

- Use this interactive documentation to understand and test our API endpoints.

## API Endpoints

- **Retrieve Latest Aggregated Price**
    - Endpoint: `/api/price/latest`
    - Description: Retrieve the latest aggregated bid and ask prices for supported cryptocurrency pairs.

- **Execute Trade**
    - Endpoint: `/api/trade`
    - Description: Execute a trade based on the latest aggregated prices.

- **Retrieve Wallet Balances**
    - Endpoint: `/api/wallet/balances`
    - Description: Retrieve the user's cryptocurrency wallet balances.

- **Retrieve Trading History**
    - Endpoint: `/api/trade/history`
    - Description: Retrieve the user's trading history.

For more details on API usage, refer to the API documentation.

## Configuration

The application can be configured by editing the `application.properties` file. You can specify database settings, server port, and other configuration options there.

## Adding New Users

1. Open the `data.sql` file in the project.

2. Add the following SQL statement to insert a new user into the `Customer` table. Replace `new_user_name`, `new_wallet_balance`, and other values with the desired user details:

   ```sql
   INSERT INTO Customer (username, wallet_balance) VALUES ('new_user_name', new_wallet_balance);
For example:

sql
Copy code
INSERT INTO Customer (username, wallet_balance) VALUES ('alice', 50000.00000000);
Save the data.sql file.

When you run the application, the new user will be added to the system with the specified username and wallet balance.


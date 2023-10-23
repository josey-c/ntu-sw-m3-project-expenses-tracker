-- Create a table to store users
CREATE TABLE users (
    userid serial PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL
);

-- Create a table to store categories
CREATE TABLE category (
    categoryId serial PRIMARY KEY,
    categoryNum INT NOT NULL,
    userid INT REFERENCES users(id),
    category_name VARCHAR(255) NOT NULL
);

-- Create a table to store wallets
CREATE TABLE wallet (
    walletId serial PRIMARY KEY,
    user_id INT REFERENCES users(id),
    wallet_name VARCHAR(255) NOT NULL
);

-- Create a table to store expenses
CREATE TABLE expense (
    id serial PRIMARY KEY,
    category_id INT REFERENCES category(id),
    wallet_id INT REFERENCES wallet(id),
    name VARCHAR(255) NOT NULL,
    amount NUMERIC NOT NULL,
    date DATE
);

create table accounts (
    id IDENTITY not null primary key,
    balance DECIMAL(20, 2) not null,
    currency char(3) not null
);

/* Схема таблицы orders к заданию
0. Что такое интеграционное тестирование. [#6875] */
CREATE TABLE orders (
    id serial,
    name VARCHAR(50),
    description VARCHAR(50),
    created timestamp,
    PRIMARY KEY (id)
);
/*схема таблицы Марки автомобилей модель данных CarMark*/
create table if not exists c_mark(
    id serial primary key,
    mark varchar(200)
);
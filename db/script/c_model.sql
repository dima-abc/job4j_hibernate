/*схема таблицы Моделей автомобилей, модель данных CarModel*/
create table if not exists c_model(
 id serial primary key,
 model varchar(200)
);
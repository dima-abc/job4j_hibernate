package ru.job4j.hibernate.integration;

import java.util.Collection;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.2. Mapping
 * 3.3.4. Интеграционное тестирование
 * 0. Что такое интеграционное тестирование. [#6875]
 * IStore интерфейс описывает поведение хранилища.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 29.05.2022
 */
public interface IStore<T> {
    T save(T type);

    T update(T type);

    Collection<T> findByName(String name);

    T findById(int id);

    Collection<T> findAll();
}

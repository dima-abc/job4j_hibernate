package ru.job4j.hibernate.integration;

import org.hibernate.type.LocalDateTimeType;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.2. Mapping
 * 3.3.4. Интеграционное тестирование
 * 0. Что такое интеграционное тестирование. [#6875]
 * Order модель данных заказ.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 29.05.2022
 */
public class Order {
    private int id;
    private String name;
    private String description;
    private LocalDateTime created = LocalDateTime.now().withNano(0);

    public static Order of(String name, String description) {
        Order order = new Order();
        order.name = name;
        order.description = description;
        return order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", name='" + name
                + '\'' + ", description='" + description + '\''
                + ", created=" + created + '}';
    }
}

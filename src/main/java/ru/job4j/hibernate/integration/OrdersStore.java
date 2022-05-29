package ru.job4j.hibernate.integration;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.2. Mapping
 * 3.3.4. Интеграционное тестирование
 * 0. Что такое интеграционное тестирование. [#6875]
 * OrdersStore хратилише заказов в бае данных(стек JDBC)
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 29.05.2022
 */
public class OrdersStore implements IStore<Order> {
    private final BasicDataSource pool;

    public OrdersStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Сохранить Order
     *
     * @param order Order
     * @return Order or null.
     */
    @Override
    public Order save(Order order) {
        String sql = "INSERT INTO orders(name, description, created) VALUES (?, ?, ?)";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getName());
            ps.setString(2, order.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(order.getCreated()));
            ps.execute();
            ResultSet id = ps.getGeneratedKeys();
            if (id.next()) {
                order.setId(id.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    /**
     * Обнеовить Order
     *
     * @param order Order
     * @return Order or null
     */
    @Override
    public Order update(Order order) {
        Order result = null;
        String sql = "UPDATE orders SET name=?, description=?, created=? WHERE id=?";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, order.getName());
            ps.setString(2, order.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(order.getCreated()));
            ps.setInt(4, order.getId());
            if (ps.executeUpdate() > 0) {
                result = order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Посик Order по имени.
     *
     * @param name String
     * @return Collection
     */
    @Override
    public Collection<Order> findByName(String name) {
        Collection<Order> result = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE name = ?";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getOrder(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Посик Order по id
     *
     * @param id int
     * @return Order or null
     */
    @Override
    public Order findById(int id) {
        Order result = null;
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result = getOrder(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Поиск всех Order.
     *
     * @return Collection
     */
    @Override
    public Collection<Order> findAll() {
        Collection<Order> result = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getOrder(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Возврощает Order из ResultSet.
     *
     * @param resultSet ResultSet
     * @return Order
     * @throws SQLException Exception
     */
    private Order getOrder(ResultSet resultSet) throws SQLException {
        Order order = Order.of(
                resultSet.getString("name"),
                resultSet.getString("description")
        );
        order.setId(resultSet.getInt("id"));
        order.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        return order;
    }
}

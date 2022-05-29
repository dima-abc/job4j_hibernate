package ru.job4j.hibernate.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * 3. Мидл
 * 3.3. Hibernate
 * 3.3.2. Mapping
 * 3.3.4. Интеграционное тестирование
 * 0. Что такое интеграционное тестирование. [#6875]
 * OrdersStoreTest тестирование OrderStore
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 29.05.2022
 */
public class OrdersStoreTest {
    private static final String DRIVER = "org.hsqldb.jdbcDriver";
    private static final String URL = "jdbc:hsqldb:mem:tests;sql.syntax_pgs=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final int MAX_TOTAL = 2;
    private static final String FILE_SCRIPT = "./db/script/orders.sql";
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() {
        pool.setDriverClassName(DRIVER);
        pool.setUrl(URL);
        pool.setUsername(USER);
        pool.setPassword(PASSWORD);
        pool.setMaxTotal(MAX_TOTAL);
        try {
            String sqlTable = Files.readString(Path.of(FILE_SCRIPT));
            try (Connection connection = pool.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sqlTable)) {
                ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void clear() {
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement("drop table orders")) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndFindBaId() {
        OrdersStore store = new OrdersStore(pool);
        Order result = store.save(Order.of("order", "description"));
        Order expected = store.findById(result.getId());
        assertThat(expected, is(result));
    }

    @Test
    public void whenUpdateOrderAndFindId() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("order", "description");
        store.save(order);
        order.setName("new Name order");
        order.setCreated(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        Order result = store.update(order);
        assertThat(order, is(result));
    }

    @Test
    public void whenFindByNameThenListTwoOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("order", "description");
        store.save(order);
        Order order1 = Order.of("order", "description");
        store.save(order1);
        List<Order> expected = List.of(order, order1);
        List<Order> result = (List<Order>) store.findByName(order.getName());
        assertThat(expected, is(result));
    }

    @Test
    public void whenFindById() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("order", "description");
        store.save(order);
        Order result = store.findById(order.getId());
        assertThat(order, is(result));
    }

    @Test
    public void whenFindAllOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("order", "description");
        Order order1 = Order.of("order1", "description1");
        store.save(order);
        store.save(order1);
        List<Order> expected = List.of(order, order1);
        List<Order> result = (List<Order>) store.findAll();
        assertThat(expected, is(result));
    }
}
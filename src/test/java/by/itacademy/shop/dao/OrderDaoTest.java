package by.itacademy.shop.dao;

import by.itacademy.shop.entity.Address;
import by.itacademy.shop.entity.Order;
import by.itacademy.shop.entity.Product;
import by.itacademy.shop.entity.User;
import by.itacademy.shop.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoTest {

    private final OrderDao orderDao = OrderDao.getInstance();
    private static final Long FIND_ID = 1L;
    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private static final Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private Order createOrder;

    @BeforeEach
    public void setUp() throws Exception {
        TestDBUtils.initDb();
        createOrder = Order.builder()
                .registrationTime(LocalDateTime.now())
                .products(List.of(Product.builder().id(1L).build(), Product.builder().id(2L).build() ))
                .user(User.builder().id(1L).build())
                .build();
    }

    @AfterAll
    static void tearDown() {
        try {
            TestDBUtils.getConnection().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void findAllByUserIdTest() {
        var orders = orderDao.findAllByUserId(FIND_ID);
        assertFalse(orders.isEmpty());
        assertThat(orders).hasSize(DEFAULT_SIZE);
        var productsName = orders.stream().flatMap(order -> order.getProducts().stream())
                .map(Product::getName).collect(Collectors.toList());
        assertThat(productsName).containsExactlyInAnyOrder("sofa", "carpet", "nissan 350Z", "nissan 370Z");
    }

    @Test
    public void saveTest() {
        assertThat(orderDao.findAllByUserId(FIND_ID)).hasSize(DEFAULT_SIZE);
        assertTrue(orderDao.save(createOrder).isPresent());
        assertThat(orderDao.findAllByUserId(FIND_ID)).hasSize(CREATED_SIZE);
    }

    @Test
    public void deleteTest() {
        orderDao.delete(FIND_ID);
        assertThat(orderDao.findAllByUserId(FIND_ID)).hasSize(REMOVED_SIZE);
    }



}

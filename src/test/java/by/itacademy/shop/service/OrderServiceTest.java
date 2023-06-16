package by.itacademy.shop.service;

import by.itacademy.shop.dto.OrderReadDto;
import by.itacademy.shop.dto.ProductReadDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    @BeforeEach
    public void setUp() throws Exception {
        TestDBUtils.initDb();
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
    void should1OrderWhenFindAllOrdersInFirstUser() {
        assertThat(orderService.findAllByUserId(1L)).hasSize(1);
    }


    @Test
    void shouldOptionalIsPresentWhenOrderSave() {
        var order = OrderReadDto.builder()
                .registrationTime(LocalDateTime.now())
                .products(List.of(ProductReadDto.builder().id(1L).build(), ProductReadDto.builder().id(2L).build() ))
                .user(UserReadDto.builder().id(1L).build())
                .build();
        assertTrue(orderService.save(order).isPresent());
    }

    @Test
    void shouldEmptyOrderListBy1UserWhenDeleteHisOrder() {
        orderService.delete(1L);
        assertTrue(orderService.findAllByUserId(1L).isEmpty());
    }

}

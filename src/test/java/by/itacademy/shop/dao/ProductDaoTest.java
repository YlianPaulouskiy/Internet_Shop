package by.itacademy.shop.dao;

import by.itacademy.shop.entity.Product;
import by.itacademy.shop.entity.User;
import by.itacademy.shop.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductDaoTest {

    private final ProductDao productDao = ProductDao.getInstance();
    private static final Long FIND_ID = 1L;
    private static final Integer DEFAULT_SIZE = 18;
    private static final Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private static final Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private Product createProduct;

    @BeforeEach
    public void setUp() throws Exception {
        TestDBUtils.initDb();
        createProduct = Product.builder()
                .name("snowboard")
                .description("sport invent")
                .price(BigDecimal.valueOf(300))
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
    public void findAllTest() {
        List<Product> productList = productDao.findAll();
        assertThat(productList).hasSize(DEFAULT_SIZE);
        List<String> descriptions = productList.stream().map(Product::getDescription).collect(Collectors.toList());
        assertThat(descriptions).containsAll(List.of("computer peripherals", "furniture", "car"));
    }

    @Test
    public void findAllByUserIdTest() {
        List<Product> productList = productDao.findAllByUserId(FIND_ID);
        assertThat(productList).hasSize(6);
        List<String> names = productList.stream().map(Product::getName).collect(Collectors.toList());
        assertThat(names).containsAll(List.of("processor", "computer", "SSD", "HD", "monitor", "keyboard"));
    }

    @Test
    public void findAllByOrderIdTest() {
        List<Product> productList = productDao.findAllByOrderId(FIND_ID);
        assertThat(productList).hasSize(4);
        List<String> names = productList.stream().map(Product::getName).collect(Collectors.toList());
        assertThat(names).containsAll(List.of("sofa", "carpet", "nissan 350Z", "nissan 370Z"));
    }

    @Test
    public void findByIdTest() {
        var productOptional = productDao.findById(1L);
        assertTrue(productOptional.isPresent());
        assertEquals(productOptional.get().getName(), "processor");
        assertEquals(productOptional.get().getDescription(), "computer peripherals");
        assertEquals(productOptional.get().getPrice().doubleValue(), BigDecimal.valueOf(55.0).doubleValue());
    }

    @Test
    public void saveTest() {
        assertThat(productDao.findAll()).hasSize(DEFAULT_SIZE);
        assertTrue(productDao.save(createProduct).isPresent());
        assertThat(productDao.findAll()).hasSize(CREATED_SIZE);
    }

    @Test
    public void deleteTest() {
        assertThat(productDao.findAll()).hasSize(DEFAULT_SIZE);
        assertTrue(productDao.delete(FIND_ID));
        assertThat(productDao.findAll()).hasSize(REMOVED_SIZE);
        assertThat(productDao.findById(FIND_ID)).isEmpty();
    }


}

package by.itacademy.shop.service;

import by.itacademy.shop.dto.ProductReadDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.exception.EntityExistsException;
import by.itacademy.shop.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductServiceTest {

    private final ProductService productService = new ProductService();

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
    void should18ProductsWhenFindAllProducts() {
        assertThat(productService.findAll()).hasSize(18);
    }

    @Test
    void should6ProductsWhenUserIdIs1() {
        assertThat(productService.findAllByUserId(1L)).hasSize(6);
    }

    @Test
    void shouldOptionalIsPresentWhenProductSave() {
        var product = ProductReadDto.builder()
                .name("product")
                .description("description")
                .price(BigDecimal.ZERO)
                .user(UserReadDto.builder().id(1L).build())
                .build();
        assertTrue(productService.save(product).isPresent());
    }

    @Test
    void deleteTest() {
        productService.delete(1L);
        assertThat(productService.findAllByUserId(1L)).hasSize(5);
    }

    @Test
    void shouldExceptionWhenSaveTargetsIsIncorrect() {
        assertThrows(ConstraintViolationException.class, () -> productService.save(ProductReadDto.builder().build()));
        var productReadDto = ProductReadDto.builder()
                .name("computer")
                .price(BigDecimal.ZERO)
                .build();
        assertThrows(EntityExistsException.class, () -> productService.save(productReadDto));
    }
}

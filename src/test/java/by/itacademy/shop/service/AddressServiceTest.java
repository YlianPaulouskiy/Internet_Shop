package by.itacademy.shop.service;

import by.itacademy.shop.dao.UserDao;
import by.itacademy.shop.dto.AddressReadDto;
import by.itacademy.shop.entity.User;
import by.itacademy.shop.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressServiceTest {

    private final AddressService addressService = new AddressService();

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
    void shouldPresentWhenFindByUserId() {
        assertTrue(addressService.findByUserId(1L).isPresent());
    }

    @Test
    void shouldOptionalIsPresentWhenAddressSave() {
        var user = User.builder()
                .name("Andrey")
                .lastName("Nekrashevich")
                .email("axolm@gmail.com")
                .phone("+3754443211232")
                .password("1qaz2wsx3edc")
                .build();
        UserDao.getInstance().save(user).ifPresent(user1 -> user.setId(user1.getId()));
        var address = AddressReadDto.builder()
                .city("City")
                .street("Street")
                .house("112")
                .build();
        assertTrue(addressService.save(user.getId(), address).isPresent());
    }

    @Test
    void shouldTrueWhenAddressUpdate() {
        var address = AddressReadDto.builder()
                .city("City")
                .street("Street")
                .house("112")
                .build();
        assertTrue(addressService.update(1L, address));
    }

    @Test
    void shouldTrueWhenAddressDelete() {
        assertTrue(addressService.delete(1L));
    }

    @Test
    void shouldExceptionWhenSaveTargetsIsIncorrect() {
        assertThrows(ConstraintViolationException.class, () -> addressService.save(1L, AddressReadDto.builder().build()));
    }
    
}

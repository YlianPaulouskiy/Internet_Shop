package by.itacademy.shop.service;

import by.itacademy.shop.dao.UserDao;
import by.itacademy.shop.dto.StringUserDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.exception.EntityExistsException;
import by.itacademy.shop.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private final UserService userService = new UserService();
    private StringUserDto userDto;

    @BeforeEach
    public void setUp() throws Exception {
        TestDBUtils.initDb();
        userDto = StringUserDto.builder()
                .name("name")
                .lastName("lastName")
                .email("example@gmail.com")
                .phone("+77712398221")
                .password("qwerty123")
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
    void shouldPresentUserWhenLogin() {
        assertTrue(userService.login("maxik123@gmail.com", "qwerty123").isPresent());
    }

    @Test
    void shouldPresentWhenCreateUser() {
        assertTrue(userService.create(userDto).isPresent());
    }

    @Test
    void shouldChangeFieldsWhenUpdateUser() {
        var user = UserReadDto.builder()
                .id(1L)
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
        assertTrue(userService.update(user));
        var updatedUserOpt = userService.login(user.getEmail(), user.getPassword());
        assertTrue(updatedUserOpt.isPresent());
        var updatedUser = updatedUserOpt.get();
        assertEquals(updatedUser.getName(), user.getName());
        assertEquals(updatedUser.getLastName(), user.getLastName());
        assertEquals(updatedUser.getPhone(), user.getPhone());
        assertEquals(updatedUser.getEmail(), user.getEmail());
        assertEquals(updatedUser.getPassword(), user.getPassword());
    }

    @Test
    void shouldEmptyFindByIdWhenUserDeleted() {
        userService.delete(1L);
        assertTrue(UserDao.getInstance().findById(1L).isEmpty());
    }

    @Test
    void shouldExceptionWhenSaveTargetsIsIncorrect() {
        assertThrows(ConstraintViolationException.class, () -> userService.create(StringUserDto.builder().build()));
        var stringUser = StringUserDto.builder()
                .name("name")
                .lastName("lastName")
                .email("maxik123@gmail.com")
                .phone("+77712398221")
                .password("qwerty123")
                .build();
        assertThrows(EntityExistsException.class, () -> userService.create(stringUser));
    }
}

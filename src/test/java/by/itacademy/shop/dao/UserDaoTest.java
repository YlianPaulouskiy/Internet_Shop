package by.itacademy.shop.dao;

import by.itacademy.shop.entity.User;
import by.itacademy.shop.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserDaoTest {

    private final UserDao userDao = UserDao.getInstance();
    private static final Long FIND_ID = 1L;
    private static final Integer DEFAULT_SIZE = 3;
    private static final Integer CREATED_SIZE = DEFAULT_SIZE + 1;
    private static final Integer REMOVED_SIZE = DEFAULT_SIZE - 1;
    private User rightUser;
    private User createUser;

    @BeforeEach
    public void setUp() throws Exception {
        TestDBUtils.initDb();
        userDao.findById(FIND_ID).ifPresent(user -> rightUser = user);
        createUser = User.builder()
                .name("Andrey")
                .lastName("Nekrashevich")
                .email("axolm@gmail.com")
                .phone("+3754443211232")
                .password("1qaz2wsx3edc")
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
        List<User> userList = userDao.findAll();
        assertThat(userList).hasSize(DEFAULT_SIZE);
        List<String> emails = userList.stream().map(User::getEmail).collect(Collectors.toList());
        assertThat(emails).containsExactlyInAnyOrder("maxik123@gmail.com", "katrina@gmail.com", "uladzik@gmail.com");
    }

    @Test
    public void findByIdTest() {
        var optionalUser = userDao.findById(1L);
        if (optionalUser.isPresent()) {
            assertEquals(optionalUser.get().getName(), rightUser.getName());
            assertEquals(optionalUser.get().getLastName(), rightUser.getLastName());
            assertEquals(optionalUser.get().getEmail(), rightUser.getEmail());
            assertEquals(optionalUser.get().getPhone(), rightUser.getPhone());
        } else {
            assert false;
        }
    }

    @Test
    public void saveTest() {
        assertThat(userDao.findAll()).hasSize(DEFAULT_SIZE);
        assertTrue(userDao.save(createUser).isPresent());
        assertThat(userDao.findAll()).hasSize(CREATED_SIZE);
        assertThat(userDao.login(createUser.getEmail(), createUser.getPassword())).isPresent();
    }

    @Test
    public void updateTest() {
            rightUser.setEmail("newLogin@gamil.com");
            rightUser.setName("new Name");
            userDao.update(rightUser);
            var updateOptionalUser = userDao.findById(1L);
            updateOptionalUser.ifPresentOrElse(user1 -> {
                assertThat(user1.getEmail()).isEqualTo("newLogin@gamil.com");
                assertThat(user1.getName()).isEqualTo("new Name");
            }, () -> assertThat(false));
    }

    @Test
    public void deleteTest() {
        assertThat(userDao.findAll()).hasSize(DEFAULT_SIZE);
        assertTrue(userDao.delete(FIND_ID));
        assertThat(userDao.findAll()).hasSize(REMOVED_SIZE);
        assertThat(userDao.findById(FIND_ID)).isEmpty();
    }

    @Test
    void loginTest() {
        var optionalUser = userDao.login(rightUser.getEmail(), rightUser.getPassword());
        assertTrue(optionalUser.isPresent());
        assertEquals(optionalUser.get().getId(), rightUser.getId());
        assertEquals(optionalUser.get().getName(), rightUser.getName());
        assertEquals(optionalUser.get().getLastName(), rightUser.getLastName());
        assertEquals(optionalUser.get().getEmail(), rightUser.getEmail());
    }


}

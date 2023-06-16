package by.itacademy.shop.dao;

import by.itacademy.shop.entity.Address;
import by.itacademy.shop.entity.User;
import by.itacademy.shop.utils.TestDBUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressDaoTest {

    private final AddressDao addressDao = AddressDao.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private static final Long FIND_ID = 1L;
    private Address createAddress;
    private Address rightAddress;
    private User createUser;

    @BeforeEach
    public void setUp() throws Exception {
        TestDBUtils.initDb();
        createAddress = Address.builder()
                .city("Lyninets")
                .street("Sovetskay")
                .house("18")
                .flat("67")
                .build();
        createUser = User.builder()
                .name("Andrey")
                .lastName("Nekrashevich")
                .email("axolm@gmail.com")
                .phone("+3754443211232")
                .password("1qaz2wsx3edc")
                .build();
        addressDao.findByUserId(FIND_ID).ifPresent(address -> rightAddress = address);
        userDao.save(createUser).ifPresent(user -> createUser = user);
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
    public void findByUserIdTest() {
        var addressOptional = addressDao.findByUserId(FIND_ID);
        assertTrue(addressOptional.isPresent());
        assertEquals(addressOptional.get().getCity(), "Moscow");
        assertEquals(addressOptional.get().getStreet(), "Sovetskay");
        assertEquals(addressOptional.get().getHouse(), "19B");
        assertEquals(addressOptional.get().getFlat(), "4");
    }

    @Test
    public void saveTest() {
        addressDao.save(createUser.getId(), createAddress);
        assertTrue(addressDao.findByUserId(createUser.getId()).isPresent());
    }

    @Test
    public void updateTest() {
        rightAddress.setCity("MyCity");
        rightAddress.setFlat("333A");
        addressDao.update(FIND_ID, rightAddress);
        var updateOptionalAddress = addressDao.findByUserId(FIND_ID);
        updateOptionalAddress.ifPresentOrElse(address -> {
            assertEquals(address.getCity(), "MyCity");
            assertEquals(address.getFlat(), "333A");
        }, () -> assertThat(false));
    }

    @Test
    public void deleteByUserIdTest() {
        addressDao.deleteByUserId(FIND_ID);
        assertTrue(addressDao.findByUserId(FIND_ID).isEmpty());
    }

}

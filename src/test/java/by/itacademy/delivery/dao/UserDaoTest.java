//package by.itacademy.delivery.dao;
//
//import by.itacademy.delivery.entity.User;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//
//public class UserDaoTest {
//
//    private final UserDao userDao = UserDao.getInstance();
//    private User user;
//
//    @Before
//    public void setUp() throws Exception {
//        user = new User(123L,
//                "NAME",
//                "LASTNAME",
//                "@Gmail.com",
//                "+375112232231");
//    }
//
//    @Test
//    public void findAllTest() {
//        try {
//            var startSize = userDao.findAll().size();
//            saveModel();
//            assertEquals(startSize + 1, userDao.findAll().size());
//        } finally {
//            deleteModel();
//        }
//    }
//
//    @Test
//    public void findByIdTest() {
//        try {
//            saveModel();
//            var account = userDao.findById(this.user.getId()).get();
//            assertEquals(this.user.getId(), account.getId());
//            assertEquals(this.user.getName(), account.getName());
//            assertEquals(this.user.getLastName(), account.getLastName());
//            assertEquals(this.user.getEmail(), account.getEmail());
//            assertEquals(this.user.getPhone(), account.getPhone());
//        } finally {
//            deleteModel();
//        }
//    }
//
//    @Test
//    public void saveTest() {
//
//    }
//
//    @Test
//    public void updateTest() {
//        try {
//            saveModel();
//            user.setName("new_name");
//            user.setLastName("new_last_name");
//            user.setEmail("email");
//            user.setPhone("phone");
//            if (userDao.update(user)) {
//                var account = userDao.findById(this.user.getId()).get();
//                assertEquals(this.user.getName(), account.getName());
//                assertEquals(this.user.getLastName(), account.getLastName());
//                assertEquals(this.user.getEmail(), account.getEmail());
//                assertEquals(this.user.getPhone(), account.getPhone());
//            } else {
//                assert false;
//            }
//        } finally {
//            deleteModel();
//        }
//    }
//
//    @Test
//    public void deleteTest() {
//        try {
//            var startSize = userDao.findAll().size();
//            saveModel();
//            assertNotEquals(startSize, userDao.findAll().size());
//            userDao.delete(user.getId());
//            assertEquals(startSize, userDao.findAll().size());
//        } finally {
//            deleteModel();
//        }
//    }
//
//    private void saveModel() {
//        Optional<User> optionalTheme = userDao.save(user);
//        optionalTheme.ifPresent(value -> user = value);
//    }
//
//    private void deleteModel() {
//        if (user.getId() != null) {
//            userDao.delete(user.getId());
//        }
//    }
//
//}

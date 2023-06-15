//package by.itacademy.delivery.dao;
//
//import by.itacademy.delivery.entity.Account;
//import by.itacademy.delivery.entity.UsersServlet;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//public class AccountDaoTest {
//
//    private Account account;
//    private final AccountDao accountDao = AccountDao.getInstance();
//
//    @Before
//    public void setUp() throws Exception {
//        UsersServlet user = new UsersServlet();
//        user.setId(1L);
//        account = new Account(user, "login", "password");
//    }
//
//    @Test
//    public void findAllTest() {
//        try {
//            var startSize = accountDao.findAll().size();
//            saveModel();
//            assertEquals(startSize + 1, accountDao.findAll().size());
//        } finally {
//            deleteModel();
//        }
//    }
//
//    @Test
//    public void findByIdTest() {
//        try {
//            saveModel();
//            var account = accountDao.findById(this.account.getUser()).get();
//            assertEquals(this.account.getUser(), account.getUser());
//            assertEquals(this.account.getLogin(), account.getLogin());
//            assertEquals(this.account.getPassword(), account.getPassword());
//        } finally {
//            deleteModel();
//        }
//    }
//
//    @Test
//    public void saveTest() {
//        try {
//            var account = this.account;
//            saveModel();
//            assertEquals(account.getUser().getId(), this.account.getUser().getId());
//            assertEquals(account.getLogin(), this.account.getLogin());
//            assertEquals(account.getPassword(), this.account.getPassword());
//        } finally {
//            deleteModel();
//        }
//    }
//
//    @Test
//    public void updateTest() {
//        try {
//            saveModel();
//            account.setLogin("new_login");
//            account.setPassword("new_password");
//            if (accountDao.update(account)) {
//                var account = accountDao.findById(this.account.getUser()).get();
//                assertEquals(this.account.getLogin(), account.getLogin());
//                assertEquals(this.account.getPassword(), account.getPassword());
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
//            var startSize = accountDao.findAll().size();
//            saveModel();
//            assertNotEquals(startSize, accountDao.findAll().size());
//            accountDao.delete(account.getUser());
//            assertEquals(startSize, accountDao.findAll().size());
//    }
//
//    private void saveModel() {
//        Optional<Account> optionalTheme = accountDao.save(account);
//        optionalTheme.ifPresent(value -> account = value);
//    }
//
//    private void deleteModel() {
//        if (account.getUser() != null) {
//            accountDao.delete(account.getUser());
//        }
//    }
//
//}

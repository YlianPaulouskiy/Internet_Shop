package by.itacademy.shop.dao;

import by.itacademy.shop.exception.DaoException;
import by.itacademy.shop.entity.User;
import by.itacademy.shop.utils.ConnectionManager;
import by.itacademy.shop.utils.EntityManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private static final String FIND_ALL = """
            SELECT u.id as user_id, u.name as user_name, last_name, email, password, phone,
                    a.id as address_id, city, street, house, flat
            FROM users u
            LEFT JOIN addresses a ON a.user_id = u.id
            """;

    private static final String FIND_BY_ID = FIND_ALL + " WHERE u.id = ?";

    private static final String SAVE_SQL = """
            INSERT INTO users(name, last_name, email, password, phone)
            VALUES (?, ?, ?, ?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE users
            SET name = ?,
                        last_name = ?,
                        email = ?,
                        password = ?,
                        phone = ?
            WHERE id = ?
            """;

    private static final String LOGIN_SQL = FIND_ALL + " WHERE email = ? AND password = ?";

    public Optional<User> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
            User user = null;
            prepareStatement.setLong(1, id);
            var result = prepareStatement.executeQuery();
            if (result.next()) {
                user = EntityManager.buildUserWithProducts(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public List<User> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {
            List<User> users = new ArrayList<>();
            var result = statement.executeQuery(FIND_ALL);
            while (result.next()) {
                users.add(EntityManager.buildUserWithProducts(result));
            }
            return users;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public Optional<User> save(User user) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getLastName());
            prepareStatement.setString(3, user.getEmail());
            prepareStatement.setString(4, user.getPassword());
            prepareStatement.setString(5, user.getPhone());

            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }
            return user.getId() != null
                    ? Optional.of(user)
                    : Optional.empty();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public boolean update(User user) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getLastName());
            prepareStatement.setString(3, user.getEmail());
            prepareStatement.setString(4, user.getPassword());
            prepareStatement.setString(5, user.getPhone());
            prepareStatement.setLong(6, user.getId());

            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public Optional<User> login(String email, String password) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(LOGIN_SQL)) {
            User user = null;
            prepareStatement.setString(1, email);
            prepareStatement.setString(2, password);
            var result = prepareStatement.executeQuery();
            if (result.next()) {
                user = EntityManager.buildUserWithProducts(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

}

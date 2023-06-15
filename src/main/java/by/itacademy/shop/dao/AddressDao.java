package by.itacademy.shop.dao;

import by.itacademy.shop.entity.Address;
import by.itacademy.shop.exception.DaoException;
import by.itacademy.shop.utils.ConnectionManager;
import by.itacademy.shop.utils.EntityManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class AddressDao {

    private static final AddressDao INSTANCE = new AddressDao();

    private AddressDao() {
    }

    public static AddressDao getInstance() {
        return INSTANCE;
    }

    private static final String FIND_ALL = """
                        SELECT a.id as address_id, city, street, house, flat
                        FROM addresses a
            """;

    private static final String FIND_BY_USER_ID = FIND_ALL + " WHERE user_id = ?";

    private static final String SAVE_SQL = """
            INSERT INTO addresses(city, street, house, flat, user_id)
            VALUES (?, ?, ?, ?, ?);
            """;

    private static final String DELETE_BY_USER_ID = """
            DELETE FROM addresses
            WHERE user_id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE addresses
            SET     city = ?,
                        street = ?,
                        house = ?,
                        flat = ?
            WHERE user_id = ?
            """;

    public Optional<Address> findByUserId(Long userId) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_USER_ID)) {
            Address address = null;
            prepareStatement.setLong(1, userId);
            var result = prepareStatement.executeQuery();
            if (result.next()) {
                address = EntityManager.buildAddress(result);
            }
            return Optional.ofNullable(address);
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public Optional<Address> save(Long userId, Address address) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, address.getCity());
            prepareStatement.setString(2, address.getStreet());
            prepareStatement.setString(3, address.getHouse());
            prepareStatement.setString(4, address.getFlat());
            prepareStatement.setLong(5, userId);
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                address.setId(generatedKeys.getLong("id"));
            }
            return address.getId() != null
                    ? Optional.of(address)
                    : Optional.empty();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public boolean update(Long userId, Address address) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, address.getCity());
            prepareStatement.setString(2, address.getStreet());
            prepareStatement.setString(3, address.getHouse());
            prepareStatement.setString(4, address.getFlat());
            prepareStatement.setLong(5, userId);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public boolean deleteByUserId(Long userId) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_BY_USER_ID)) {
            prepareStatement.setLong(1, userId);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

}

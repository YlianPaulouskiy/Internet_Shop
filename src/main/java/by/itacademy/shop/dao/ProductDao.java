package by.itacademy.shop.dao;

import by.itacademy.shop.exception.DaoException;
import by.itacademy.shop.entity.Product;
import by.itacademy.shop.utils.ConnectionManager;
import by.itacademy.shop.utils.EntityManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao {

    private static final ProductDao INSTANCE = new ProductDao();

    private ProductDao() {
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    private static final String FIND_ALL = """
            SELECT p.id as product_id, p.name as product_name, description, price
            FROM products p
                        """;

    private static final String FIND_ALL_WITH_USER = """
            SELECT p.id as product_id, p.name as product_name, description, price,
            u.id as user_id, u.name as user_name, last_name, email, phone,
            a.id as address_id, city, street, house, flat
            FROM products p
            JOIN users u on p.user_id = u.id
            JOIN addresses a on u.id = a.user_id
            """;

    private static final String SAVE_SQL = """
            INSERT INTO products(name, description, price, user_id)
            VALUES (?, ?, ?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM products
            WHERE id = ?
            """;

    private static final String FIND_PRODUCTS_BY_USER_ID = FIND_ALL + " where user_id = ?";

    public List<Product> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {
            List<Product> products = new ArrayList<>();
            var result = statement.executeQuery(FIND_ALL_WITH_USER);
            while (result.next())
                products.add(EntityManager.buildProductWithUser(result));
            return products;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public List<Product> findAllByUserId(Long userId) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_PRODUCTS_BY_USER_ID)) {
            prepareStatement.setLong(1, userId);
            List<Product> products = new ArrayList<>();
            var result = prepareStatement.executeQuery();
            while (result.next()) {
                products.add(EntityManager.buildProduct(result));
            }
            return products;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public Optional<Product> save(Product product) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            setFieldsAtSql(product, prepareStatement);
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getLong("id"));
            }
            return product.getId() != null
                    ? Optional.of(product)
                    : Optional.empty();
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

    //---------------------------------------------------------------------------------------------------------------

    /**
     * Устанавливает поля в sql запросы save и update
     *
     * @param product          модель из которой берутся поля
     * @param prepareStatement запрос в который нужно установить поля из модели
     */
    private void setFieldsAtSql(Product product, PreparedStatement prepareStatement) throws SQLException {
        prepareStatement.setString(1, product.getName());
        prepareStatement.setString(2, product.getDescription());
        prepareStatement.setBigDecimal(3, product.getPrice());
        prepareStatement.setLong(4, product.getUser().getId());
    }

}

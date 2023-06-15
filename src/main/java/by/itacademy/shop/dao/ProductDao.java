package by.itacademy.shop.dao;

import by.itacademy.shop.exception.DaoException;
import by.itacademy.shop.entity.Product;
import by.itacademy.shop.utils.ConnectionManager;
import by.itacademy.shop.utils.EntityManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao implements Dao<Long, Product> {

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


    private static final String FIND_BY_ID = FIND_ALL_WITH_USER + " WHERE pr.id = ?";

    private static final String SAVE_SQL = """
            INSERT INTO products(name, description, price, user_id)
            VALUES (?, ?, ?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM products
            WHERE id = ?
            """;

    private static final String UPDATE_SQL = """
            UPDATE products
            SET name =?,
                        description =?,
                        price = ?,
                        user_id = ?
            WHERE id = ?
            """;

    private static final String FIND_PRODUCTS_BY_USER_ID = FIND_ALL + " where user_id = ?";


    public Optional<Product> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
            Product product = null;
            prepareStatement.setLong(1, id);
            var result = prepareStatement.executeQuery();
            if (result.next()) {
                product = EntityManager.buildProduct(result);
            }
            return product != null
                    ? Optional.of(product)
                    : Optional.empty();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

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

    public List<Product> findByUserId(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_PRODUCTS_BY_USER_ID)) {
            prepareStatement.setLong(1, id);
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
//            saveOrderProduct(product.getId(), product.getOrders(), connection);
//            saveStoreProduct(product.getId(), product.getStores(), connection);
            return product.getId() != null
                    ? Optional.of(product)
                    : Optional.empty();
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
//        return Optional.empty();
    }

    public boolean update(Product product) {
//        try (var connection = ConnectionManager.get();
//             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
//            setFieldsAtSql(product, prepareStatement);
//            prepareStatement.setLong(5, product.getId());
//            updateOrderProduct(product.getId(), product.getOrders(), connection);
//            updateStoreProduct(product.getId(), product.getStores(), connection);
//            return prepareStatement.executeUpdate() > 0;
//        } catch (SQLException exception) {
//            throw new DaoException(exception);
//        }
        return false;
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

//    /**
//     * Устанавливаем данные в таблицу связи многие ко многим order_product
//     *
//     * @param productId  id продукта
//     * @param ordersId   список заказов в который входит данный продукт
//     * @param connection соединение с базой
//     */
//    private void saveOrderProduct(Long productId, List<Long> ordersId, Connection connection) throws SQLException {
//        for (Long orderId : ordersId) {
//            String orderProductSql = """
//                    INSERT INTO order_product(order_id, product_id)
//                    VALUES (?,%d);
//                    """.formatted(productId);
//            try (var prepareStatement = connection.prepareStatement(orderProductSql)) {
//                prepareStatement.setLong(1, orderId);
//                prepareStatement.executeUpdate();
//            }
//        }
//    }
//
//    /**
//     * Обновляем данные в таблице order_product
//     *
//     * @param productId  id продукта
//     * @param ordersId   список заказов в который входит данный продукт
//     * @param connection соединение с базой
//     */
//    private void updateOrderProduct(Long productId, List<Long> ordersId, Connection connection) throws SQLException {
//        for (Long orderId : ordersId) {
//            String orderProductSql = """
//                    UPDATE order_product
//                    SET order_id = ?
//                    WHERE product_id = %d
//                    """.formatted(productId);
//            try (var prepareStatement = connection.prepareStatement(orderProductSql)) {
//                prepareStatement.setLong(1, orderId);
//                prepareStatement.executeUpdate();
//            }
//        }
//    }

    /**
     * Устанавливаем данные в таблицу связи многие ко многим store_product
     *
     * @param productId  id продукта
     * @param storesId   список магазинов в который входит данный продукт
     * @param connection соединение с базой
     */
//    private void saveStoreProduct(Long productId, List<Long> storesId, Connection connection) throws SQLException {
//        for (Long storeId : storesId) {
//            String storeProductSql = """
//                    INSERT INTO store_product(store_id, product_id)
//                    VALUES (?, %d);
//                    """.formatted(productId);
//            try (var prepareStatement = connection.prepareStatement(storeProductSql)) {
//                prepareStatement.setLong(1, storeId);
//                prepareStatement.executeUpdate();
//            }
//        }
//    }

    /**
     * Обновляем данные в таблице связи store_product
     *
     * @param productId  id продукта
     * @param storesId   список магазинов в который входит данный продукт
     * @param connection соединение с базой
     */
//    private void updateStoreProduct(Long productId, List<Long> storesId, Connection connection) throws SQLException {
//        for (Long storeId : storesId) {
//            String storeProductSql = """
//                    UPDATE store_product
//                    SET store_id = ?
//                    WHERE product_id = %d
//                    """.formatted(productId);
//            try (var prepareStatement = connection.prepareStatement(storeProductSql)) {
//                prepareStatement.setLong(1, storeId);
//                prepareStatement.executeUpdate();
//            }
//        }
//    }

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
        if (product.getUser() != null && product.getUser().getId() != null) {
            prepareStatement.setLong(4,product.getUser().getId());
        }
    }

}

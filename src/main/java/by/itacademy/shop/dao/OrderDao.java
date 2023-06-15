//package by.itacademy.delivery.dao;
//
//import by.itacademy.delivery.exception.DaoException;
//import by.itacademy.delivery.entity.*;
//import by.itacademy.delivery.utils.ConnectionManager;
//import by.itacademy.delivery.utils.EntityManager;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class OrderDao implements Dao<Long, Order> {
//
//    private static final OrderDao INSTANCE = new OrderDao();
//
//    private OrderDao() {
//    }
//
//    public static OrderDao getInstance() {
//        return INSTANCE;
//    }
//
//    private static final String FIND_ALL = """
//            SELECT o.id                                               as order_id,
//                                 registration_time,
//                                 status,
//                                 user_id,
//                                 address_id,
//                                 array_to_string(ARRAY(SELECT product_id
//                                                       FROM "order"
//                                                                JOIN order_product op on "order".id = op.order_id
//                                                       WHERE order_id = o.id), ' ') as products
//                          FROM "order" o
//            """;
//
//    private static final String FIND_BY_ID = FIND_ALL + " WHERE o.id = ?";
//
//    private static final String SAVE_SQL = """
//            INSERT INTO "order"(registration_time, status, user_id, address_id)
//            VALUES (?, ?, ?, ?);
//            """;
//
//    private static final String DELETE_SQL = """
//            DELETE FROM "order"
//            WHERE id = ? ;
//            """;
//
//    private static final String UPDATE_SQL = """
//            UPDATE "order"
//            SET registration_time = ?,
//                        status = ?,
//                        user_id = ?,
//                        address_id = ?
//            WHERE id = ?;
//            """;
//
//    public Optional<Order> findById(Long id) {
//        try (var connection = ConnectionManager.get();
//             var prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
//            Order order = null;
//            prepareStatement.setLong(1, id);
//            var result = prepareStatement.executeQuery();
//            if (result.next()) {
//                order = EntityManager.buildOrder(result);
//            }
//            return order != null
//                    ? Optional.of(order)
//                    : Optional.empty();
//        } catch (SQLException exception) {
//            throw new DaoException(exception);
//        }
//    }
//
//    public List<Order> findAll() {
//        try (var connection = ConnectionManager.get();
//             var statement = connection.createStatement()) {
//            List<Order> orders = new ArrayList<>();
//            var result = statement.executeQuery(FIND_ALL);
//            while (result.next()) {
//                orders.add(EntityManager.buildOrder(result));
//            }
//            return orders;
//        } catch (SQLException exception) {
//            throw new DaoException(exception);
//        }
//    }
//
//    public Optional<Order> save(Order order) {
//        try (var connection = ConnectionManager.get();
//             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
//            setFieldsAtSql(order, prepareStatement);
//            prepareStatement.executeUpdate();
//            var result = prepareStatement.getGeneratedKeys();
//            if (result.next()) {
//                order.setId(result.getLong("id"));
//            }
//            //сохраняем связи меж таблицами
//            saveOrderProduct(order.getId(), order.getProducts(), connection);
//            return order.getId() != null
//                    ? Optional.of(order)
//                    : Optional.empty();
//        } catch (SQLException exception) {
//            throw new DaoException(exception);
//        }
//    }
//
//    public boolean update(Order order) {
//        try (var connection = ConnectionManager.get();
//             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
//            setFieldsAtSql(order, prepareStatement);
//            //обновляем связи между таблицами
//            updateOrderProduct(order.getId(), order.getProducts(), connection);
//            prepareStatement.setLong(3, order.getId());
//            return prepareStatement.executeUpdate() > 0;
//        } catch (SQLException exception) {
//            throw new DaoException(exception);
//        }
//    }
//
//    public boolean delete(Long id) {
//        try (var connection = ConnectionManager.get();
//             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
//            prepareStatement.setLong(1, id);
//            return prepareStatement.executeUpdate() > 0;
//        } catch (SQLException exception) {
//            throw new DaoException(exception);
//        }
//    }
//
//    //---------------------------------------------------------------------------------------------------------------
//
//    /**
//     * Устанавливаем данные в таблицу связи многие ко многим order_product
//     *
//     * @param orderId    id заказа
//     * @param productsId список продуктов в заказе
//     * @param connection соединение с базой
//     */
//    private void saveOrderProduct(Long orderId, List<Long> productsId, Connection connection) throws SQLException {
//        for (Long productId : productsId) {
//            String orderProductSql = """
//                    INSERT INTO order_product(order_id, product_id)
//                    VALUES (%d,?);
//                    """.formatted(orderId);
//            try (var prepareStatement = connection.prepareStatement(orderProductSql)) {
//                prepareStatement.setLong(1, productId);
//                prepareStatement.executeUpdate();
//            }
//        }
//    }
//
//    /**
//     * Обновляем данные в таблице order_product
//     *
//     * @param orderId    id заказа
//     * @param productsId список продуктов в заказе
//     * @param connection соединение с базой
//     */
//    private void updateOrderProduct(Long orderId, List<Long> productsId, Connection connection) throws SQLException {
//        for (Long productId : productsId) {
//            String orderProductSql = """
//                    UPDATE delivery.public.order_product
//                    SET product_id = ?
//                    WHERE order_id = %d
//                    """.formatted(orderId);
//            try (var prepareStatement = connection.prepareStatement(orderProductSql)) {
//                prepareStatement.setLong(1, productId);
//                prepareStatement.executeUpdate();
//            }
//        }
//    }
//
//    /**
//     * Устанавливает поля в sql запросы save и update
//     *
//     * @param order            модель из которой берутся поля
//     * @param prepareStatement запрос в который нужно установить поля из модели
//     */
//    private void setFieldsAtSql(Order order, PreparedStatement prepareStatement) throws SQLException {
//        prepareStatement.setTimestamp(1, Timestamp.valueOf(order.getRegistrationTime()));
//        prepareStatement.setTimestamp(2, Timestamp.valueOf(order.getStatus().name()));
//        prepareStatement.setLong(3, order.getUser().getId());
//        prepareStatement.setLong(4, order.getAddress().getId());
//    }
//
//}

package by.itacademy.shop.dao;

import by.itacademy.shop.entity.Order;
import by.itacademy.shop.entity.Product;
import by.itacademy.shop.exception.DaoException;
import by.itacademy.shop.utils.ConnectionManager;
import by.itacademy.shop.utils.EntityManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderDao {

    private static final OrderDao INSTANCE = new OrderDao();

    private OrderDao() {
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    private static final String FIND_ALL = """
            SELECT o.id as order_id, registration_time,
                        u.id as user_id, u.name as user_name, u.last_name, email, phone, password
            FROM orders o
            JOIN users u on u.id = o.user_id
            """;

    private static final String FIND_BY_USER_ID = FIND_ALL + " WHERE u.id = ?";

    private static final String SAVE_SQL = """
            INSERT INTO orders(registration_time, user_id)
            VALUES (?, ?);
            """;

    private static final String DELETE_SQL = """
            DELETE FROM orders
            WHERE id = ? ;
            """;

    public List<Order> findAllByUserId(Long userId) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(FIND_BY_USER_ID)) {
            List<Order> orders = new ArrayList<>();
            prepareStatement.setLong(1, userId);
            var result = prepareStatement.executeQuery();
            while (result.next()) {
                orders.add(EntityManager.buildOrder(result));
            }
            return orders;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public Optional<Order> save(Order order) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setTimestamp(1, Timestamp.valueOf(order.getRegistrationTime()));
            prepareStatement.setLong(2, order.getUser().getId());
            prepareStatement.executeUpdate();
            var result = prepareStatement.getGeneratedKeys();
            if (result.next()) {
                order.setId(result.getLong("id"));
            }
            //сохраняем связи меж таблицами
            saveOrderProduct(order.getId(),
                    order.getProducts().stream().map(Product::getId).collect(Collectors.toList()),
                    connection);
            return order.getId() != null
                    ? Optional.of(order)
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
     * Устанавливаем данные в таблицу связи многие ко многим order_product
     *
     * @param orderId    id заказа
     * @param productsId список продуктов в заказе
     * @param connection соединение с базой
     */
    private void saveOrderProduct(Long orderId, List<Long> productsId, Connection connection) throws SQLException {
        for (Long productId : productsId) {
            String orderProductSql = """
                    INSERT INTO order_product(order_id, product_id)
                    VALUES (%d,?);
                    """.formatted(orderId);
            try (var prepareStatement = connection.prepareStatement(orderProductSql)) {
                prepareStatement.setLong(1, productId);
                prepareStatement.executeUpdate();
            }
        }
    }

}

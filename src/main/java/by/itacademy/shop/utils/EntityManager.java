package by.itacademy.shop.utils;

import by.itacademy.shop.dao.ProductDao;
import by.itacademy.shop.entity.Address;
import by.itacademy.shop.entity.Order;
import by.itacademy.shop.entity.Product;
import by.itacademy.shop.entity.User;
import by.itacademy.shop.exception.DaoException;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;

@UtilityClass
public class EntityManager {

    public User buildUser(ResultSet result) throws SQLException {
        return User.builder()
                .id(result.getLong("user_id"))
                .name(result.getString("user_name"))
                .lastName(result.getString("last_name"))
                .email(result.getString("email"))
                .phone(result.getString("phone"))
                .address(buildAddress(result))
                .build();
    }

    public User buildUserWithProducts(ResultSet result) throws SQLException {
        return User.builder()
                .id(result.getLong("user_id"))
                .name(result.getString("user_name"))
                .lastName(result.getString("last_name"))
                .email(result.getString("email"))
                .password(result.getString("password"))
                .phone(result.getString("phone"))
                .address(buildAddress(result))
                .products(ProductDao.getInstance().findAllByUserId(result.getLong("user_id")))
                .build();
    }


    public Product buildProduct(ResultSet result) throws SQLException {
        return Product.builder()
                .id(result.getLong("product_id"))
                .name(result.getString("product_name"))
                .description(result.getString("description"))
                .price(result.getBigDecimal("price"))
                .build();
    }

    public Product buildProductWithUser(ResultSet result) throws SQLException {
        var product = buildProduct(result);
        product.setUser(buildUser(result));
        return product;
    }

    public Address buildAddress(ResultSet result) {
        try {
            if (result.getLong("address_id") != 0) {
                return Address.builder()
                        .id(result.getLong("address_id"))
                        .city(result.getString("city"))
                        .street(result.getString("street"))
                        .house(result.getString("house"))
                        .flat(result.getString("flat"))
                        .build();
            } else {
                return null;
            }
        } catch (SQLException exception) {
            return null;
        }
    }

    public Order buildOrder(ResultSet result) throws SQLException {
        return Order.builder()
                .id(result.getLong("order_id"))
                .registrationTime(result.getTimestamp("registration_time").toLocalDateTime())
                .user(buildUser(result))
                .products(ProductDao.getInstance().findAllByOrderId(result.getLong("order_id")))
                .build();
    }

}

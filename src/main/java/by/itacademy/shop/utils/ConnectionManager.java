package by.itacademy.shop.utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManager {

    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String URL_KEY = "db.url";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final Integer DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> connectionPool;

    static {
        loadDriver();
        initConnection();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void initConnection() {

        var poolSize = PropertiesManager.get(POOL_SIZE_KEY);
        var size = poolSize == null
                ? DEFAULT_POOL_SIZE
                : Integer.parseInt(poolSize);
        connectionPool = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            var connection = open();
            var proxyConnection = (Connection) Proxy.newProxyInstance(
                    ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    ((proxy, method, args) -> method.getName().equals("close")
                            ? connectionPool.add(open())
                            : method.invoke(connection, args))
            );
            connectionPool.add(proxyConnection);
        }
    }

    public static Connection get() {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection open() {
        try {
            return DriverManager.getConnection(PropertiesManager.get(URL_KEY),
                    PropertiesManager.get(USERNAME_KEY),
                    PropertiesManager.get(PASSWORD_KEY));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}

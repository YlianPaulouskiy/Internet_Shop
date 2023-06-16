package by.itacademy.shop.utils;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

@UtilityClass
public class TestDBUtils {

    private final Path schemaPath = Paths.get("src/main/resources/sql/schema.sql");
    private final Path dataPath = Paths.get("src/main/resources/sql/data.sql");
    private final Connection connection = ConnectionManager.get();

    public void initDb() {
        try (
             var createStatement = connection.prepareStatement(FileUtils.read(schemaPath));
             var insertStatement = connection.createStatement()) {
            createStatement.executeUpdate();
            insertStatement.executeUpdate(FileUtils.read(dataPath));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}

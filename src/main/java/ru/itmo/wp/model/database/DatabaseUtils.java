package ru.itmo.wp.model.database;

import org.mariadb.jdbc.MariaDbDataSource;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseUtils {
    public static DataSource getDataSource() {
        return DataSourceHolder.INSTANCE;
    }

    public static void setUpStatement(PreparedStatement statement, Object... parameters) {
        try {
            for (int index = 1; index <= parameters.length; ++index) {
                statement.setObject(index, parameters[index - 1]);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't set up statement.", e);
        }
    }

    private static final class DataSourceHolder {
        private static final DataSource INSTANCE;
        private static final Properties properties = new Properties();

        static {
            try {
                properties.load(DataSourceHolder.class.getResourceAsStream("/application.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Can't load /application.properties.", e);
            }

            try {
                MariaDbDataSource instance = new MariaDbDataSource();
                instance.setUrl(properties.getProperty("database.url"));
                instance.setUser(properties.getProperty("database.user"));
                instance.setPassword(properties.getProperty("database.password"));
                INSTANCE = instance;
            } catch (SQLException e) {
                throw new RuntimeException("Can't initialize DataSource.", e);
            }

            try (Connection connection = INSTANCE.getConnection()) {
                if (connection == null) {
                    throw new RuntimeException("Can't create testing connection via DataSource.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Can't create testing connection via DataSource.", e);
            }
        }
    }
}

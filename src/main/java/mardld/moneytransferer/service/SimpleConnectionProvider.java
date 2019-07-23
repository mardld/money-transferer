package mardld.moneytransferer.service;

import lombok.RequiredArgsConstructor;
import mardld.moneytransferer.MoneyTransfererProperties;
import org.jooq.ConnectionProvider;
import org.jooq.exception.DataAccessException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class SimpleConnectionProvider implements ConnectionProvider {

    private final MoneyTransfererProperties properties;

    @Override
    public Connection acquire() throws DataAccessException {
        try {
            return DriverManager.getConnection(
                    properties.getDatabaseUrl(),
                    properties.getDatabaseUser(),
                    properties.getDatabasePassword());
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void release(Connection connection) throws DataAccessException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}

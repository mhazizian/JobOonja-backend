package ir.aziz.karam.model.dataLayer.dbConnectionPool.impl;

import ir.aziz.karam.model.dataLayer.dbConnectionPool.BasicDBConnectionPool;
import ir.aziz.karam.model.dataLayer.dbConnectionPool.DBConnectionPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * this implementation is NOT thread-safe
 *
 * */
public class SQLiteBasicDBConnectionPool extends BasicDBConnectionPool {


    private List<Connection> connectionPool;
    private List<Connection> usedConnections;


    public SQLiteBasicDBConnectionPool(int minConnections, int maxConnections, String dbUrl) {
        super(minConnections, maxConnections, dbUrl);
    }

    protected void setUp() {
        usedConnections = new ArrayList<>();
        connectionPool = new ArrayList<>(getMinConnections());
        for (int i = 0; i < getMinConnections(); i++) {
            connectionPool.add(createConnection());
        }
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(getDbUrl());
        } catch (SQLException e) {
            throw new DBConnectionPoolException("error in connection to db: " + e.getMessage());
        }
    }

    public Connection get() {
        if (connectionPool.isEmpty()) {
            if (getSize() < getMaxConnections()) {
                connectionPool.add(createConnection());
            } else {
                throw new DBConnectionPoolException(
                        "Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public void release(Connection connection) {
        if (!usedConnections.contains(connection)) {
            throw new DBConnectionPoolException(
                    "connection is not in use!");
        }
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }

    public void terminate() {
        connectionPool.addAll(usedConnections);
        usedConnections.clear();
        for (Connection c : connectionPool) {
            try {
                c.close();
            } catch (SQLException e) {
                throw new DBConnectionPoolException("error in closing connections: " + e.getMessage());
            }
        }
        connectionPool.clear();
    }
}

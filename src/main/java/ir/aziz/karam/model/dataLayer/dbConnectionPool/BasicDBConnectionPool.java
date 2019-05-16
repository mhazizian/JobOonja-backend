package ir.aziz.karam.model.dataLayer.dbConnectionPool;

import java.sql.Connection;



/**
 *
 * implementing connection-pool from scratch is not best choice!
 *
 * @see dataLayer.DBCPDBConnectionPool
 *
 * */
public abstract class BasicDBConnectionPool implements ResourcePool<Connection> {

    private int minConnections;
    private int maxConnections;
    private String dbUrl;

    protected BasicDBConnectionPool(int minConnections,
                                    int maxConnections,
                                    String dbUrl) {
        if (minConnections <= 0 || maxConnections <= 0 || maxConnections < minConnections)
            throw new DBConnectionPoolException("invalid connections size config.");

        this.dbUrl = dbUrl;
        this.minConnections = minConnections;
        this.maxConnections = maxConnections;

        setUp();
    }

    protected abstract void setUp();

    public abstract int getSize();

    public int getMinConnections() {
        return minConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public String getDbUrl() {
        return dbUrl;
    }

}

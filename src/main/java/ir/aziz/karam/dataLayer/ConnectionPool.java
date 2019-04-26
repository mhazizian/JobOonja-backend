package ir.aziz.karam.dataLayer;

import ir.aziz.karam.dataLayer.dbConnectionPool.BasicDBConnectionPool;
import ir.aziz.karam.dataLayer.dbConnectionPool.impl.SQLiteBasicDBConnectionPool;

public class ConnectionPool {

    private final static String dbURL = "jdbc:sqlite:taca7.db";
    private static BasicDBConnectionPool instance;

    public static BasicDBConnectionPool getInstance() {
        if (instance == null) {
            instance = new SQLiteBasicDBConnectionPool(2, 4 , dbURL);
        }
        return instance;
    }
}

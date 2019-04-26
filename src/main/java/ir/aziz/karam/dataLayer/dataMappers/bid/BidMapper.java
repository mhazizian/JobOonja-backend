package ir.aziz.karam.dataLayer.dataMappers.bid;

import ir.aziz.karam.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.Bid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BidMapper extends Mapper<Bid, String> implements IBidMapper {

    private static final String COLUMNS = " user_id, project_id, bidAmount";
    private static BidMapper instance;

    public static BidMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new BidMapper();
        }
        return instance;
    }

    public Bid find(String project_id, String user_id) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getFindStatement())) {
            st.setString(1, project_id);
            st.setString(2, user_id);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    private BidMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st
                = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Bid" + " ("
                + "user_id TEXT, "
                + "project_id TEXT, "
                + "bidAmount INTEGER, "
                + "PRIMARY KEY (user_id, project_id),"
                + "FOREIGN KEY (user_id) REFERENCES User"
                + "FOREIGN KEY (project_id) REFERENCES Project"
                + ")");
        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS
                + " FROM Bid"
                + " WHERE "
                + " user_id = ? AND"
                + " project_id = ?";
    }

    @Override
    protected Bid convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Bid(rs.getString(1), rs.getString(2), rs.getInt(3));
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM Bid";
    }

    @Override
    protected void setInsertElementParamters(PreparedStatement st, Bid element) throws SQLException {
        st.setString(1, element.getBiddingUser());
        st.setString(2, element.getProjectTitle());
        st.setInt(3, element.getBidAmount());

    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO Bid (user_id, project_id, bidAmount) VALUES (?, ?, ?)";
    }

}

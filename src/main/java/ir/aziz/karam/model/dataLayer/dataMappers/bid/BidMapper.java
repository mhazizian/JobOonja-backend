package ir.aziz.karam.model.dataLayer.dataMappers.bid;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.model.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.Bid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BidMapper extends Mapper<Bid, String> implements IBidMapper {

    private static final String COLUMNS = " user_id, project_id, bidAmount";
    private static BidMapper instance;

    public static BidMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new BidMapper();
        }
        return instance;
    }

    public List<Bid> getProjectBids(String project_id) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getFindStatement())) {
            st.setString(1, project_id);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                return convertResultSetToDomainModelList(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query. BidMapper\n" + ex.getMessage());
                throw ex;
            }
        }
    }

    private BidMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st
                = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Bid" + " ("
                + "user_id INTEGER, "
                + "project_id VARCHAR(200), "
                + "bidAmount INTEGER, "
                + "PRIMARY KEY (user_id, project_id), "
                + "FOREIGN KEY (user_id) REFERENCES User(id), "
                + "FOREIGN KEY (project_id) REFERENCES Project(id) "
                + ")");
        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS
                + " FROM Bid"
                + " WHERE "
                + " project_id = ?";
    }

    @Override
    protected Bid convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Bid(
                rs.getInt(1),
                rs.getString(2),
                rs.getInt(3)
        );
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM Bid";
    }

    @Override
    protected void setInsertElementParameters(PreparedStatement st, Bid element, int baseIndex) throws SQLException {
        st.setInt(baseIndex, element.getBiddingUser());
        st.setString(1 + baseIndex, element.getProjectTitle());
        st.setInt(2 + baseIndex, element.getBidAmount());

    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO Bid (user_id, project_id, bidAmount) VALUES (?, ?, ?)";
    }

    @Override
    protected void setInsertOrUpdateElementParameters(PreparedStatement st, Bid element) throws SQLException {
        this.setInsertElementParameters(st, element, 1);
        this.setInsertElementParameters(st, element, 4);
    }

    @Override
    protected String getInsertOrUpdateStatement() {
        return  "INSERT INTO Bid (user_id, project_id, bidAmount) VALUES (?, ?, ?)\n"
                + "ON DUPLICATE KEY UPDATE\n"
                + "user_id=?, project_id=?, bidAmount=?";

    }

}

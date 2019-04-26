package ir.aziz.karam.model.dataLayer.dataMappers.endorsment;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.model.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.Endorse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EndorsmentMapper extends Mapper<Endorse, String> implements IEndorsmentMapper {

    private static final String COLUMNS = " endorser_id, endorsed_id, skill_id";
    private static EndorsmentMapper instance;

    public static EndorsmentMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new EndorsmentMapper();
        }
        return instance;
    }

    public Endorse find(String endorser_id, String endorsed_id, String skill_id) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getFindStatement())) {
            st.setString(1, endorser_id);
            st.setString(2, endorsed_id);
            st.setString(3, skill_id);
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

    private EndorsmentMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st
                = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Endorse" + " ("
                + "endorser_id TEXT PRIMARY KEY, "
                + "endorsed_id TEXT PRIMARY KEY, "
                + "skill_id TEXT PRIMARY KEY, "
                + "FOREIGN KEY (endorser_id) REFERENCES User, "
                + "FOREIGN KEY (endorsed_id) REFERENCES User, "
                + "FOREIGN KEY (skill_id, endorsed_id) REFERENCES SkillUser ON DELETE CASCADE "
                + ")");
        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS
                + " FROM Endorse"
                + " WHERE "
                + " endorser_id = ? AND"
                + " endorsed_id = ? AND"
                + " skill_id = ?";
    }

    @Override
    protected Endorse convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Endorse(rs.getString(1), rs.getString(2), rs.getString(3));
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM Endorse";
    }

    @Override
    protected void setInsertElementParamters(PreparedStatement st, Endorse element) throws SQLException {
        st.setString(1, element.getEndorser_id());
        st.setString(2, element.getUserIsEndorsed());
        st.setString(3, element.getSkill());

    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO Endorse (endorser_id, endorsed_id, skill_id) VALUES (?, ?, ?)";
    }

}

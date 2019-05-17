package ir.aziz.karam.model.dataLayer.dataMappers.endorsment;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.model.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.Endorse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EndorsmentMapper extends Mapper<Endorse, String> implements IEndorsmentMapper {

    private static final String COLUMNS = " endorser_id, skill_id, endorsed_id";
    private static EndorsmentMapper instance;

    private String getSkillsEndorsedByUserOnUserStatement() {
        return "SELECT " + COLUMNS
                + " FROM Endorse"
                + " WHERE "
                + " endorser_id = ? AND"
                + " endorsed_id = ?";
    }


    public static EndorsmentMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new EndorsmentMapper();
        }
        return instance;
    }

    public Endorse find(int endorser_id, int endorsed_id, String skill_id) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getFindStatement())) {
            st.setInt(1, endorser_id);
            st.setInt(2, endorsed_id);
            st.setString(3, skill_id);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query. Endorsment\n" + ex.getMessage());
                throw ex;
            }
        }
    }

    private EndorsmentMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st
                = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Endorse" + " ("
                + "endorser_id INTEGER , "
                + "endorsed_id INTEGER , "
                + "skill_id VARCHAR(200), "
                + "PRIMARY KEY (endorser_id, endorsed_id, skill_id), "
                + "FOREIGN KEY (endorser_id) REFERENCES User(id), "
                + "FOREIGN KEY (endorsed_id) REFERENCES User(id), "
                + "FOREIGN KEY (skill_id, endorsed_id) REFERENCES SkillUser(skill_name, user_id) ON DELETE CASCADE "
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
        return new Endorse(
                rs.getInt(1),
                rs.getString(2),
                rs.getInt(3)
        );
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM Endorse";
    }

    @Override
    protected void setInsertElementParameters(PreparedStatement st, Endorse element, int baseIndex) throws SQLException {
        st.setInt(baseIndex, element.getEndorser_id());
        st.setInt(1 + baseIndex, element.getUserIsEndorsed());
        st.setString(2 + baseIndex, element.getSkill());

    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO Endorse (endorser_id, endorsed_id, skill_id) VALUES (?, ?, ?)";
    }

    @Override
    protected void setInsertOrUpdateElementParameters(PreparedStatement st, Endorse element) throws SQLException {
        this.setInsertElementParameters(st, element, 1);
    }

    @Override
    protected String getInsertOrUpdateStatement() {
        return  "INSERT IGNORE INTO Endorse (endorser_id, endorsed_id, skill_id) VALUES (?, ?, ?)";
    }



    public List<Endorse> getSkillsEndorsedByUserOnUser(int endorser, int endorsed) throws SQLException {
        List<Endorse> skills = new ArrayList<>();

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(this.getSkillsEndorsedByUserOnUserStatement())) {
            st.setInt(1,endorser);
            st.setInt(2, endorsed);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()) {
                    skills.add(this.convertResultSetToDomainModel(resultSet));
                }

                return skills;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.  Endorsmernt\n" + ex.getMessage());
                throw ex;
            }
        }
    }
}
